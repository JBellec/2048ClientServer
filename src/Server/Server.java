package Server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;

public class Server {
	
	public static final int DEFAULT_PORT = 8189;
	private int port;
	
	//The channel on wich we'll accept connections
	private ServerSocketChannel serverChannel;
	
	//The selector we'll be monitoring
	private Selector selector;
	private String messages = "message";

	public Server()
	{
		this(DEFAULT_PORT);
	}
	public Server(int port)
	{
		this.port = port;
	}
	
	
	public void setup() throws IOException
	{
		this.selector = Selector.open();
		
		this.serverChannel = ServerSocketChannel.open();
		this.serverChannel.configureBlocking(false);
		
		InetAddress ia = InetAddress.getLocalHost();
		System.out.println(ia);
		InetSocketAddress isa = new InetSocketAddress(ia,this.port);
		this.serverChannel.socket().bind(isa);
	}
	
	public void start() throws IOException
	{
		System.out.println("Setting up server...");
		this.setup();
		System.out.println("Server started...");
		SelectionKey acceptKey = this.serverChannel.register(this.selector,  SelectionKey.OP_ACCEPT);
		
		while(acceptKey.selector().select()>0)
		{
			for(Iterator<SelectionKey> it = this.selector.selectedKeys().iterator();it.hasNext();)
			{
				SelectionKey key = it.next();
				it.remove();
				
				if(!key.isValid())
				{
					continue;
				}
				
				if(key.isAcceptable())
				{
					System.out.println("Key is Acceptable");
					this.accept(key);
					continue;
				}
				else if (key.isReadable())
				{
					System.out.println("Key is Readable");
					this.read(key);
					continue;
				}
				else if (key.isWritable())
				{
					System.out.println("Key is Writable");
					this.write(key);
					continue;
				}
			}
		}
	}
	
	private void write(SelectionKey key) throws IOException {
		SocketChannel socket = (SocketChannel) key.channel();
		byte [] message = new String(messages).getBytes();
		ByteBuffer buffer = ByteBuffer.wrap(message);
		int nBytes = socket.write(buffer);
		buffer.clear();
		
	}
	private void read(SelectionKey key) throws IOException {
		SocketChannel socket = (SocketChannel) key.channel();
		ByteBuffer buffer = ByteBuffer.allocate(512);
		socket.read(buffer);
		String output = new String(buffer.array()).trim();
		System.out.println("Message read from client: " + output);
		if(output.equals("coucou"))
		{
			System.out.println("Client send coucou !");
			this.write(key);
		}		
	}
	private void accept(SelectionKey key) throws IOException {
		ServerSocketChannel ssc = (ServerSocketChannel)key.channel();
		SocketChannel socket = (SocketChannel)ssc.accept();
		socket.configureBlocking(false);
		socket.register(this.selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
	}
	
	public static void main(String[] args)
	{
	    Server server = new Server();
	    try {
			server.start();
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

}

package Server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Iterator;

public class Server {
	
	public static final int DEFAULT_PORT = 8189;
	private int port;
	
	//The channel on wich we'll accept connections
	private ServerSocketChannel serverChannel;
	
	//The selector we'll be monitoring
	private Selector selector;
	private String messages = "message";
	
	private ServerController sc;
	
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
		sc = new ServerController(this);
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
				try {
					if(!key.isValid())
					{
				//		System.out.println("Key is not valid...");
						continue;
					}
					
					if(key.isAcceptable())
					{
						System.out.println("Key is Acceptable");
						 ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
						 SocketChannel socket = (SocketChannel) ssc.accept();
						 socket.configureBlocking(false);
						 socket.register(this.selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
						 continue;
					}
					
					if (key.isReadable())
					{
				//		System.out.println("Key is Readable");
						
						SocketChannel clientChannel = (SocketChannel) key.channel();
						this.doEcho("readable", clientChannel);
						
						continue;
					}
					
					if (key.isWritable())
					{
			//			System.out.println("Key is Writable");
						
						SocketChannel clientChannel = (SocketChannel) key.channel();
						this.doEcho("writable", clientChannel);
						
						continue;
					}
				} catch (CancelledKeyException e) {
					e.printStackTrace();
					key.channel().close();
				}
				
			}
		}
	}
	
	public void doEcho(String evt, SocketChannel socket) throws IOException {
		String msg = this.readMessage(socket);
		if (msg.length() <= 0) return;
		
		if (msg.trim().equals("getSize")) 
		{
			String res = this.sc.addClient(socket).toString();
			System.out.println(res);
			this.writeMessage(socket, res);
		}
		if (msg.trim().startsWith("move")) 
		{
			String gotString = msg.trim();
			System.out.println(gotString);
			gotString.replace("move,", "");
			System.out.println(gotString);
			String res;
			res = (this.sc.move(gotString).toString());
			this.writeMessage(socket, res);
		}
		
		
	}
	public void writeMessage(SocketChannel socket, String msg) throws IOException {
		  ByteBuffer buffer = ByteBuffer.wrap((msg.getBytes()));
		  int nBytes = socket.write(buffer);
		}
	public String readMessage(SocketChannel socket) throws IOException {
		  ByteBuffer rcvbuf = ByteBuffer.allocate(1024);
		  int nBytes = socket.read(rcvbuf);
		  rcvbuf.flip();
		  Charset charset = Charset.forName("us-ascii");
		  CharsetDecoder decoder = charset.newDecoder();
		  String res = decoder.decode(rcvbuf).toString();
		  return res;
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
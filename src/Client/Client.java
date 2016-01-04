package Client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Client{

	public static final int DEFAULT_PORT = 8189;
	int port;
	
	SocketChannel clientChannel;
	
	public Client() throws IOException
	{
		this(DEFAULT_PORT);
	}
	
	public Client(int port) {
		this.port = port;
	}
	
	public void setup() throws IOException
	{
		this.clientChannel = SocketChannel.open();
		clientChannel.configureBlocking(false);
		clientChannel.connect(new InetSocketAddress(InetAddress.getLocalHost(),DEFAULT_PORT));
		
	}
	
	public void start() throws IOException
	{
		System.out.println("Setting up client ...");
		this.setup();
		System.out.println("Client ready");
		
		while(!clientChannel.finishConnect())
		{
			
		}
		
	}

	private  void write(String str) throws IOException
	{
		String message = str;
		byte[] messages = message.getBytes();
		ByteBuffer buffer = ByteBuffer.wrap(messages);
		clientChannel.write(buffer);
		buffer.clear();
	}
	
	private void read() throws IOException
	{
		ByteBuffer buffer = ByteBuffer.allocate(512);
		clientChannel.read(buffer);
		String output = new String(buffer.array()).trim();
		System.out.println("Message read from client: " + output);
		if(output.equals("message"))
		{
			System.out.println("Server send a message !");
		}
	}
	
	public static void main (String [] args) throws IOException
	{
		Client client = new Client();
		try {
			client.start();
			client.write("coucou");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

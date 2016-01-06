package Client;

import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public class Client {
	
	private ClientController controller;
	private final int port;
	private SocketChannel socket;
	
	/*public static final int DEFAULT_PORT = 8189;
	
	public Client() throws IOException {
		this(DEFAULT_PORT);
	}*/
	
	public Client(int port) throws IOException
	{
		this.port = port;
        InetSocketAddress hostAddress = new InetSocketAddress(InetAddress.getLocalHost(), this.port);
        this.socket = SocketChannel.open(hostAddress);
        // server should initiate a model creation for each new connection
      
        System.out.println("Client sending messages to server...");

        // request size to server
        byte [] sizeBuff = new String("getSize").getBytes();
        ByteBuffer buffer = ByteBuffer.wrap(sizeBuff);
        this.socket.write(buffer);
        
        System.out.println("getSize sent");
        // receive size from server
        ByteBuffer rcvbuf = ByteBuffer.allocate(1024);
		int nBytes = this.socket.read(rcvbuf);
		rcvbuf.flip();  
		Charset charset = Charset.forName("us-ascii");
		CharsetDecoder decoder = charset.newDecoder();
		String res = decoder.decode(rcvbuf).toString();
		System.out.println("coucou " + res);
		String[] test = res.split(",");
		
		int size = Integer.parseInt(test[(test.length-1)]);
		
		int[][] values = new int[size][size];
		for (int i = 0; i < size*size; i++ ) 
		{
			values[i/size][i%size] = Integer.parseInt(test[i]);
			//System.out.println(values[i/size][i%size] + " = " + Integer.parseInt(test[i]));
		}
		
		int index = Integer.parseInt(test[test.length-2]);
		this.controller = new ClientController(size, values, this, index);
	
		
		/*
		// request defaultValues
		byte [] defaultValBuff = new String("getDefaultVal").getBytes();
	    buffer.clear();
	    buffer = ByteBuffer.wrap(defaultValBuff);
	    this.socket.write(buffer);
        // receive defVal from server
        rcvbuf = ByteBuffer.allocate(1024);
		nBytes = this.socket.read(rcvbuf);
		rcvbuf.flip();  
		IntBuffer values =
				   ByteBuffer.wrap(rcvbuf.array())
				     .order(ByteOrder.BIG_ENDIAN)
				     .asIntBuffer();
				 int[] array = new int[values.remaining()]; // ne marche que si le tableau est un tableau simple et pas un [][]
				 values.get(array);
		// soit changer le int[] array = new... soit changer le format de values
		
		
		
		*/
		
		
		// client initialisé et prèt a etre utilisé
		// la maj en fonction des directions est presque la meme operation
	}
	
	
	
	public void sendDown() throws IOException{
		System.out.println("SEND DOWN");
		byte [] downBuff = new String("down").getBytes();
		ByteBuffer buffer = ByteBuffer.wrap(downBuff);
        this.socket.write(buffer);
        
        // receive val
       ByteBuffer rcvbuf = ByteBuffer.allocate(1024);
		int nBytes = this.socket.read(rcvbuf);
		rcvbuf.flip();  
		IntBuffer values =
				   ByteBuffer.wrap(rcvbuf.array())
				     .order(ByteOrder.BIG_ENDIAN)
				     .asIntBuffer();
				 int[] array = new int[values.remaining()]; // ne marche que si le tableau est un tableau simple et pas un [][]
				 values.get(array);
					        
	}

    public static void main (String [] args)
            throws IOException, InterruptedException {
    	new Client(8189);
    	
    	/*
    	
        InetSocketAddress hostAddress = new InetSocketAddress(InetAddress.getLocalHost(), 8189);
        SocketChannel client = SocketChannel.open(hostAddress);

        System.out.println("Client sending messages to server...");

        // Send messages to server
		
        String [] messages = new String [] {"Time goes fast.", "What now?", "getSize"};

        for (int i = 0; i < messages.length; i++) {

            byte [] message = new String(messages [i]).getBytes();
            ByteBuffer buffer = ByteBuffer.wrap(message);
            client.write(buffer);

            System.out.println(messages [i]);
            buffer.clear();
            Thread.sleep(3000);
        }
        
        // lecture sur la socketchannel
        ByteBuffer rcvbuf = ByteBuffer.allocate(1024);
		  int nBytes = client.read(rcvbuf);
		  rcvbuf.flip();
		  Charset charset = Charset.forName("us-ascii");
		  CharsetDecoder decoder = charset.newDecoder();
		  String res = decoder.decode(rcvbuf).toString();
		  System.out.println(res);*/
    }
    
    public SocketChannel getSocket()
    {
    	return this.socket;
    }
    
    public void sendDirection()
    {
    	
    }
}
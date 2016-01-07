package Client;

import gameModel.Direction;

import java.nio.channels.SocketChannel;
import java.nio.charset.CharacterCodingException;
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
        
        // receive size, values of the grid and index of the grid from server
        ByteBuffer rcvbuf = ByteBuffer.allocate(1024);
		int nBytes = this.socket.read(rcvbuf);
		rcvbuf.flip();  
		Charset charset = Charset.forName("us-ascii");
		CharsetDecoder decoder = charset.newDecoder();
		String res = decoder.decode(rcvbuf).toString();
		System.out.println(res);
		
		//Split the string received
		String[] test = res.split(",");
		
		//get the different values and use it to initiate the client controller
		int size = Integer.parseInt(test[(test.length-1)]);
		
		
		int[][] values = getValues(test,size);
		
		
		int index = Integer.parseInt(test[test.length-2]);
		this.controller = new ClientController(size, values, this, index);
	
		

		
		
		// client initialised and ready for use
	}
	
	
	//return the values of the grid sent by the server which are in test String
	private int[][] getValues(String[] test, int size) 
	{
		int[][] res = new int[size][size];
		for (int i = 0; i < size*size; i++ ) 
		{
			res[i/size][i%size] = Integer.parseInt(test[i]);
			//System.out.println(values[i/size][i%size] + " = " + Integer.parseInt(test[i]));
		}
		
		return res;
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
            throws IOException, InterruptedException 
    {
    	new Client(8189);
    	
    }
    
    public SocketChannel getSocket()
    {
    	return this.socket;
    }



	public void sendDirection(Direction dir, int index, int size) 
	{
		// TODO Auto-generated method stub
		System.out.println(dir.toString());
		StringBuffer direction = new StringBuffer();
		direction.append("move,"+index+","+dir.toString().toLowerCase());
		byte [] directionBuff = direction.toString().getBytes();
        ByteBuffer buffer = ByteBuffer.wrap(directionBuff);
        try {
			this.socket.write(buffer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        ByteBuffer rcvbuf = ByteBuffer.allocate(1024);
		try 
		{
			int nBytes = this.socket.read(rcvbuf);
		} catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rcvbuf.flip();  
		Charset charset = Charset.forName("us-ascii");
		CharsetDecoder decoder = charset.newDecoder();
		try 
		{
			String res = decoder.decode(rcvbuf).toString();
			System.out.println(res);
			//Split the string received
			String[] test = res.split(",");
			int[][] newValues = getValues(test, size);
			int score = Integer.parseInt(test[test.length-1]);
			this.controller.update(newValues, score);
		} catch (CharacterCodingException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
    





}
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
/**
 * This class is the main class of our Client
 * @author Julien Bellec & Paul Bourgeois
 *
 */
public class Client {
	
	private ClientController controller;
	private final int port;
	private SocketChannel socket;
	

	/**
	 * this constructor connect the client to the server with a server socket and initiate the client's client controller which will initiate the frame of the game
	 * @param port : the port used by the socket
	 * @throws IOException
	 */
	public Client(int port) throws IOException
	{
		this.port = port;
        InetSocketAddress hostAddress = new InetSocketAddress(InetAddress.getLocalHost(), this.port);
        this.socket = SocketChannel.open(hostAddress);
        /*-- server should initiate a model creation for each new connection--*/
      
        System.out.println("Client sending messages to server...");

        /*-- request size to server--*/
        byte [] sizeBuff = new String("getSize").getBytes();
        ByteBuffer buffer = ByteBuffer.wrap(sizeBuff);
        this.socket.write(buffer);
        
        System.out.println("getSize sent");
        
        /* --receive size, values of the grid and index of the grid from server--*/
        ByteBuffer rcvbuf = ByteBuffer.allocate(1024);
		int nBytes = this.socket.read(rcvbuf);
		rcvbuf.flip();  
		Charset charset = Charset.forName("us-ascii");
		CharsetDecoder decoder = charset.newDecoder();
		String res = decoder.decode(rcvbuf).toString();
		//System.out.println(res);
		
		/*--Split the string received--*/
		String[] test = res.split(",");
		
		/*--get the different values and use it to initiate the client controller--*/
		int size = Integer.parseInt(test[(test.length-1)]);
		
		
		int[][] values = getValues(test,size);
		
		
		int index = Integer.parseInt(test[test.length-2]);
		this.controller = new ClientController(size, values, this, index);
	
		

		
		
		/*-- client initialised and ready for use --*/
	}
	
	
	/**
	 * return the values of the grid sent by the server which are in test String
	 * @param values
	 * @param size
	 * @return Values of the grid of the good size
	 */
	private int[][] getValues(String[] values, int size) 
	{
		int[][] res = new int[size][size];
		for (int i = 0; i < size*size; i++ ) 
		{
			res[i/size][i%size] = Integer.parseInt(values[i]);
			//System.out.println(values[i/size][i%size] + " = " + Integer.parseInt(test[i]));
		}
		
		return res;
	}





    public static void main (String [] args)
            throws IOException, InterruptedException 
    {
    	new Client(8189);
    	
    }
    
    /**
     * 
     * @return the socket of this client
     */
    public SocketChannel getSocket()
    {
    	return this.socket;
    }


    /**
     * Send the direction to the server and compute the result of this Direction
     * @param dir :		the desired direction
     * @param index :	index of the client's grid
     * @param size :	the size of the grid
     */
	public void sendDirection(Direction dir, int index, int size) 
	{
		System.out.println(dir.toString());
		StringBuffer direction = new StringBuffer();
		direction.append("move,"+index+","+dir.toString().toLowerCase());
		byte [] directionBuff = direction.toString().getBytes();
        ByteBuffer buffer = ByteBuffer.wrap(directionBuff);
        try {
			this.socket.write(buffer);
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        ByteBuffer rcvbuf = ByteBuffer.allocate(1024);
		try 
		{
			int nBytes = this.socket.read(rcvbuf);
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
		rcvbuf.flip();  
		Charset charset = Charset.forName("us-ascii");
		CharsetDecoder decoder = charset.newDecoder();
		try 
		{
			String res = decoder.decode(rcvbuf).toString();
			//System.out.println(res);
			
			/*--Split the string received--*/
			String[] test = res.split(",");
			int[][] newValues = getValues(test, size);
			int score = Integer.parseInt(test[test.length-1]);
			this.controller.update(newValues, score);
		} catch (CharacterCodingException e) 
		{
			e.printStackTrace();
		}
		
		
	}
    





}
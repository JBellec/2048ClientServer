package Server;


import gameModel.*;

import java.nio.channels.SocketChannel;
import java.util.HashMap;

/**
 * Controller of our MVC model from the server part of the application
 * @author Julien Bellec & Paul Bourgeois
 *
 */
public class ServerController 
{
	private static final int SIZE = 4;
	private Game model;
	private HashMap<Integer, Grid> clientsGrid;
	private int[] values;
	private int index;
	private Server server;
	
	
	/**
	 * Initialise the ServerController with his model and a HashMap of grids of the different clients
	 * @param server :	The server of this Controller
	 */
	public ServerController(Server server) 
	{
		this.clientsGrid = new HashMap<Integer, Grid>();
		this.values = new int[SIZE*SIZE];
		this.model = new Game();
		this.index = 0;
		this.server = server;
	}

	/**
	 * init the grid for the client and send back to him few values
	 * @return a Buffer which will be sent to the client after a string convertion
	 */
	public StringBuffer addClient() 
	{
		this.index++;
		/*-- initialise the grid for the new client) --*/
		clientsGrid.put(this.index, new Grid());
		
		/*-- add 2 tiles randomly in the empty grid --*/
		model.addRandomTile(clientsGrid.get(this.index));
		model.addRandomTile(clientsGrid.get(this.index));
		
		/* -- prepare the string to send to the client --*/
		return addIndexAndSize(copyGridValues(clientsGrid.get(this.index)));
		
		
	}
	
	/**
	 * prepare the stringbuffer which will be sent to the client
	 * @param buffer will be completed with the index of the grid and the size of the grid
	 * @return the completed buffer
	 */
	private StringBuffer addIndexAndSize(StringBuffer buffer) 
	{
		
		buffer.append(this.index+","+SIZE);
		System.out.println(buffer.toString());
		return buffer;
	}

	


	/**
	 * copy the grid values in a Buffer
	 * @param grid
	 * @return A buffer with the values of the grid separated by commas
	 */
	private StringBuffer copyGridValues(Grid grid) 
	{
		StringBuffer result = new StringBuffer();
		for (int y = 0; y < SIZE; y++) 
			for (int x = 0; x < SIZE; x++) 
				result.append(grid.getValue(x, y)+",");
			
		
		return result;
	}

	
	
	/**
	 * Prepare the string which will be used to move the grid and send the resulting values to the server
	 * @param string incoming string 
	 * @return The Buffer which will be send to the client with the new grid values
	 */
	public StringBuffer move(String string) 
	{
		string = string.replace("move,", "");
		String[] result = string.split(",");
		//System.out.println("you are moving to the : " + result[1]);
		return (moveReceived(Direction.parseDir(result[1].toLowerCase()), Integer.parseInt(result[0].toString())));
	}
	/**
	 * move the tiles in one direction in the grid and prepare the string to send back to the client in order to update the frame and the score of the client
	 * @param dir :			The direction choosen
	 * @param gridIndex :	the index of the grid which belongs to the proper client
	 * @return The Buffer which will be send to the client with the new grid values
	 */
	private StringBuffer moveReceived(Direction dir, int gridIndex) 
	{
		StringBuffer toSend = new StringBuffer();
		if (clientsGrid.containsKey(gridIndex)) 
		{
			Grid grid = clientsGrid.get(gridIndex);
			model.play(dir, grid);
			StringBuffer result = copyGridValues(grid);
			result.append(model.getScore(grid));
			return result;
		}
		return toSend;
		
	}

	public void resetGrid(String string) 
	{
		string = string.replace("newGame,", "");
		String[] result = string.split(",");
		
		int gridIndex = Integer.parseInt(result[result.length-1]);
		int[] newGridValues = new int[SIZE*SIZE];
		for (int x = 0; x < SIZE *SIZE; x++)
		{
			newGridValues[x] = Integer.parseInt(result[x]);
			System.out.println(newGridValues[x]);
		}
		if (clientsGrid.containsKey(gridIndex))
				clientsGrid.get(gridIndex).setGridValues(newGridValues);
		
	}
}

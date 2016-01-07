package Server;


import gameModel.*;

import java.nio.channels.SocketChannel;
import java.util.HashMap;


public class ServerController 
{
	private static final int SIZE = 4;
	private Game model;
	private HashMap<Integer, Grid> clientsGrid;
	private int[] values;
	private int index;
	private Server server;
	
	public ServerController(Server server) 
	{
		this.clientsGrid = new HashMap<Integer, Grid>();
		this.values = new int[SIZE*SIZE];
		this.model = new Game();
		this.index = 0;
		this.server = server;
	}

	public StringBuffer addClient() 
	{
		this.index++;
		//initialise the grid for the new client)
		clientsGrid.put(this.index, new Grid());
		model.addRandomTile(clientsGrid.get(this.index));
		model.addRandomTile(clientsGrid.get(this.index));
		
		
		
		return sendInit(this.index);
		
		
	}

	private StringBuffer sendInit(int index) 
	{
		// TODO Auto-generated method stub
		Grid clientGrid = clientsGrid.get(index);
		

		return sendSizeAndValues(copyGridValues(clientGrid), index);
	}

	
	
	private StringBuffer sendSizeAndValues(StringBuffer buffer, int index) 
	{
		
		buffer.append(index+","+SIZE);
		System.out.println(buffer.toString());
		return buffer;
	}

	


	// copy the grid values in a Buffer
	private StringBuffer copyGridValues(Grid grid) 
	{
		StringBuffer result = new StringBuffer();
		for (int y = 0; y < SIZE; y++) 
		{
			for (int x = 0; x < SIZE; x++) 
			{
				//this.values[x+(y*4)] = grid.getValue(y, x);
				//System.out.println(grid.getValue(x,y)+ " = " +this.values[x+y]);
				result.append(grid.getValue(x, y)+",");
			}
		}
		return result;
	}

	
	
	public StringBuffer move(String res) 
	{
		System.out.println("result = " + res);
		String[] result = res.split(",");
		System.out.println("you are moving to the : " + result[1]);
		return (moveReceived(Direction.parseDir(result[1].toLowerCase()), Integer.parseInt(result[0].toString())));
		
	}
	
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
}

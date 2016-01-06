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

	public StringBuffer addClient(SocketChannel socket) 
	{
		this.index++;
		//initialise the grid for the new client)
		clientsGrid.put(this.index, new Grid());
		model.addRandomTile(clientsGrid.get(this.index));
		model.addRandomTile(clientsGrid.get(this.index));
		
		
		
		return sendInit(this.index, socket);
		
		
	}

	private StringBuffer sendInit(int index, SocketChannel socket) 
	{
		// TODO Auto-generated method stub
		Grid clientGrid = clientsGrid.get(index);
		copyGridValues(clientGrid);

		return sendSizeAndValues(SIZE, this.values, index, socket);
	}

	private StringBuffer sendSizeAndValues(int gridSize, int[] values, int index, SocketChannel socket) 
	{
		StringBuffer toSend = new StringBuffer();
		for (int i = 0 ; i < values.length; i++ )
		{
			toSend.append(values[i]+",");
		}
		toSend.append(index+","+gridSize);
		return toSend;
	}

	
	
	
	private void moveReceived(Direction dir, int gridIndex) 
	{
		if (clientsGrid.containsKey(gridIndex)) 
		{
			Grid grid = clientsGrid.get(gridIndex);
			model.play(dir, grid);
			copyGridValues(grid);
			sendToClient(this.values, model.getScore(grid));
			
		}
	}

	private void sendToClient(int[] values, int score) 
	{
		// TODO Auto-generated method stub
		// envoi au client les nouvelles valeurs à afficher et le score
	}

	// copy the grid values in this.values
	private void copyGridValues(Grid grid) 
	{
		for (int y = 0; y < SIZE; y++) 
		{
			for (int x = 0; x < SIZE; x++) 
			{
				this.values[x+y] = grid.getValue(x, y);
			}
		}
	}
}

package Server;


import gameModel.Grid;
import gameModel.Game;
import gameModel.*;

import java.util.HashMap;

import Client.Client;

public class ServerController 
{
	private static final int SIZE = 4;

	private Game model;
	//private ArrayList<Grid> clientsGrid;
	private HashMap<Integer, Grid> clientsGrid;
	private int[][] values;
	private int index;

	public ServerController() 
	{
		this.values = new int[SIZE][SIZE];
		this.model = new Game();
		this.index = 0;
	}

	private void addClient() 
	{
		//initialise the grid for the new client)
		clientsGrid.put(this.index, new Grid());
		model.addRandomTile(clientsGrid.get(this.index));
		model.addRandomTile(clientsGrid.get(this.index));
		
		
		
		sendInit(this.index);
		this.index++;
	}

	private void sendInit(int index) 
	{
		// TODO Auto-generated method stub
		Grid clientGrid = clientsGrid.get(index);
		copyGridValues(clientGrid);

		sendSizeAndValues(SIZE, this.values, index);
	}

	private void sendSizeAndValues(int gridSize, int[][] clientValues, int index) 
	{
		// TODO Auto-generated method stub
		// envoyer au client via le serveur gridSize et clientValues ainsi que l'index du client
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

	private void sendToClient(int[][] gridValues, int score) 
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
				this.values[x][y] = grid.getValue(x, y);
			}
		}
	}
}

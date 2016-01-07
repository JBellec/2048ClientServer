package gameModel;

import java.util.Random;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;


import ui.GameFrame;

/**
 * Our game Model
 * @author Julien Bellec & Paul Bourgeois
 *
 */
public class Game 
{

	protected Grid copy;
	protected Random rnd;

	public Game() 
	{
		copy = new Grid();
		rnd = new Random();
	}



	/**
	 * Move the tiles of the grid in dir direction then add an random tile in the grid
	 * @param dir : 	the choosen direction
	 * @param grid :	the choosen grid
	 */
	public void play(Direction dir, Grid grid) 
	{
		if (!grid.gameOver()) {
			grid.getCopy(copy);
			if (grid.move(dir)) {
				addRandomTile(grid);
	}
			try 
			{
				Thread.sleep(200);
			} catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * gives the current score of the grid
	 * @param grid
	 * @return the score of the grid
	 */
	public int getScore(Grid grid) 
	{
		return grid.getScore();
	}


	/**
	 *  add a random tile in the grid
	 * @param grid
	 */
	public void addRandomTile(Grid grid) 
	{
		grid.addValue(rnd.nextDouble() < 0.9 ? 2 : 4,
				rnd.nextInt(grid.getEmptyCount()));
	}
	

}

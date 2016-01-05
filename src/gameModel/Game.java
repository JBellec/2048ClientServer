package gameModel;

import java.util.Random;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;


import ui.GameFrame;

public class Game 
{

	protected Grid copy;
	protected Random rnd;

	public Game() 
	{
		copy = new Grid();
		rnd = new Random();
	}

	public void setSeed(long seed) 
	{
		rnd.setSeed(seed);
	}


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

	public int getScore(Grid grid) 
	{
		return grid.getScore();
	}


	
	public void addRandomTile(Grid grid) 
	{
		grid.addValue(rnd.nextDouble() < 0.9 ? 2 : 4,
				rnd.nextInt(grid.getEmptyCount()));
	}
	

}

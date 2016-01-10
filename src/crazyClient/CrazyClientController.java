package crazyClient;

import gameModel.Direction;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

import javax.swing.JFrame;

import ui.GameFrame;

/**
 * This is the Controller of our MVC model from the client part of the application
 * @author Julien Bellec & Paul Bourgeois
 *
 */
public class CrazyClientController implements Runnable
{
	private GameFrame frame;
	private CrazyClient client;
	private int index;
	private int size;
	
	
	/**
	 * Initialise the frame of the game
	 * @param size :	size of the game
	 * @param values :	values of the tiles on the game
	 * @param client :	client of this controller
	 * @param index	:	index of the client. Used to specify to the server which grid needs to be modified
	 */
	public CrazyClientController(int size, int[][] values, CrazyClient client, int index)
	{
		
		this.client = client;
		this.index = index;
		this.size = size;
		frame = new GameFrame(this.size);
		frame.addComponents();
		frame.update(values, 0);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
		
		setWindowListener();
	}
	
	
	


	/**
	 *  WindowListener to close the socket when closing the frame 
	 */
	private void setWindowListener()
	{
		frame.addWindowListener(new WindowListener()
		{
			

			public void windowClosing(WindowEvent e) 
			{
				try {
					client.getSocket().close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			public void windowActivated(WindowEvent arg0) {}

			public void windowClosed(WindowEvent arg0) {}

			public void windowDeactivated(WindowEvent arg0) {}

			public void windowDeiconified(WindowEvent arg0) {}

			public void windowIconified(WindowEvent arg0) {}

			public void windowOpened(WindowEvent arg0) {}

		});
	}


	
	
	/**
	 * ask to the client to sent the direction detected by the KeyListener
	 * @param dir
	 */
	private void sendMove(Direction dir)  
	{
		client.sendDirection(dir, this.index, this.size);
		//client.sendGetScore
	}
	
	
	/**
	 * update the game frame
	 * @param values :	values of the tiles on the game
	 * @param score :	score of the game
	 */
	public void update(int[][] values, int score) 
	{
			frame.update(values, score);
	}


	@Override
	public void run() {
		while(true)
		{
			sendMove(Direction.UP);
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sendMove(Direction.DOWN);
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sendMove(Direction.LEFT);
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sendMove(Direction.RIGHT);
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	
}


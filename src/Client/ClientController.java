package Client;

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
public class ClientController 
{
	private GameFrame frame;
	private Client client;
	private int index;
	private int size;
	
	
	/**
	 * Initialise the frame of the game
	 * @param size :	size of the game
	 * @param values :	values of the tiles on the game
	 * @param client :	client of this controller
	 * @param index	:	index of the client. Used to specify to the server which grid needs to be modified
	 */
	public ClientController(int size, int[][] values, Client client, int index)
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
		
		setKeyListener();
		setWindowListener();
	}
	
	
	/**
	 * KeyListener to look for the player inputs
	 */
	private void setKeyListener() {
		frame.addKeyListener(new KeyAdapter() {
		    public void keyReleased(KeyEvent e) {
		        int code = e.getKeyCode();
		        switch (code) {
		        case KeyEvent.VK_UP:
		        case KeyEvent.VK_KP_UP:
		        	sendMove(Direction.UP);
		        	break;
		        case KeyEvent.VK_DOWN:
		        case KeyEvent.VK_KP_DOWN:
		        	sendMove(Direction.DOWN);
		        	
		        	break;
		        case KeyEvent.VK_LEFT:
		        case KeyEvent.VK_KP_LEFT:
		        	sendMove(Direction.LEFT);
		        	break;
		        case KeyEvent.VK_RIGHT:
		        case KeyEvent.VK_KP_RIGHT:
		        	sendMove(Direction.RIGHT);
		        	break;
		        }
		        try {
					Thread.sleep(200);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    }
		});
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
	
	
}


package Client;



import gameModel.Direction;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.JFrame;

import ui.GameFrame;

public class ClientController 
{
	
	private GameFrame frame;
	private Client client;
	private int index;
	
	public ClientController(int size, int[][] values, Client client, int index)
	{
		
		this.client = client;
		this.index = index;
		
		frame = new GameFrame(size);
		frame.addComponents();
		frame.update(values, 0);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
	}
	
	
	
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
		    }
		});
	}
	
	
	
	private void sendMove(Direction dir) 
	{
		switch (dir) {
		case DOWN:
			try {
				client.sendDown();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		default:
			break;
		}
		//demande au Client d'envoyer au serveur move(dir)
		
		//client.sendGetScore
	}
	
	private void update(int[][] values, int score) 
	{
			frame.update(values, score);
	}
	
	
}


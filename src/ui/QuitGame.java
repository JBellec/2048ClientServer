package ui;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.AbstractAction;
import Client.ClientController;

public class QuitGame extends AbstractAction 
{
	private ClientController cc;


	public QuitGame(ClientController cc, String quit)
	{
		super(quit);
		this.cc = cc;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		// TODO Auto-generated method stub
		try {
			cc.getClient().getSocket().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.exit(0);
	}

}

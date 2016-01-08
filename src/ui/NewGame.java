package ui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import Client.ClientController;

public class NewGame extends AbstractAction 
{
	private ClientController cc;

	public NewGame(ClientController cc, String quit)
	{
		super(quit);
		this.cc = cc;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		cc.newGame();
	}



}

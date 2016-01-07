package ui;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;

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
		// TODO Auto-generated method stub
		cc.newGame();
	}



}

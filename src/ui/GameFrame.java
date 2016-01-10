package ui;

import gameModel.Grid;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import Client.ClientController;

/**
 * This is the frame of our game
 * @author Julien Bellec & Paul Bourgeois
 *
 */
public class GameFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private int size;
	private Cell[][] cells;
	private JLabel scoreLabel;
	private ClientController cc;
	
	public GameFrame(int size, ClientController clientController) 
	{
		super("2048");
		this.size = size;
		this.cc = clientController;
	}

	public GameFrame(int size) 
	{
		this.size=size;
		JPanel panel = new JPanel();
		GridLayout layout = new GridLayout(size, size);
		layout.setHgap(10);
		layout.setVgap(10);
		panel.setLayout(layout);
		panel.setBackground(new Color(0xbb, 0xad, 0xa0));
		cells = new Cell[size][size];
		for (int y = 0; y < size; y++)
			for (int x = 0; x < size; x++) {
				cells[y][x] = new Cell();
				cells[y][x].setValue(0);
				panel.add(cells[y][x]);
			}
		scoreLabel = new JLabel("Score: 0");
		getContentPane().add(scoreLabel, BorderLayout.NORTH);
		getContentPane().add(new JSeparator(), BorderLayout.CENTER);
		getContentPane().add(panel, BorderLayout.SOUTH);
	}

	public void addComponents() {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu menu = new JMenu("GameMenu");
		
		JMenuItem newGame = new JMenuItem(new NewGame(this.cc, "New Game"));
		menu.add(newGame);
		
		JMenuItem quit = new JMenuItem(new QuitGame(this.cc, "Quit"));
		menu.add(quit);
		
		menuBar.add(menu);
		menu.add(newGame);
		menu.add(quit);
		
		setJMenuBar(menuBar);
		JPanel panel = new JPanel();
		GridLayout layout = new GridLayout(size, size);
		layout.setHgap(10);
		layout.setVgap(10);
		panel.setLayout(layout);
		panel.setBackground(new Color(0xbb, 0xad, 0xa0));
		cells = new Cell[size][size];
		for (int y = 0; y < size; y++)
			for (int x = 0; x < size; x++) {
				cells[y][x] = new Cell();
				cells[y][x].setValue(0);
				panel.add(cells[y][x]);
			}
		scoreLabel = new JLabel("Score: 0");
		getContentPane().add(scoreLabel, BorderLayout.NORTH);
		getContentPane().add(new JSeparator(), BorderLayout.CENTER);
		getContentPane().add(panel, BorderLayout.SOUTH);
	}

	public void update(Grid grid) {
		for (int y = 0; y < size; y++)
			for (int x = 0; x < size; x++)
				cells[y][x].setValue(grid.getValue(x, y));
		scoreLabel.setText("Score: " + grid.getScore());
	}

	
	public void update(int[][] values, int score) 
	{
		// TODO Auto-generated method stub
		for (int y = 0; y < size; y++)
			for (int x = 0; x < size; x++)
				cells[x][y].setValue(values[x][y]);
		scoreLabel.setText("Score: " + score);
	}
}

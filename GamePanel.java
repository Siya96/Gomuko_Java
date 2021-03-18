package lab4.gui;



import java.awt.Color;


import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import lab4.data.GameGrid;
import lab4.data.GomokuGameState;

/**
 * 
 * @author Siyabend Revend & Philip Wenkel
 *
 */


/**
 * A panel providing a graphical view of the game board
 */

public class GamePanel extends JPanel implements Observer {

	
	private final int UNIT_SIZE = 30;
	private GameGrid grid;

	/**
	 * The constructor
	 * 
	 * @param grid The grid that is to be displayed
	 */
	public GamePanel(GameGrid grid) {
		this.grid = grid;
		grid.addObserver(this);
		Dimension d = new Dimension(grid.getSize() * UNIT_SIZE + 1, grid.getSize() * UNIT_SIZE + 1);
		this.setMinimumSize(d);
		this.setPreferredSize(d);
		this.setBackground(Color.WHITE);
		
		
	}

	/**
	 * Returns a grid position given pixel coordinates of the panel
	 * 
	 * @param x the x coordinates
	 * @param y the y coordinates
	 * @return an integer array containing the [x, y] grid position
	 */
	public int[] getGridPosition(int x, int y) {

		int[] getGridPos = new int[2];

		getGridPos[0] = x / UNIT_SIZE;
		getGridPos[1] = y / UNIT_SIZE;

		return getGridPos;
	}

	public void update(Observable arg0, Object arg1) {
		
		
		this.repaint();
	}

	public void paintComponent(Graphics g) {
		
		
		
		super.paintComponent(g);
		
		
		//Implementing the help-methods

		drawBoard(g);
		drawMoves(g);

	}

	//This help-method draws the rectangles on the grid.
	private void drawBoard(Graphics g) {

		int gridSize = grid.getSize();

		for (int i = 0; i < gridSize; i ++) {
			
			for (int j = 0; j < gridSize;  j ++) {
				

			// Paints the rectangles.
			g.setColor(Color.black);
			g.drawOval(i * UNIT_SIZE , j * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);


			}

		}

	}

	
	//This help-method draws the players "characters".
	private void drawMoves(Graphics g) {

		int gridSize = grid.getSize();

		for (int i = 0; i < gridSize; i++) {

			for (int j = 0; j < gridSize; j++) {

				int gridLock = grid.getLocation(i, j);

				// Paints the Me and Opponent oval
				if (gridLock == grid.ME) {

					g.setColor(Color.green);
					g.drawOval((i * UNIT_SIZE) , (j * UNIT_SIZE), UNIT_SIZE, UNIT_SIZE);
				}
				if (gridLock == grid.OTHER) {

					g.setColor(Color.red);
					g.drawOval((i * UNIT_SIZE), (j * UNIT_SIZE), UNIT_SIZE, UNIT_SIZE);
				}

			}
		}
	}



}

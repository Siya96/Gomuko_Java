package lab4.data;

import java.util.Arrays;
import java.util.Observable;

/**
 * Represents the 2-d game grid
 */

/**
 * 
 * @author Siyabend Revend & Philip Wenkel
 *
 */

public class GameGrid extends Observable {

	public static void main(String[] args) {

	}
	// The variables are declared static due to the reason
	// that all objects will be given the same value. The variables are also
	// declared final
	// so that you wont be able to change their value. The variables states the
	// current state
	// of a position on the gird and therefore represents each player and a third
	// one called EMPTY.
	public static final int EMPTY = 0;
	public static final int ME = 1;
	public static final int OTHER = 2;

	private int[][] gameGridArr;

	private final int INROW = 5;

	/**
	 * Constructor
	 * 
	 * @param size The width/height of the game grid
	 */
	public GameGrid(int size) {

		gameGridArr = new int[size][size];

	}

	/**
	 * Reads a location of the grid
	 * 
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @return the value of the specified location
	 */

	public int getLocation(int x, int y) {

		return gameGridArr[y][x];

	}

	/**
	 * Returns the size of the grid
	 * 
	 * @return the grid size
	 */
	public int getSize() {

		return gameGridArr.length;
	}

	/**
	 * Enters a move in the game grid
	 * 
	 * @param x      the x position
	 * @param y      the y position
	 * @param player
	 * @return true if the insertion worked, false otherwise
	 */
	public boolean move(int x, int y, int player) {

		// Checks if a certain position/square is occupied
		// by checking the square's x- and y-axis.

		// If the given position should be empty then it's given to the
		// player who chose that certain square.
		if (gameGridArr[y][x] == EMPTY) {

			gameGridArr[y][x] = player;

			// When we assign the empty-square to a certain player
			// then it will result with us successfully changing the board, therefore
			// we also have to call the methods setChanged() and notifyObeservers()
			// so that we can notify potential observers.
			setChanged();
			notifyObservers();
			return true;

		}

		else {

			return false;
		}
	}

	/**
	 * Clears the grid of pieces
	 */
	public void clearGrid() {

		// We clear the grid of pieces with the help
		// of a for-each loop.
		
		// the objects in the first array are arrays.
		for (int i = 0; i < gameGridArr.length; i++) {
			
			// The objects of the second array are integers.
			for(int j = 0; j < gameGridArr.length; j++) {
				
				gameGridArr[i][j] = EMPTY;
			}

		}
		// As final measure we notify the observers of the
		// changes that has been made.
		setChanged();
		notifyObservers();
	}

	/**
	 * Check if a player has 5 in row
	 * 
	 * @param player the player to check for
	 * @return true if player has 5 in row, false otherwise
	 */

	// Create help-methods to this method.
	public boolean isWinner(int player) {

		if (diagonallyRightDown(player) == true) {

			return true;

		}
		if (diagonallyRightUp(player) == true) {

			return true;
		}

		if (horizontal(player) == true) {

			return true;
		}

		if (vertical(player) == true) {

			return true;
		}

		return false;
	}

	// The first for-loop checks the iterates through the horizontal lines while the
	// second for-loop
	// iterates through the columns. The outer loop iterates on the x-axis and the
	// inner loop iterates
	// on the y-axis.
	private boolean vertical(int player) {

		int countRows = 0;

		for (int x = 0; x < gameGridArr.length; x++) {
			
			countRows = 0;

			for (int y = 0; y < gameGridArr[x].length; y++) {

				if (getLocation(x, y) == player) {

					countRows += 1;

					if (countRows == INROW) {

						return true;
					}
				} 
				else {
					countRows = 0;
				}

			}
		}
		return false;

	}

	// The first for-loop checks the iterates through the columns while the second
	// for-loop
	// iterates through the lines. The outer loop iterates on the y-axis and the
	// inner loop iterates
	// on the x-axis.
	private boolean horizontal(int player) {

		int countRows = 0;

		for (int y = 0; y < gameGridArr.length; y++) {
			
			countRows = 0;

			for (int x = 0; x < gameGridArr[y].length; x++) {

				if (getLocation(x, y) == player) {

					countRows += 1;

					if (countRows == INROW) {

						return true;
					}
				} else {
					countRows = 0;
				}

			}
		}
		return false;

	}
	
	private boolean diagonallyRightDown(int player) {
		int countRows = 0;

		for (int y = 0; y < gameGridArr.length; y++) {
			
			countRows = 0;

			for (int x = 0; x < gameGridArr.length; x++) {

				// Creating a third loop that adds one to both of the indexes without changing
				// their
				// value. By doing this we are allowed to check after any "five" rows
				// diagonally.
				for (int z = y, i = x; z < gameGridArr.length && i < gameGridArr.length; z++, i++) {
					if (getLocation(z, i) == player) {

						countRows += 1;

						if (countRows >= INROW) {

							return true;
						}

					} else {

						countRows = 0;
					}
				}

			}

		}

		return false;

	}
	

	private boolean diagonallyRightUp(int player) {


		int countRows = 0;

		for (int y = 1; y < gameGridArr.length; y++) {
			
			countRows = 0;

			for (int x = 0; x < gameGridArr.length; x++) {

				//Substituting variables since double variables only gave me trouble.
				for (int z = y, i = x; z > 0 && i < gameGridArr.length; z--, i++) {
					if (getLocation(z, i) == player) {

						countRows++;

						if (countRows == INROW) {

							return true;
						}
					} else {

						countRows = 0;
					}

				}

			}

		}
		return false;

	}



}

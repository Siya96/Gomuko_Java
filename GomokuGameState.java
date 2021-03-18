/*
 * Created on 2007 feb 8
 */
package lab4.data;

import java.util.Observable;
import java.util.Observer;

import lab4.client.GomokuClient;

/**
 * 
 * @author Siyabend Revend & Philip Wenkel
 *
 */

/**
 * Represents the state of a game
 */

public class GomokuGameState extends Observable implements Observer{

   // Game variables
	public final static int DEFAULT_SIZE = 18;
	private GameGrid gameGrid;
	
    //Possible game states. These variables are for which player's turn it currently is.
	private final int NOT_STARTED = 0;
	private final int MY_TURN = 1;
	private final int OTHERS_TURN = 2;
	private final int FINISHED = 3;
	
	//The variable currentState shall at any given moment 
	//in time be equal to one of the four possible game states above.
	private int currentState;
	
	//This variable is an object responsible for communicating with 
	//other players.
	private GomokuClient client;
	
	//A string variable which holds a text that eventually and
	//which eventually gets displayed if something essential should happen.
	private String message;
	
	/**
	 * The constructor
	 * 
	 * @param gc The client used to communicate with the other player
	 */
	public GomokuGameState(GomokuClient gc){
		client = gc;
		client.addObserver(this);
		gc.setGameState(this);
		currentState = NOT_STARTED;
		gameGrid = new GameGrid(DEFAULT_SIZE);
	}
	

	/**
	 * Returns the message string
	 * 
	 * @return the message string
	 */
	public String getMessageString(){
		
		return message;
	}
	
	/**
	 * Returns the game grid
	 * 
	 * @return the game grid
	 */
	public GameGrid getGameGrid(){
		
		return gameGrid;
	}

	/**
	 * This player makes a move at a specified location
	 * 
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public void move(int x, int y){
		
		if (currentState == MY_TURN) {
			
			//Making a move to a certain location if that spot is free.
			if (gameGrid.move(x, y, GameGrid.ME) == true) {
				
				
				client.sendMoveMessage(x, y);
				
				
				//If the player wins by the next move then
				if (gameGrid.isWinner(GameGrid.ME) == true) {
					
					message = "You have won the game!";
					
					currentState = FINISHED;
				}
				else {
					
					message = "Next player!";
					currentState = OTHERS_TURN;
				}
				
			}
			else {
				
				message = "That spot is occupied!";
			}
		}
		else {
			
			message = "Wait for your turn!";
		}

		setChanged();
		notifyObservers();
	}
	
	/**
	 * Starts a new game with the current client
	 */
	public void newGame(){
		
		gameGrid.clearGrid();
		currentState = OTHERS_TURN;
		
		message = "A new game has started! Your opponent begins.";
		
		client.sendNewGameMessage();
		
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Other player has requested a new game, so the 
	 * game state is changed accordingly
	 */
	public void receivedNewGame(){
		
		gameGrid.clearGrid();
		currentState = MY_TURN;
		
		message = "New game! It is your turn.";
		
		setChanged();
		notifyObservers();
	}
	
	/**
	 * The connection to the other player is lost, 
	 * so the game is interrupted
	 */
	public void otherGuyLeft(){
		
		gameGrid.clearGrid();
		currentState = NOT_STARTED;
		
		message = "The opponent has left the game!";
		
		client.disconnect();
		
		setChanged();
		notifyObservers();
	}
	
	/**
	 * The player disconnects from the client
	 */
	public void disconnect(){
		
		gameGrid.clearGrid();
		currentState = NOT_STARTED;
		
		message = "You have disconnected fron the game!";
		
		//The method disconnect in the client is being called.
		client.disconnect();
		
		setChanged();
		notifyObservers();
		
		
	}
	
	/**
	 * The player receives a move from the other player
	 * 
	 * @param x The x coordinate of the move
	 * @param y The y coordinate of the move
	 */
	public void receivedMove(int x, int y){
		
		gameGrid.move(x, y, GameGrid.OTHER);
		
		//We check if the player has won by the latest move
		if (gameGrid.isWinner(GameGrid.OTHER) == true) {
			
			currentState = FINISHED;
			message = "Your opponent has won!";
				
		}else {

			message = "Your turn!";
			currentState = MY_TURN;
			
		}
		setChanged();
		notifyObservers();
		
		
	}
	
	public void update(Observable o, Object arg) {
		
		switch(client.getConnectionStatus()){
		case GomokuClient.CLIENT:
			message = "Connected! Make a move!";
			currentState = MY_TURN;
			break;
		case GomokuClient.SERVER:
			message = "Wait for your turn...";
			currentState = OTHERS_TURN;
			break;
		}
		setChanged();
		notifyObservers();
		
		
	}
	
}

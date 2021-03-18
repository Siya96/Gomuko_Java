package lab4;

import lab4.client.GomokuClient;
import lab4.data.GomokuGameState;
import lab4.gui.GomokuGUI;


/**
 * 
 * @author Siyabend Revend & Philip Wenkel
 *
 */

public class GomokuMain {

	// Port-number as an argument
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// An if-statement that checks the length of the argument-array,
		// if there is nothing in the array then we're given a default value
		// of at least 9001. If the argument-array does contain something
		// then instead of 9002, the variable "portNum" will be given the first
		// object in that array.

		if (args.length == 0) {

			args = new String[] {"9006", "9007"};

		}
		GomokuClient gomokuClient = new GomokuClient(Integer.parseInt(args[0]));
		GomokuGameState gomokuGameState = new GomokuGameState(gomokuClient);
		GomokuGUI gomokuGUI = new GomokuGUI(gomokuGameState, gomokuClient);
//
		GomokuClient gomokuClient2 = new GomokuClient(Integer.parseInt(args[1]));
		GomokuGameState gomokuGameState2 = new GomokuGameState(gomokuClient2);
		GomokuGUI gomokuGUI2 = new GomokuGUI(gomokuGameState2, gomokuClient2);

	}

}

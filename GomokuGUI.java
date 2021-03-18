package lab4.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;
import lab4.client.GomokuClient;
import lab4.data.GameGrid;
import lab4.data.GomokuGameState;
import java.awt.*;

/**
 * 
 * @author Siyabend Revend & Philip Wenkel
 *
 */

/*
 * The GUI class
 */

public class GomokuGUI implements Observer {

	private GomokuClient client;
	private GomokuGameState gamestate;

	private JLabel messageLabel;
	private JButton newGameButton;
	private JButton connectButton;
	private JButton disconnectButton;

	private GamePanel gameGridPanel;

	/**
	 * The constructor
	 * 
	 * @param g The game state that the GUI will visualize
	 * @param c The client that is responsible for the communication
	 */
	public GomokuGUI(GomokuGameState g, GomokuClient c) {
		this.client = c;
		this.gamestate = g;
		client.addObserver(this);
		gamestate.addObserver(this);

		// Creating a frame that contains the game. Makes the frame to a container.
		JFrame frame = new JFrame("Gomoku");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container contentPane = frame.getContentPane();
		
//		contentPane.setBackground(Color.black);
		
		// Creating a panel for the components which we then add to the JPanel "contentPane".
		JPanel compPanel = new JPanel();
//		compPanel.setBackground(Color.cyan);
		
	
		GamePanel gameGridPanel = new GamePanel(gamestate.getGameGrid());
		
		//Creating a JPanel for the buttons.
		JPanel buttons = new JPanel();
//		buttons.setBackground(Color.pink);
		
		
		// A label to be displayed.
		messageLabel = new JLabel("Welcome to Gomoku!");
		
		
		newGameButton = new JButton("New Game");
		connectButton = new JButton("Connect");
		disconnectButton = new JButton("Disconnect");

		newGameButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				// Connecting to the local host

				gamestate.newGame();

				messageLabel.setText("A new game has started!");

			}
		});

		connectButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				// Connecting to the local host

				new ConnectionWindow(client);

				messageLabel.setText("Connecting to other player!");

			}
		});

		disconnectButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				// Connecting to the local host

				gamestate.disconnect();

				messageLabel.setText("You have disconnected from the game!");

			}

		});

		// Mouse-action.
		gameGridPanel.addMouseListener(new MouseAdapter() {

			public void mouseReleased(MouseEvent e) {

				// e.getX and e.getY gives the x & y position at where the "action" takes place,
				// that's caused by the
				// mouse-click.

				int x = e.getX();
				int y = e.getY();
				int[] positionList = gameGridPanel.getGridPosition(x, y);

				// After the click, one of the players makes a move.
				gamestate.move(positionList[0], positionList[1]);
//				gamestate.move(gameGridPanel.getGridPosition(e.getX(), e.getY())[0], gameGridPanel.getGridPosition(e.getX(), e.getY())[1]);

			}
		});
		
		
		buttons.add(connectButton);
		buttons.add(newGameButton);
		buttons.add(disconnectButton);
		
		compPanel.add(gameGridPanel);
		compPanel.add(messageLabel);
		compPanel.add(buttons);
		
		
		
		
		int buttonsWidth = buttons.getPreferredSize().width;
		int panelHeight = gameGridPanel.getPreferredSize().height + 60;
		
		if ( buttonsWidth > gameGridPanel.getPreferredSize().width) {
			
			compPanel.setPreferredSize(new Dimension(buttonsWidth, panelHeight));
			
		}
		else {
			compPanel.setPreferredSize(new Dimension(gameGridPanel.getPreferredSize().width, panelHeight));
		}
		
		
		SpringLayout springLayout = new SpringLayout();
		
		
		//Connecting all the components to each other as preferred, by using co-ordinates of pixels.
		springLayout.putConstraint(SpringLayout.NORTH, buttons, 5, SpringLayout.SOUTH, gameGridPanel);
		
		springLayout.putConstraint(SpringLayout.NORTH, messageLabel, 0, SpringLayout.SOUTH, buttons);
		
		springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, gameGridPanel, 0, SpringLayout.HORIZONTAL_CENTER, compPanel);
		
		springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, buttons, 0, SpringLayout.HORIZONTAL_CENTER, compPanel);
		
		springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, messageLabel, 0, SpringLayout.HORIZONTAL_CENTER, compPanel);
		
		
		
		//Setting the springLayout to the JPanel "compPanel".
		compPanel.setLayout(springLayout);
		
		contentPane.setLayout(new FlowLayout());
		
		//Adding the JPanel with the components to another JPanel.
		contentPane.add(compPanel);
		
		
		
		
		
		newGameButton.setEnabled(false);
		disconnectButton.setEnabled(false);
		
		
		frame.pack();

		frame.setVisible(true);
		

		
		
		
		

//		Box box1 = Box.createHorizontalBox();
//
//		
//		
//		box1.add(connectButton);
//		box1.add(newGameButton);
//		box1.add(disconnectButton);
//
//		Box box2 = Box.createHorizontalBox();
//
//		box2.add(messageLabel);
//		
//		
//		
//		Box box3 = Box.createHorizontalBox();
//		
//		box3.add(gameGridPanel);
//
//		
//		
//		Box box4 = Box.createVerticalBox();
//
//		box4.add(box3);
//		box4.add(box2);
//		box4.add(box1);
//
//		
//		contentPane.add(box4);
//
//		// Set the "newGame"- and "disconnect"-buttons as false before connecting.
//		newGameButton.setEnabled(false);
//		disconnectButton.setEnabled(false);
//
//		frame.setContentPane(contentPane);
//
//		frame.pack();
//
//		frame.setVisible(true);

	}

	public void update(Observable arg0, Object arg1) {

		// Update the buttons if the connection status has changed
		if (arg0 == client) {
			if (client.getConnectionStatus() == GomokuClient.UNCONNECTED) {
				connectButton.setEnabled(true);
				newGameButton.setEnabled(false);
				disconnectButton.setEnabled(false);
			} else {
				connectButton.setEnabled(false);
				newGameButton.setEnabled(true);
				disconnectButton.setEnabled(true);
			}
		}

		// Update the status text if the gamestate has changed
		if (arg0 == gamestate) {
			messageLabel.setText(gamestate.getMessageString());
		}

	}

}

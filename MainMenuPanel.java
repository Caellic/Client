import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class MainMenuPanel extends JPanel implements MouseListener{
	Game gamePanel = new Game();
	ChatPanel chatPanel = new ChatPanel();
	UserPanel users = new UserPanel();
	
	JPanel  innerContainer, jbtContainer;
	JLabel  lblMainMenu;
	JButton jbtStartGame, jbtJoinGame, jbtOptions, jbtLogout, jbtExit;
	
	boolean isJoinable = true;
	
	public MainMenuPanel(){
		// set properties of main menu panel
		setLayout(new FlowLayout(FlowLayout.CENTER));
		setBackground(new Color(47, 47, 47));
		setVisible(false);
		
		// Create inner panel, set properties
		innerContainer = new JPanel(new BorderLayout(0, 20));
		innerContainer.setPreferredSize(new Dimension(200, 250));
		innerContainer.setBackground( new Color( 41, 41, 41) );
		innerContainer.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.black),
				BorderFactory.createMatteBorder(15, 25, 15, 25, new Color(41, 41, 41))));
		
		// Create button panel, set properties
		jbtContainer = new JPanel(new GridLayout(5, 1, 0, 0));
		jbtContainer.setBackground( new Color( 41, 41, 41) );
		
		// Create Main Menu label, set props
		lblMainMenu = new JLabel("Main Menu");
		lblMainMenu.setHorizontalAlignment(JLabel.CENTER);
		lblMainMenu.setFont(new Font("SansSerif", Font.BOLD, 26));
		lblMainMenu.setForeground( Color.WHITE );
		
		// Create buttons
		jbtStartGame = new JButton("Start Game");
		jbtJoinGame = new JButton("Join Game");
		jbtOptions = new JButton("Options");
		jbtLogout = new JButton("Logout");
		jbtExit = new JButton("Exit");

		// We have no options at the moment - Disable
		jbtOptions.setEnabled(false);
		
		// Set button properties
		setJBTProps(jbtStartGame);
		setJBTProps(jbtJoinGame);
		setJBTProps(jbtOptions);
		setJBTProps(jbtLogout);
		setJBTProps(jbtExit);
		jbtExit.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		
		// Add mouse listeners for buttons
		jbtStartGame.addMouseListener(this);
		jbtJoinGame.addMouseListener(this);
		jbtOptions.addMouseListener(this);
		jbtLogout.addMouseListener(this);
		jbtExit.addMouseListener(this);
		
		// Add buttons to button container
		jbtContainer.add(jbtStartGame);
		jbtContainer.add(jbtJoinGame);
		jbtContainer.add(jbtOptions);
		jbtContainer.add(jbtLogout);
		jbtContainer.add(jbtExit);
		
		// Add label and button container to inner container
		innerContainer.add(lblMainMenu, BorderLayout.NORTH);
		innerContainer.add(jbtContainer, BorderLayout.CENTER);
		
		// Add innerContainer to panel
		// Also add game panel to this one for now (...)
		add(innerContainer);
		add(gamePanel);
	}

	/** MouseListeners */
	public void mouseClicked(MouseEvent me){}
	public void mouseEntered(MouseEvent me){
		if(me.getSource() == jbtStartGame){
			setJBTPropsHighlight(jbtStartGame);
		}
		if(me.getSource() == jbtJoinGame){
			setJBTPropsHighlight(jbtJoinGame);
		}
		if(me.getSource() == jbtOptions){}
		if(me.getSource() == jbtLogout){
			setJBTPropsHighlight(jbtLogout);
		}
		if(me.getSource() == jbtExit){
			setJBTPropsHighlight(jbtExit);
			jbtExit.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		}
	}
	public void mouseExited(MouseEvent me){
		if(me.getSource() == jbtStartGame){
			setJBTProps(jbtStartGame);
		}
		if(me.getSource() == jbtJoinGame){
			setJBTProps(jbtJoinGame);
		}
		if(me.getSource() == jbtOptions){}
		if(me.getSource() == jbtLogout){
			setJBTProps(jbtLogout);
		}
		if(me.getSource() == jbtExit){
			setJBTProps(jbtExit);
			jbtExit.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		}
	}
	public void mousePressed(MouseEvent me){}
	public void mouseReleased(MouseEvent me){
		if(me.getSource() == jbtStartGame && jbtStartGame.getText() == "Start Game"){
			// Let server know, user has started game
			TestGame.Instance.SendMessage(new String(), "STARTGAME");
			System.out.println("User has started a new game.");
			
			try {
			    Thread.sleep(100);                 //1000 milliseconds is one second.
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
			
			TestGame.Instance.clientFrame.loginPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
			// Set main menu panel false, set other panels true
			innerContainer.setVisible(false);
			gamePanel.setVisible(true);
    		users.setVisible(true);
    		chatPanel.setVisible(true);
    		gamePanel.setBackground( Color.WHITE );
    		TestGame.Instance.clientFrame.loginPanel.setBackground( Color.WHITE );
    		setBackground( Color.WHITE );
    		jbtStartGame.setText("Resume");
    		gamePanel.lblPlayer1.setText("Player 1:  " + TestGame.Instance.game.getPlayer1());
		}
		if(me.getSource() == jbtStartGame && jbtStartGame.getText() == "Resume"){
			// If jbtStartGame no longer says Start Game, and instead says Resume
			// Then no need to let server know that user has started game.
			// Probably could put this stuff in another method, since it's duplicate code
			TestGame.Instance.clientFrame.loginPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
			innerContainer.setVisible(false);
			gamePanel.setVisible(true);
    		users.setVisible(true);
    		chatPanel.setVisible(true);
    	//	gamePanel.setBackground( Color.WHITE );
    		TestGame.Instance.clientFrame.setBackground( Color.WHITE );
    		setBackground( Color.WHITE );
		}
		if(me.getSource() == jbtJoinGame){
			 if(jbtStartGame.getText() == "Resume"){
				 JOptionPane.showMessageDialog(this,
						    "You are already in a game.");
			 }
			 else{
				// Let server know, user has started game
				TestGame.Instance.SendMessage(new String(), "JOINGAME");
				
				try {
				    Thread.sleep(100);                 //1000 milliseconds is one second.
				} catch(InterruptedException ex) {
				    Thread.currentThread().interrupt();
				}
				if(isJoinable){
					TestGame.Instance.clientFrame.loginPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
					innerContainer.setVisible(false);
					gamePanel.setVisible(true);
					users.setVisible(true);
					chatPanel.setVisible(true);
					gamePanel.setBackground( Color.WHITE );
					TestGame.Instance.clientFrame.loginPanel.setBackground( Color.WHITE );
					setBackground( Color.WHITE );
					jbtStartGame.setText("Resume");
				}
				else{
					JOptionPane.showMessageDialog(this,
						    "There are no available games to join at this time.");
				}
			 }
		}
		if(me.getSource() == jbtOptions){}
		if(me.getSource() == jbtLogout){
			if(jbtStartGame.getText() != "Start Game"){
				jbtStartGame.setText("Start Game");
				TestGame.Instance.SendMessage(new String(), "QUITGAME"); 
			}
			
			TestGame.Instance.SendMessage(new String(), "LOGOUT");
			
			// Logout - Have loginpanel set back to visible
			// Set this panel to false
			// Set chatbox text to empty - No residual text left over from last login
			// If user uses same client to log back in.
			TestGame.Instance.clientFrame.loginPanel.loginContainer.setVisible(true);
			setVisible(false);
			chatPanel.chatBox.setText("");
		}
		if(me.getSource() == jbtExit){
    		TestGame.Instance.SendMessage(new String(), "LOGOUT");
    		
			if(TestGame.Instance.game != null){            		
        		TestGame.Instance.SendMessage(new String(), "QUITGAME"); 
			}
			
			System.exit(0);			
		}
	}
	
	/** Properties for Buttons when mouseout*/
	public void setJBTProps(JButton button){
		button.setFont(new Font("SansSerif", Font.PLAIN, 16));
		button.setBackground(new Color(61, 61, 61));
		button.setForeground(Color.WHITE);
		button.setBorder(BorderFactory.createMatteBorder(1, 1, 0, 1, Color.BLACK));
	}
	
	/** Properties for buttons when mouseover */
	public void setJBTPropsHighlight(JButton button){
		button.setFont(new Font("SansSerif", Font.PLAIN, 16));
		button.setBackground(new Color(119, 159, 140));
		button.setForeground(Color.WHITE);
		button.setBorder(BorderFactory.createMatteBorder(1, 1, 0, 1, Color.BLACK));
	}
		
	// Adds a method to get UserPanel (So that it can be added to frame, not main menu)
	public UserPanel getUserPanel(){
		return users;
	}
	
	// Adds a method to get ChatPanel (Again so that it can be returned to Frame and used)
	public ChatPanel getChatPanel(){
		return chatPanel;
	}
}

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class MainMenuPanel extends JPanel implements MouseListener{
	Game game = new Game();
	ChatPanel chatPanel = new ChatPanel();
	UserPanel users = new UserPanel();
	
	JPanel  innerContainer, jbtContainer;
	JLabel  lblMainMenu;
	JButton jbtStartGame, jbtOptions, jbtLogout, jbtExit;
	
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
		jbtContainer = new JPanel(new GridLayout(4, 1, 0, 0));
		jbtContainer.setBackground( new Color(47, 47, 47) );
		
		// Create Main Menu label, set props
		lblMainMenu = new JLabel("Main Menu");
		lblMainMenu.setHorizontalAlignment(JLabel.CENTER);
		lblMainMenu.setFont(new Font("SansSerif", Font.BOLD, 26));
		lblMainMenu.setForeground( Color.WHITE );
		
		// Create buttons
		jbtStartGame = new JButton("Start Game");
		jbtOptions = new JButton("Options");
		jbtLogout = new JButton("Logout");
		jbtExit = new JButton("Exit");

		// We have no options at the moment - Disable
		jbtOptions.setEnabled(false);
		
		// Set button properties
		setJBTProps(jbtStartGame);
		setJBTProps(jbtOptions);
		setJBTProps(jbtLogout);
		setJBTProps(jbtExit);
		jbtExit.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		
		// Add mouse listeners for buttons
		jbtStartGame.addMouseListener(this);
		jbtOptions.addMouseListener(this);
		jbtLogout.addMouseListener(this);
		jbtExit.addMouseListener(this);
		
		// Add buttons to button container
		jbtContainer.add(jbtStartGame);
		jbtContainer.add(jbtOptions);
		jbtContainer.add(jbtLogout);
		jbtContainer.add(jbtExit);
		
		// Add label and button container to inner container
		innerContainer.add(lblMainMenu, BorderLayout.NORTH);
		innerContainer.add(jbtContainer, BorderLayout.CENTER);
		
		// Add innerContainer to panel
		// Also add game panel to this one for now (...)
		add(innerContainer);
		add(game);
	}

	/** MouseListeners */
	public void mouseClicked(MouseEvent me){}
	public void mouseEntered(MouseEvent me){
		if(me.getSource() == jbtStartGame){
			setJBTPropsHighlight(jbtStartGame);
		}
		if(me.getSource() == jbtOptions){}
		if(me.getSource() == jbtLogout){
			setJBTPropsHighlight(jbtLogout);
		}
		if(me.getSource() == jbtExit){
			setJBTPropsHighlight(jbtExit);
		}
	}
	public void mouseExited(MouseEvent me){
		if(me.getSource() == jbtStartGame){
			setJBTProps(jbtStartGame);
		}
		if(me.getSource() == jbtOptions){}
		if(me.getSource() == jbtLogout){
			setJBTProps(jbtLogout);
		}
		if(me.getSource() == jbtExit){
			setJBTProps(jbtExit);
		}
	}
	public void mousePressed(MouseEvent me){}
	public void mouseReleased(MouseEvent me){
		if(me.getSource() == jbtStartGame && jbtStartGame.getText() == "Start Game"){
			// Let server know, user has started game
			TestGame.Instance.SendMessage(new String(), "STARTGAME");

			// Set main menu panel false, set other panels true
			innerContainer.setVisible(false);
			game.setVisible(true);
    		users.setVisible(true);
    		chatPanel.setVisible(true);
    		game.setBackground( Color.WHITE );
    		ClientFrame.loginPanel.setBackground( Color.WHITE );
    		setBackground( Color.WHITE );
		}
		if(me.getSource() == jbtStartGame && jbtStartGame.getText() == "Resume"){
			// If jbtStartGame no longer says Start Game, and instead says Resume
			// Then no need to let server know that user has started game.
			// Probably could put this stuff in another method, since it's duplicate code
			innerContainer.setVisible(false);
			game.setVisible(true);
    		users.setVisible(true);
    		chatPanel.setVisible(true);
    		game.setBackground( Color.WHITE );
    		ClientFrame.loginPanel.setBackground( Color.WHITE );
    		setBackground( Color.WHITE );
		}
		if(me.getSource() == jbtOptions){
			
		}
		if(me.getSource() == jbtLogout){
			if(jbtStartGame.getText() == "Start Game"){
				// User never actually entered game, so log out user - 
				// This just lets server know to remove the name from the list
				// Since user does not need to be removed from UserList
				TestGame.Instance.SendMessage(new String(), "LOGOUTUSER");
			}
			else{
				// User has entered game, must remove username from names list
				// As well as the actual user list, also reset text back to start game
				// For when user re-enters with same client
				TestGame.Instance.SendMessage(new String(), "LOGOUT");
				jbtStartGame.setText("Start Game");
			}
			
			// Logout - Have loginpanel set back to visible
			// Set this panel to false
			// Set chatbox text to empty - No residual text left over from last login
			// If user uses same client to log back in.
			ClientFrame.loginPanel.loginContainer.setVisible(true);
			setVisible(false);
			chatPanel.chatBox.setText("");
		}
		if(me.getSource() == jbtExit){
			if(jbtStartGame.getText() == "Start Game"){
				TestGame.Instance.SendMessage(new String(), "LOGOUTUSER");
			}
			else{
				TestGame.Instance.SendMessage(new String(), "LOGOUT");
				jbtStartGame.setText("Start Game");
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

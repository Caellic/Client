import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import java.awt.event.WindowAdapter;

import javax.swing.JFrame;


public class ClientFrame extends JFrame implements MouseListener{
	/** Data Members */
	JPanel 		innerFrame = new JPanel();	
	TitlePanel  titlePanel = new TitlePanel();
	LoginScreen loginPanel = new LoginScreen();
	IntroPanel  introPanel = loginPanel.getIntroPanel();
	MainMenuPanel mainMenu = loginPanel.getMainMenuPanel();
	UserPanel 	 userPanel = mainMenu.getUserPanel();
	ChatPanel 	 chatPanel = mainMenu.getChatPanel();
		
	//Prototype instructions
	private JTextArea instructions;
	
	/** Constructor for Client Frame */
	public ClientFrame(){
		// Set details for client frame
		setTitle("Tic Tac Toe");
		setLayout(new BorderLayout(0, 0));
		setSize(900, 700);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		// Frame panel to hold login, and chat panel
		// done to make sure side panel stretches across the entire right side
		innerFrame.setVisible(true);
		innerFrame.setLayout(new BorderLayout(0, 0));
		
		// Add panels to frame
		innerFrame.add(loginPanel, BorderLayout.CENTER);
		innerFrame.add(chatPanel, BorderLayout.SOUTH);
		
		// Add panels to client frame
		add(titlePanel, BorderLayout.NORTH);
		add(innerFrame, BorderLayout.CENTER);		
		add(userPanel, BorderLayout.EAST);
		
		// Add Mouse Listeners for userPanel
		userPanel.jbtMainMenu.addMouseListener(this);
		userPanel.jbtQuitGame.addMouseListener(this);		
		
		// Prototype Stuff 
		/*
		instructions = new JTextArea("Prototype:\n By-passing database - "
				+ "To test login enter 'test' for both "
				+ "the username and password.\n "
				+ "Enter something else to watch it fail.");
		instructions.setEditable(false);
		instructions.setLineWrap(true);
		instructions.setWrapStyleWord(true);
		instructions.setFont(new Font("SansSerif", Font.BOLD, 18));
		instructions.setForeground( Color.RED );
		frame.add(instructions, BorderLayout.NORTH);
		*/
		
		/** Forcing game max/min size, since to many panels deep to naturally fill panel */
		this.addWindowStateListener(new WindowStateListener() {
	        @Override
	        public void windowStateChanged(WindowEvent e) {
	    	   int oldState = e.getOldState();
	           int newState = e.getNewState();
	           Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	           
	           if( (oldState & Frame.MAXIMIZED_BOTH) == 0
	        	 && (newState & Frame.MAXIMIZED_BOTH) != 0) {
	        	   mainMenu.setPreferredSize(new Dimension(screenSize.width - 470, screenSize.height - 340));
	        	   mainMenu.gamePanel.setPreferredSize(new Dimension(screenSize.width - 490, screenSize.height - 360));
	           }
	           if( (oldState & Frame.MAXIMIZED_BOTH) != 0
	            && (newState & Frame.NORMAL) == 0){
	        	   mainMenu.setPreferredSize(new Dimension(520, 395));
	        	   mainMenu.gamePanel.setPreferredSize(new Dimension(500, 390));
	           }
	        }
	    });
		
		/** If user exits game, do appropriate action */
		this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {   
            	if(TestGame.Instance.game != null){            		
            		TestGame.Instance.SendMessage(new String(), "QUITGAME"); 
            		TestGame.Instance.SendMessage(new String(), "LOGOUT");

            		//System.out.println("Why does it keep breaking?");
            	}
            	else{
            		if(mainMenu.isVisible()){
            			TestGame.Instance.SendMessage(new String(), "LOGOUT");     
            		}
            	}
            }
        });		
	}	
	
	/** Sets the text of the user list... directly */
	public void setUsers(String usersLists){
		userPanel.userList.setText(usersLists +"\n");
	}	

	/** MouseListeners */
	public void mouseClicked(MouseEvent me){}
	public void mouseEntered(MouseEvent me){}
	public void mouseExited(MouseEvent me){}
	public void mousePressed(MouseEvent me){}
	public void mouseReleased(MouseEvent me){
		if(me.getSource() == userPanel.jbtMainMenu){
			mainMenu.innerContainer.setVisible(true);
			mainMenu.gamePanel.setVisible(false);
			mainMenu.users.setVisible(false);
			mainMenu.chatPanel.setVisible(false);
			loginPanel.setBackground(new Color(47, 47, 47));
			loginPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 90));
			mainMenu.setBackground(new Color(47, 47, 47));
		}
		if(me.getSource() == userPanel.jbtQuitGame){
			TestGame.Instance.SendMessage(new String(), "QUITGAME");
			TestGame.Instance.game = null;
			mainMenu.innerContainer.setVisible(true);
			mainMenu.jbtStartGame.setText("Start Game");
			mainMenu.gamePanel.setVisible(false);
			mainMenu.users.setVisible(false);
			mainMenu.chatPanel.setVisible(false);
			loginPanel.setBackground(new Color(47, 47, 47));
			loginPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 90));
			mainMenu.setBackground(new Color(47, 47, 47));
		}
	}	
}

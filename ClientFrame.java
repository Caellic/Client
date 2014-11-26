import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import java.awt.event.WindowAdapter;

import javax.swing.JFrame;


public class ClientFrame extends JFrame implements MouseListener{
	/** Private data members */
	private JPanel frame = new JPanel();
	private TitlePanel title = new TitlePanel();
	static LoginScreen loginPanel = new LoginScreen();
	MainMenuPanel mainMenu = loginPanel.getMainMenuPanel();
	private UserPanel users;
	private ChatPanel chatPanel;
		
	//Prototype instructions
	private JTextArea instructions;	
	
	/** Constructor for frame */
	public ClientFrame(){
		// Set details for frame
		setTitle("Tic Tac Toe");
		setLayout(new BorderLayout(0, 0));
		setSize(900, 700);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		// Frame panel to hold login, and chat panel
		// done to make sure side panel stretches across the entire right side
		frame.setVisible(true);
		frame.setLayout(new BorderLayout(0, 0));
		frame.add(loginPanel, BorderLayout.CENTER);
		
		// Get chat panel from login panel to display
		chatPanel = mainMenu.getChatPanel();
		frame.add(chatPanel, BorderLayout.SOUTH);
		
		// Add panels to frame
		add(title, BorderLayout.NORTH);
		add(frame, BorderLayout.CENTER);		
		
		// Get user panel from login panel to display
		users = mainMenu.getUserPanel();
		add(users, BorderLayout.EAST);
		
		users.jbtMainMenu.addMouseListener(this);
		
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
		
		// Argh
		this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev) {       
            	if(chatPanel.isVisible()){
            		TestGame.Instance.SendMessage(new String(), "LOGOUT");
            	}
            }
        });
		
	}	
	
	/** Sets the text of the user list... directly */
	public void setUsers(String usersLists){
		users.userList.setText(usersLists +"\n");
	}
	
	/** Method sets and returns attributes for Username/Events */
	public SimpleAttributeSet getUserAttributes(Color c){
		SimpleAttributeSet userAttr = new SimpleAttributeSet();
		StyleConstants.setForeground(userAttr, c);
		StyleConstants.setAlignment(userAttr , StyleConstants.ALIGN_LEFT); 
		StyleConstants.setBold(userAttr, true);
		
		return userAttr;
	}
	
	/** Method inserts text stating user has exited, directly */
	public void setUserExited(String chatMsg){
		SimpleAttributeSet userName = getUserAttributes(new Color(119, 159, 140));
		
		int index = chatMsg.indexOf(" ");
		String user = chatMsg.substring(index + 1, chatMsg.length());
		
		try {
			//chatPanel.doc.insertString(0, user + ": ", pretendUserAttr );
			if(chatPanel.isVisible()){
				chatPanel.doc.insertString(chatPanel.doc.getLength(), user + " has exited the game.\n", userName);
				//chatPanel.doc.setParagraphAttributes(0, user.length(), stuffSaid, true);
			}
			
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/** Method inserts text stating user has entered, directly */
	public void setUserEntered(String chatMsg){
		SimpleAttributeSet userName = getUserAttributes( new Color(119, 159, 140) );
		
		int index = chatMsg.indexOf(" ");
		String user = chatMsg.substring(index + 1, chatMsg.length());
		
		try {
			//chatPanel.doc.insertString(0, user + ": ", pretendUserAttr );
			chatPanel.doc.insertString(chatPanel.doc.getLength(), user + " has entered game.\n", userName);
			//chatPanel.doc.setParagraphAttributes(0, user.length(), stuffSaid, true);
			
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/** Method inserts text that user has typed, directly */
	public void setMsg(String chatMsg){
		SimpleAttributeSet userName  = getUserAttributes( new Color(44, 146, 198) );		
		SimpleAttributeSet stuffSaid = new SimpleAttributeSet();
		
		try {
			int index = chatMsg.indexOf(" ");
			String userString = chatMsg.substring(0, index) + " : ";
			String msgString  = chatMsg.substring(index, chatMsg.length());
			
			//chatPanel.doc.insertString(0, user + ": ", pretendUserAttr );
			chatPanel.doc.insertString(chatPanel.doc.getLength(), userString, userName);
			chatPanel.doc.insertString(chatPanel.doc.getLength(), msgString, stuffSaid);
			//chatPanel.doc.setParagraphAttributes(0, user.length(), stuffSaid, true);
			chatPanel.chatBox.setCaretPosition(chatPanel.doc.getLength());
			
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/** MouseListeners */
	public void mouseClicked(MouseEvent me){}
	public void mouseEntered(MouseEvent me){}
	public void mouseExited(MouseEvent me){}
	public void mousePressed(MouseEvent me){}
	public void mouseReleased(MouseEvent me){
		if(me.getSource() == users.jbtMainMenu){
			mainMenu.innerContainer.setVisible(true);
			mainMenu.jbtStartGame.setText("Resume");
			mainMenu.game.setVisible(false);
			mainMenu.users.setVisible(false);
			mainMenu.chatPanel.setVisible(false);
			loginPanel.setBackground(new Color(47, 47, 47));
			mainMenu.setBackground(new Color(47, 47, 47));
		}
	}	
}

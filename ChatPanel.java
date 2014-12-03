import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;


public class ChatPanel extends JPanel implements MouseListener, KeyListener{
	/** Data Members */
	JTextField chatLine = new JTextField(50);
	JButton jbtChatSend = new JButton("Send");
	JTextPane chatBox 	= new JTextPane();
	String pretendUser  = new String("Pretend User");
	JScrollPane chatBoxScroll;
	String chatMsg = "";
	StyledDocument doc = chatBox.getStyledDocument();
	
	/** ChatPanel Constructor */
	public ChatPanel(){
		setLayout(new BorderLayout(0, 10));
		setBackground(new Color(47, 47, 47));
		setBorder(new EmptyBorder(10, 20, 10, 5));
		setVisible(false);

		chatBox.setEditable(false);
		
		// Create a scroll pane to hold the text area
		// Add chatbox to it
		chatBoxScroll = new JScrollPane(chatBox);
		chatBoxScroll.setHorizontalScrollBarPolicy(31);

		// Set dimensions for chatBoxScroll
		chatBoxScroll.setPreferredSize(new Dimension(280, 100));		
		
		// Set props for send button
		jbtChatSend.setBackground(new Color(119, 159, 140));
		jbtChatSend.setForeground(Color.WHITE);
		
		// Add components to chatPanel
		add(chatBoxScroll, BorderLayout.NORTH);
		add(jbtChatSend, BorderLayout.EAST);
		add(chatLine, BorderLayout.CENTER);		
		
		// Add listeners to send button, and chat line
		jbtChatSend.addMouseListener(this);
		chatLine.addKeyListener(this);
	}    
	
	/***********************************************************
	 *		Methods that decorate and sets text	in chatbox	   *
	 ***********************************************************/
	public SimpleAttributeSet getUserAttributes(Color c){
		// Set the attributes
		SimpleAttributeSet userAttr = new SimpleAttributeSet();
		StyleConstants.setForeground(userAttr, c);
		StyleConstants.setAlignment(userAttr , StyleConstants.ALIGN_LEFT); 
		StyleConstants.setBold(userAttr, true);
		
		return userAttr;
	}
	
	/** Method inserts text stating user has exited */
	public void setUserExited(String chatMsg){
		SimpleAttributeSet userName = getUserAttributes(new Color(119, 159, 140));
		
		int index = chatMsg.indexOf(" ");
		String user = chatMsg.substring(index + 1, chatMsg.length());
		
		try {
			//chatPanel.doc.insertString(0, user + ": ", pretendUserAttr );
			if(isVisible()){
				doc.insertString(doc.getLength(), user + " has exited the game.\n", userName);
				//chatPanel.doc.setParagraphAttributes(0, user.length(), stuffSaid, true);
			}
			
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/** Method inserts text stating user has entered */
	public void setUserEntered(String chatMsg){
		SimpleAttributeSet userName = getUserAttributes( new Color(119, 159, 140) );
		
		int index = chatMsg.indexOf(" ");
		String user = chatMsg.substring(index + 1, chatMsg.length());
		
		try {
			//chatPanel.doc.insertString(0, user + ": ", pretendUserAttr );
			doc.insertString(doc.getLength(), user + " has entered game.\n", userName);
			//chatPanel.doc.setParagraphAttributes(0, user.length(), stuffSaid, true);
			
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/** Method inserts text that user has typed */
	public void setMsg(String chatMsg){
		SimpleAttributeSet userName  = getUserAttributes( new Color(44, 146, 198) );		
		SimpleAttributeSet stuffSaid = new SimpleAttributeSet();
		
		try {
			int index = chatMsg.indexOf(" ");
			String userString = chatMsg.substring(0, index) + " : ";
			String msgString  = chatMsg.substring(index, chatMsg.length());
			
			//chatPanel.doc.insertString(0, user + ": ", pretendUserAttr );
			doc.insertString(doc.getLength(), userString, userName);
			doc.insertString(doc.getLength(), msgString, stuffSaid);
			//chatPanel.doc.setParagraphAttributes(0, user.length(), stuffSaid, true);
			chatBox.setCaretPosition(doc.getLength());
			
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/************************************************************
	 *		  Method for Sending Messages from Chatline			*
	 ************************************************************/
	public void sendChatLineText(){
		// If chatline is not empty..
		if(!chatLine.getText().equals("")){
			// Send trimmed msg of extra outer spaces to server
			TestGame.Instance.Chat(chatLine.getText().trim());
			// Reset chatline to empty
			chatLine.setText("");
		}
	}
	
	
	/************************************************************
	 *		  				Listeners							*
	 ************************************************************/	
	/** MouseListeners */
	public void mouseClicked(MouseEvent me){}
	public void mouseEntered(MouseEvent me){}
	public void mouseExited(MouseEvent me){}
	public void mousePressed(MouseEvent me){}
	public void mouseReleased(MouseEvent me){
		if(me.getButton() == MouseEvent.BUTTON1){
			sendChatLineText();
		}
	}
	
	/** KeyListeners*/
	public void keyPressed(KeyEvent ke){
		if(ke.getKeyCode() == KeyEvent.VK_ENTER){
			sendChatLineText();
		}
	}
	public void keyReleased(KeyEvent ke){}
	public void keyTyped(KeyEvent ke){}	
}

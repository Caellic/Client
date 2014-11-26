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
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;


public class ChatPanel extends JPanel implements MouseListener, KeyListener{
	private JTextField chatLine = new JTextField(50);
	private JButton jbtChatSend= new JButton("Send");
	JTextPane chatBox = new JTextPane();
	private JScrollPane chatBoxScroll;
	private String pretendUser = new String("Pretend User");
	String chatMsg = "";
	StyledDocument doc = chatBox.getStyledDocument();
	
	
	public ChatPanel(){
		setLayout(new BorderLayout(0, 10));
		setBackground(new Color(47, 47, 47));
		setBorder(new EmptyBorder(10, 20, 10, 5));
		setVisible(false);

		chatBox.setEditable(false);
		
		// Create a scroll pane to hold the text area
		chatBoxScroll = new JScrollPane(chatBox);
		chatBoxScroll.setHorizontalScrollBarPolicy(31);

		// Set BorderLayout for the panel, add label and scrollpane
		chatBoxScroll.setPreferredSize(new Dimension(280, 100));		
		
		jbtChatSend.setBackground(new Color(119, 159, 140));
		jbtChatSend.setForeground(Color.WHITE);

		add(chatBoxScroll, BorderLayout.NORTH);
		add(jbtChatSend, BorderLayout.EAST);
		add(chatLine, BorderLayout.CENTER);
		
		
		jbtChatSend.addMouseListener(this);
		chatLine.addKeyListener(this);
	}    
	
	/** MouseListeners */
	public void mouseClicked(MouseEvent me){}
	public void mouseEntered(MouseEvent me){}
	public void mouseExited(MouseEvent me){}
	public void mousePressed(MouseEvent me){}
	public void mouseReleased(MouseEvent me){
		if(me.getButton() == MouseEvent.BUTTON1){
			if(!chatLine.getText().equals("") && !chatLine.getText().matches(" +")){
				TestGame.Instance.Chat(chatLine.getText().trim());
				chatLine.setText("");
			}
		}
	}
	
	/** KeyListeners*/
	public void keyPressed(KeyEvent ke){
		if(ke.getKeyCode() == KeyEvent.VK_ENTER){
			if(!chatLine.getText().equals("") && !chatLine.getText().matches(" +")){
				TestGame.Instance.Chat(chatLine.getText().trim());
				chatLine.setText("");
			}
		}
	}
	public void keyReleased(KeyEvent ke){}
	public void keyTyped(KeyEvent ke){}
}

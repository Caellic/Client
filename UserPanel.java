import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;


public class UserPanel extends JPanel implements MouseListener{
	/** Private data members */
	private JPanel userTitlePanel = new JPanel();
	private JLabel users = new JLabel("Users");

	private JPanel userListPanel = new JPanel();
	JTextArea userList = new JTextArea("");
	
	private JPanel userButtonPanel = new JPanel();
	JButton jbtMainMenu = new JButton("Main Menu");
	private JButton jbtExitGame = new JButton("Exit");
	
	/** Constructor for UserPanel (Located East in Frame) */
	public UserPanel(){
		// Set properties for panel
		setLayout(new BorderLayout(0, 10));
		setVisible(false);
		setBackground(new Color(47, 47, 47));
		setBorder(new MatteBorder(10, 10, 10, 10, new Color(47, 47, 47)));
		
		// Set properties for userTitlePanel (holds title "Users")
		userTitlePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 4));
		userTitlePanel.setBackground(new Color(41, 41, 41));
		userTitlePanel.setBorder(new LineBorder(Color.BLACK));
		userTitlePanel.setPreferredSize(new Dimension(120,30));
		
		// Set properties for userListPanel (Holds users)
		userListPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		userListPanel.setBackground(new Color(41, 41, 41));
		userListPanel.setBorder(new LineBorder(Color.BLACK));
		userListPanel.setPreferredSize(new Dimension(120,310));
		
		// Set properties for UserTitle Text
		users.setFont(new Font("Tahoma", Font.BOLD, 16));
		users.setForeground(new Color(50, 165, 224));
		
		// Set properties for list of users
		userList.setRows(25);
		userList.setColumns(8);
		userList.setPreferredSize(new Dimension(108,310));
		userList.setEditable(false);
		userList.setBackground(new Color(47, 47, 47));
		userList.setForeground(Color.WHITE);
		userList.setMargin(new Insets(5, 5, 5, 5));

		// Add users and userList to appropriate panels
		userTitlePanel.add(users);
		userListPanel.add(userList);
		
		// Set properties for panel which holds buttons(located on the user panel)
		userButtonPanel.setLayout(new GridLayout(2, 1, 5, 10));
		userButtonPanel.setBackground(new Color(47, 47, 47));
		userButtonPanel.setPreferredSize(new Dimension(120, 67));
		
		// Set properties for buttons
		jbtMainMenu.setBackground(new Color(41, 41, 41));
		jbtExitGame.setBackground(new Color(41, 41, 41));
		jbtMainMenu.setForeground(new Color(50, 165, 224));
		jbtExitGame.setForeground(new Color(50, 165, 224));
		
		// Add buttons to button panel
		userButtonPanel.add(jbtMainMenu);
		userButtonPanel.add(jbtExitGame);
		
		// Add all categorized panels to userPanel
		add(userTitlePanel, BorderLayout.NORTH);
		add(userListPanel, BorderLayout.CENTER);
		add(userButtonPanel, BorderLayout.SOUTH);	
		
		// Add action for exit button (exit program)
		jbtExitGame.addActionListener(new ActionListener() {
	    	// Handle ActionEvent 
	    	public void actionPerformed(ActionEvent e) {
	    		TestGame.Instance.SendMessage(new String(), "LOGOUT");
	    		System.exit(0);
	    	}
	    });
	}
	
	/** MouseListeners */
	public void mouseClicked(MouseEvent me){}
	public void mouseEntered(MouseEvent me){}
	public void mouseExited(MouseEvent me){}
	public void mousePressed(MouseEvent me){}
	public void mouseReleased(MouseEvent me){
		if(me.getSource() == jbtMainMenu){
		//	mainMenu.innerContainer.setVisible(true);
		//	mainMenu.game.setVisible(false);
		//	mainMenu.users.setVisible(false);
	//		mainMenu.chatPanel.setVisible(false);
    	//	game.setBackground( Color.WHITE );
    	//	ClientFrame.loginPanel.setBackground( Color.WHITE );
    	//	setBackground( Color.WHITE );
		}
	}
}

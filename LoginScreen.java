import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.MatteBorder;


public class LoginScreen extends JPanel implements MouseListener{
	/** Private data members */
	// Panels from other classes
	RegisterPanel rgsterPanel   = new RegisterPanel();
	MainMenuPanel mainMenuPanel = new MainMenuPanel();
	IntroPanel introPanel  		= new IntroPanel();
	
	// Login Panels
	JPanel loginContainer	  = new JPanel();
	JPanel loginTitle	 	  = new JPanel();
	JPanel loginInfoContainer = new JPanel();
	JPanel jbtContainer		  = new JPanel();
	
	// For Login Title 
	JLabel jblLoginTitle = new JLabel("Login");
	
	// Username, password and error message for loginInfoContainer
	JTextField userName  = new JTextField(13);
	JTextField password  = new JTextField(13);
	JLabel userPassError = new JLabel();
	
	// Register new user
	JLabel registerNow = new JLabel();
	
	// Buttons for jbt container
	JButton jbtLogin = new JButton("Login");
	JButton jbtExit  = new JButton("Exit");
	
	/** Constructor For LoginScreen (Located Center in Frame) */
	public LoginScreen(){
		// Set layout and background of LoginScreen
		setLayout(new FlowLayout(FlowLayout.CENTER, 0, 90));
		setBackground(  new Color( 41, 41, 41) );
		
		// Set layout and visibility of LoginContainer (Contains all the login details)
		loginContainer.setLayout(new BorderLayout(5, 8));
		loginContainer.setVisible(false);
		
		// jbt Container, is the button container  Sets the dimensions, layout
		// and adds login and exit buttons
		jbtContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10));
		jbtContainer.setPreferredSize(new Dimension(100, 70));
		jbtLogin.setBackground(new Color(61, 61, 61));
		jbtExit.setBackground(new Color(61, 61, 61));
		jbtLogin.setForeground(Color.WHITE);
		jbtExit.setForeground(Color.WHITE);
		jbtContainer.add(jbtLogin);	
		jbtContainer.add(jbtExit);
		
		// The top part of the loginContainer, adds "Login" title
		loginTitle.setLayout(new FlowLayout(FlowLayout.LEFT));
		loginTitle.setBackground(new Color(41, 41, 41));
		jblLoginTitle.setFont(new Font("SansSerif", Font.BOLD, 16));
		jblLoginTitle.setForeground(Color.WHITE);
		loginTitle.add(jblLoginTitle);
		
		// The info Container, includes the user name and password textboxes 
		loginInfoContainer.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 5));
	//	loginInfoContainer.setLayout(new GridLayout(3, 1, 5, 5));
		loginInfoContainer.setPreferredSize(new Dimension(290, 78));
		
		loginInfoContainer.add(new JLabel("User Name:")).setForeground(Color.BLACK);
		loginInfoContainer.add(userName);
		loginInfoContainer.add(new JLabel("  Password:")).setForeground(Color.BLACK);
		loginInfoContainer.add(password);
		
		// Try a container
		JPanel textLogin = new JPanel(new GridLayout(2, 1, 5, 5));
		textLogin.setBackground(new Color(216, 220, 222));
		// Sets user/password error to false (doesn't always show)
		//and adds to loginInfo Container
		userPassError.setVisible(false);
		userPassError.setForeground(new Color(171, 0, 16));
		loginInfoContainer.add(userPassError);
		
		// Adds label to register user
		registerNow.setText("Not a user?  Register Now!");
		registerNow.setFont(new Font("SansSerif", Font.ITALIC, 12));
		registerNow.setForeground(new Color(119, 159, 140));
		jbtContainer.add(registerNow);
		
		loginInfoContainer.add(textLogin);
		
		// Sets the background of the various login containers
		loginInfoContainer.setBackground(new Color(216, 220, 222));
		jbtContainer.setBackground(new Color(41, 41, 41));
		loginContainer.setBackground(new Color(197, 202, 208));
		
		// Login containers adds the title, info, and button containers
		loginContainer.add(loginTitle, BorderLayout.NORTH);
		loginContainer.add(loginInfoContainer, BorderLayout. CENTER);
		loginContainer.add(jbtContainer, BorderLayout.SOUTH);

		// Set a border around the login container to make more distinct
		loginContainer.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.black),
				BorderFactory.createMatteBorder(3, 5, 3, 5, new Color(41, 41, 41))
				));
		
		// Add the loginContainer to panel as well as the game,
		// since it will be in the same spot as the login container
		add(introPanel);
		add(loginContainer);
		//add(game);
		add(mainMenuPanel);
		
		registerNow.addMouseListener(this);
		
		// Add action listener for the login button, call displayLogin_actionPerformed
		jbtLogin.addActionListener(new ActionListener() {
	    	// Handle ActionEvent 
	    	public void actionPerformed(ActionEvent e) {
	    		displayLogin_actionPerformed(e);
	    	}
	    });
		
		// Add abstract action for keystroke when enter is pressed, again call displayLogin_actionPerformed
		KeyStroke enterKeyStroke = KeyStroke.getKeyStroke("ENTER");
		Action enterAction = new AbstractAction(){
		    public void actionPerformed(ActionEvent e)
		    {
		    	displayLogin_actionPerformed(e);
	    	}
		};

		loginContainer.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(enterKeyStroke, "ENTER");
		loginContainer.getActionMap().put("ENTER", enterAction);		
		
		// Add action for exit button (exit program)
		jbtExit.addActionListener(new ActionListener() {
	    	// Handle ActionEvent 
	    	public void actionPerformed(ActionEvent e) {
            	//TestGame.Instance.SendMessage(new String(), "LOGOUT");
	    		System.exit(0);
	    	}
	    });
		
		// Make it easier to escape program by hitting esc
		KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke("ESCAPE");
		Action escapeAction = new AbstractAction(){
		    public void actionPerformed(ActionEvent e){
            	//TestGame.Instance.SendMessage(new String(), "LOGOUT");
		        System.exit(0);
		    }
		};
		
		// Only escape program when loginContainer is in focus (Might want to escape to a different window,
		// not exit program when in game (such as menu screen)
		loginContainer.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke, "ESCAPE");
		loginContainer.getActionMap().put("ESCAPE", escapeAction);		
	}
	
	// Method for the login button, sets if login and password are correct,
	// then loginContainer visibility is set to false, while game, users and chatpanel
	// are set to true. (These will now be visible, loginContainer will now go away.
	private void displayLogin_actionPerformed(ActionEvent e){
		String userName1 = userName.getText().trim();
		String password1 = password.getText().trim();
		
		TestGame.Instance.Login(userName1, password1);
		//TestGame.Instance.readMessage();
		try {
		    Thread.sleep(200);                 //1000 milliseconds is one second.
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		
		if(TestGame.Instance.isValidLogin){	
			loginContainer.setVisible(false);
			mainMenuPanel.setVisible(true);
			userName.setText("");
			password.setText("");
			//game.setVisible(true);
    		//users.setVisible(true);
    		//chatPanel.setVisible(true);
    		//setBackground(Color.WHITE);
    		setBorder(new MatteBorder(10, 20, 10, 5, new Color(47, 47, 47)));
    		userPassError.setVisible(false);
		}
		else if(!TestGame.Instance.isValidLogin && TestGame.Instance.isDuplicateLogin) {
			userPassError.setText("*This username is already logged in.");
			userPassError.setVisible(true);
		}
		else{
			userPassError.setText("*Sorry, username or password is incorrect.");
			userPassError.setVisible(true);
		}		
	}	
	
	
	/** MouseListeners */
	public void mouseClicked(MouseEvent me){}
	public void mouseEntered(MouseEvent me){
		registerNow.setForeground(new Color(197, 202, 208));
		registerNow.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}
	public void mouseExited(MouseEvent me){
		registerNow.setForeground(new Color(119, 159, 140));
	}
	public void mousePressed(MouseEvent me){}
	public void mouseReleased(MouseEvent me){
		if(me.getButton() == MouseEvent.BUTTON1){
			rgsterPanel.setVisible(true);
		}
	}
	
	// Adds a method to get ChatPanel (Again so that it can be returned to Frame and used)
	public MainMenuPanel getMainMenuPanel(){
		return mainMenuPanel;
	}
	
	// Adds a method to get ChatPanel (Again so that it can be returned to Frame and used)
	public IntroPanel getIntroPanel(){
		return introPanel;
	}
	
	// Adds a method to get ChatPanel (Again so that it can be returned to Frame and used)
	/*public Game getGamePanel(){
		return game;
	}*/
}

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;


public class IntroPanel extends JPanel {
	JLabel image = new JLabel(new ImageIcon(this.getClass().getResource(
			"/LogAweLargeAniDarkNoBack.gif")));
	
	// Timer for intro screen
	Timer timer;
	int seconds = 0;
	
	public IntroPanel(){
		setLayout(new FlowLayout(FlowLayout.CENTER, 0, 100));
		setBackground( new Color( 41, 41, 41) );
		add(image);
		
		// Start timer for intro panel
		timer = new Timer(1000, new TimerListener());
		timer.start();
		
		// Escape the logo screen
		KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke("ESCAPE");
		Action escapeAction = new AbstractAction(){
		    public void actionPerformed(ActionEvent e){
		    	endIntro();
		    }
		};
		
		// Get rid of logo screen
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke, "ESCAPE");
		getActionMap().put("ESCAPE", escapeAction);	
	}
	
	
	/** End Intro Screen */
	public void endIntro(){
		timer.stop();
		setVisible(false);
		TestGame.Instance.clientFrame.titlePanel.setVisible(true);
		TestGame.Instance.clientFrame.loginPanel.setBackground( new Color( 47, 47, 47) );
		TestGame.Instance.clientFrame.loginPanel.loginContainer.setVisible(true);
		TestGame.Instance.clientFrame.loginPanel.userName.requestFocus();
	}
	
	class TimerListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			seconds++;
			if(seconds >= 4){
				endIntro();
			}
		}
	}

}

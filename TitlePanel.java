import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TitlePanel extends JPanel{	
	private JLabel title = new JLabel("Tic-Tac-Toe");
	
	public TitlePanel(){
		setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
		setBackground(new Color(41, 41, 41));
		setVisible(false);
		//title.setForeground(new Color(50, 165, 224));
		title.setForeground(new Color(119, 159, 140));
		
		title.setFont(new Font("Tahoma", Font.BOLD, 32));
		setBorder(BorderFactory.createMatteBorder(
                0, 0, 1, 0, Color.BLACK));		
		
		add(title);
		
	}
}
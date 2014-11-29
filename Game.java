import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;


public class Game extends JPanel implements MouseListener{
	
	JPanel gridPanel, statusPanel;
	JLabel lblPlayer1, lblPlayer2;
	
	String player1 = "",
		   player2 = "";
	
	Cell[][] cell = new Cell[3][3];
	
	public Game(){		
		setLayout(new BorderLayout(0, 6));
		setVisible(false);
		setBackground(Color.WHITE);

		gridPanel = new JPanel(new GridLayout(3, 3, 0, 0));		
		//statusPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 0));
		statusPanel = new JPanel(new GridLayout(1, 2, 40, 0));
		
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				gridPanel.add(cell[i][j] = new Cell(i, j));
			}
		}
		
		lblPlayer1 = new JLabel("Player 1: ");
		lblPlayer2 = new JLabel("Player 2:  Waiting for Player... ");
		
		statusPanel.add(lblPlayer1);
		statusPanel.add(lblPlayer2);
		
		add(gridPanel, BorderLayout.CENTER);
		add(statusPanel, BorderLayout.SOUTH);
		
		cell[0][0].addMouseListener(this);
	}
	
	
	public void setPlayer1Label(String player1){
		lblPlayer1.setText("Player 1: " + player1);
	}
	
	public void setPlayer2Label(String player2){
		lblPlayer2.setText("Player 2: " + player2);
	}
	
	
	/** MouseListeners */
	public void mouseClicked(MouseEvent me){}
	public void mouseEntered(MouseEvent me){
	}
	public void mouseExited(MouseEvent me){}
	public void mousePressed(MouseEvent me){}
	public void mouseReleased(MouseEvent me){
		if(me.getSource() == cell[0][0]){
			cell[0][0].setBackground( Color.BLACK );
		}
	}	
	
	
	
	
	
	public class Cell extends JPanel {
		int row;
		int column;
		
		private char token = ' ';
		
		public Cell(int row, int column){
			this.row = row;
			this.column = column;
			this.setBorder(new LineBorder(Color.BLACK, 1));
			addMouseListener(new ClickListener());
		}
	
		public char getToken() {
			return token;
		}
		
		public void setToken(char c){
			token = c;
			repaint();
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			if (token == 'X'){
				g.drawLine(10, 10, getWidth() - 10, getHeight() - 10);
				g.drawLine(getWidth() - 10, 10, 10, getHeight() - 10);
			}
			else if (token == 'O'){
				g.drawOval(10,  10,  getWidth() - 20, getHeight() - 20);
			}
		}
		
		private class ClickListener extends MouseAdapter {
			@Override
			public void mouseClicked(MouseEvent e) {
			//	if (token == ' ' && myTurn) {
				//	setToken(myToken);
				//	myTurn = false;
				//	rowSelected = row;
					//columnSelected = column;
				//	waiting = false;
				//}
			}
		}
	}
}

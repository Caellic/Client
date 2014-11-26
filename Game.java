import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;


public class Game extends JPanel{
	
	JPanel gridPanel, statusPanel;
	Cell[][] cell = new Cell[3][3];
	
	public Game(){		
		setLayout(new BorderLayout(50, 6));
		setVisible(false);
		setBackground(Color.WHITE);

		gridPanel = new JPanel(new GridLayout(3, 3, 0, 0));		
		statusPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				gridPanel.add(cell[i][j] = new Cell(i, j));
			}
		}

		
		add(gridPanel, BorderLayout.CENTER);
		add(statusPanel, BorderLayout.SOUTH);
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

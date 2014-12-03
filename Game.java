import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;


public class Game extends JPanel implements MouseListener{
	
	JPanel gridPanel, playerPanel, statusPanel;
	JLabel lblPlayer1, lblPlayer2, lblStatus;
	
	String player1 = "",
		   player2 = "";
	
	Cell[][] cell = new Cell[3][3];
	
	public Game(){		
		setLayout(new BorderLayout(0, 10));
		setVisible(false);
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(500, 390));
		
		// Create status panel, set properties
		statusPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		statusPanel.setBackground( Color.WHITE );
		
		// Create label, set props
		lblStatus = new JLabel(" ");
		lblStatus.setFont(new Font("SansSerif", Font.BOLD, 16));
		
		// Add label to status panel
		statusPanel.add(lblStatus);
		
		// Create grid panel, set properties
		gridPanel = new JPanel(new GridLayout(3, 3, 0, 0));	
		gridPanel.setBackground( Color.WHITE );
		
		// Add cells to grid -From book Listing 33.12
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				gridPanel.add(cell[i][j] = new Cell(i, j));
			}
		}	

		// Set Individual cell properties
		cell[0][0].setBorder(BorderFactory.createMatteBorder(
				0, 0, 3, 2, Color.BLACK));	
		cell[1][0].setBorder(BorderFactory.createMatteBorder(
				0, 0, 3, 2, Color.BLACK));
		cell[2][0].setBorder(BorderFactory.createMatteBorder(
				0, 0, 0, 2, Color.BLACK));
		
		cell[0][1].setBorder(BorderFactory.createMatteBorder(
				0, 1, 3, 2, Color.BLACK));	
		cell[1][1].setBorder(BorderFactory.createMatteBorder(
				0, 1, 3, 2, Color.BLACK));
		cell[2][1].setBorder(BorderFactory.createMatteBorder(
				0, 1, 0, 2, Color.BLACK));
		
		cell[0][2].setBorder(BorderFactory.createMatteBorder(
				0, 1, 3, 0, Color.BLACK));	
		cell[1][2].setBorder(BorderFactory.createMatteBorder(
				0, 1, 3, 0, Color.BLACK));
		cell[2][2].setBorder(BorderFactory.createMatteBorder(
				0, 1, 0, 0, Color.BLACK));
		
		// Create player panel, set properties
		playerPanel = new JPanel(new GridLayout(1, 2, 20, 0));
		playerPanel.setBackground( Color.WHITE );				
		
		// Create player labels, set props
		lblPlayer1 = new JLabel("Player 1: ");
		lblPlayer2 = new JLabel("Player 2:  Waiting for Player... ");
		lblPlayer1.setHorizontalAlignment(JLabel.CENTER);
		lblPlayer2.setHorizontalAlignment(JLabel.CENTER);
		lblPlayer1.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblPlayer2.setFont(new Font("SansSerif", Font.PLAIN, 14));
		
		// Add player labels to player panel
		playerPanel.add(lblPlayer1);
		playerPanel.add(lblPlayer2);
		
		// Add panels to GamePanel
		add(statusPanel, BorderLayout.NORTH);
		add(gridPanel, BorderLayout.CENTER);
		add(playerPanel, BorderLayout.SOUTH);
		
		// Add listeners for each invidual cell
		for(int i = 0; i < cell.length; i++){
			for(int j = 0; j < cell[i].length; j++){
				cell[i][j].addMouseListener(this);
			}
		}
	}
	
	/** Method to set playerlabel 1 for when it changes */
	public void setPlayer1Label(String player1){
		lblPlayer1.setText("Player 1: " + player1);
	}
	
	/** Method to set playerlabel 2 for when it changes */
	public void setPlayer2Label(String player2){
		lblPlayer2.setText("Player 2: " + player2);
	}	
	
	/** MouseListeners */
	public void mouseClicked(MouseEvent me){}
	public void mouseEntered(MouseEvent me){
		
		for(int i = 0; i < cell.length; i++){
			for(int j = 0; j < cell[i].length; j++){
				if(me.getSource() == cell[i][j]){
					if(TestGame.Instance.game.isTurn){
						if(TestGame.Instance.clientUser.equals(
							TestGame.Instance.game.getPlayer1()) && TestGame.Instance.game.cells[i][j] == ' '){
							cell[i][j].setToken('X');
						}
						if (TestGame.Instance.clientUser.equals(
								TestGame.Instance.game.getPlayer2()) && TestGame.Instance.game.cells[i][j] == ' '){
							cell[i][j].setToken('O');
						}
					}
				}
			}
		}
	}	
	public void mouseExited(MouseEvent me){
		for(int i = 0; i < cell.length; i++){
			for(int j = 0; j < cell[i].length; j++){
				if(me.getSource() == cell[i][j]){
					if(TestGame.Instance.game.isTurn && TestGame.Instance.game.cells[i][j] == ' '){
						cell[i][j].setToken(' ');						
					}
				}
			}
		}
	}
	public void mousePressed(MouseEvent me){}
	public void mouseReleased(MouseEvent me){
		for(int i = 0; i < cell.length; i++){
			for(int j = 0; j < cell[i].length; j++){
				if(me.getSource() == cell[i][j]){
					TestGame.Instance.sendMove("0, 0" , i, j);
				}
			}			
		}	
	}		
	
	// Create cell class - Listing 33.12
	public class Cell extends JPanel {
		int row;
		int column;
		
		private char token = ' ';
		
		public Cell(int row, int column){
			this.row = row;
			this.column = column;
			//this.setBorder(new LineBorder(Color.BLACK, 1));
			this.setBackground( Color.WHITE );
		}
		
		public void setToken(char c){
			token = c;
			repaint();
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			if (token == 'X'){				
				BasicStroke line =
				        new BasicStroke(10.0f,
				                        BasicStroke.CAP_ROUND,
				                        BasicStroke.JOIN_ROUND);
				((Graphics2D) g).setStroke(line);
				g.setColor( new Color(119, 159, 140) );
				g.drawLine(10, 10, getWidth() - 10, getHeight() - 10);
				g.drawLine(getWidth() - 10, 10, 10, getHeight() - 10);

			}
			else if (token == 'O'){
				BasicStroke circle =
				        new BasicStroke(10.0f,
				                        BasicStroke.CAP_ROUND,
				                        BasicStroke.JOIN_BEVEL);
				((Graphics2D) g).setStroke(circle);
				g.setColor( new Color(50, 165, 224) );
				g.drawOval(10,  10,  getWidth() - 20, getHeight() - 20);

			}
		}
	}
}

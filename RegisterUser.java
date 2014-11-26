import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;


public class RegisterUser extends JFrame implements MouseListener {
	
	JLabel lblRegisterAccount, lblUsername, lblPassword, lblConfPass;
	JTextField txtUsername, txtPassword, txtConfPass;
	
	JLabel lblUsernameError, lblPasswordError, lblConfPassError;
	
	JButton jbtRegister, jbtCancel;
	
	JPanel registerInfoPanel, jbtPanel;
	
	Boolean usernameValid = false, 
			passwordValid = false, 
			passwordMatch = false;
	
	public RegisterUser() {// Set details for frame
		setTitle("Register");
		setLayout(new BorderLayout(0, 0));
		setSize(425, 300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(false);
		
		// Set up registerInfoPanel to get Register info
		SpringLayout layout = new SpringLayout();
		
		registerInfoPanel = new JPanel(layout);
		registerInfoPanel.setBackground( new Color(47, 47, 47) );
		
		
		lblRegisterAccount = new JLabel("Register Account");
		lblUsername = new JLabel("Username: ");
		lblPassword = new JLabel("Password: ");
		lblConfPass = new JLabel("Confirm Password: ");
		
		lblUsernameError = new JLabel();
		lblPasswordError = new JLabel();
		lblConfPassError = new JLabel();
		
		
		lblRegisterAccount.setFont(new Font("SansSerif", Font.BOLD, 18));
		lblRegisterAccount.setForeground( new Color(119, 159, 140) );
		setLabelFieldProps(lblUsername);
		setLabelFieldProps(lblPassword);
		setLabelFieldProps(lblConfPass);
		
		setErrorProps(lblUsernameError);
		setErrorProps(lblPasswordError);
		setErrorProps(lblConfPassError);
		
		txtUsername = new JTextField(10);
		txtPassword = new JTextField(10);
		txtConfPass = new JTextField(10);
		
		setTextFieldProps(txtUsername);
		setTextFieldProps(txtPassword);
		setTextFieldProps(txtConfPass);		
		
		jbtRegister = new JButton("Register");
		jbtCancel = new JButton("Cancel");
		jbtRegister.setBackground(new Color(61, 61, 61));
		jbtCancel.setBackground(new Color(61, 61, 61));
		jbtRegister.setForeground(Color.WHITE);
		jbtCancel.setForeground(Color.WHITE);
		
		jbtPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
		jbtPanel.setBackground(new Color(41, 41, 41));
		
		registerInfoPanel.add(lblRegisterAccount);
		registerInfoPanel.add(lblUsername);
		registerInfoPanel.add(txtUsername);
		registerInfoPanel.add(lblPassword);
		registerInfoPanel.add(txtPassword);
		registerInfoPanel.add(lblConfPass);
		registerInfoPanel.add(txtConfPass);
		registerInfoPanel.add(lblUsernameError);
		registerInfoPanel.add(lblPasswordError);
		registerInfoPanel.add(lblConfPassError);
		
		// Set various positions for components on PersonInfo Panel
		setSpringLayoutFields(layout, lblRegisterAccount,
				registerInfoPanel, 75, 15);
		setSpringLayoutFields(layout, lblUsername, txtUsername,
				registerInfoPanel, 52, 65);
		setSpringLayoutFields(layout, lblUsernameError,
				txtUsername, 75, 2);
		setSpringLayoutFields(layout, lblPassword, txtPassword,
				registerInfoPanel, 53, 96);
		setSpringLayoutFields(layout, lblPasswordError,
				txtPassword, 75, 2);
		setSpringLayoutFields(layout, lblConfPass, txtConfPass,
				registerInfoPanel, 5, 127);
		setSpringLayoutFields(layout, lblConfPassError,
				txtConfPass, 75, 2);

		
		jbtPanel.add(jbtRegister);
		jbtPanel.add(jbtCancel);
		
		jbtRegister.addMouseListener(this);
		jbtCancel.addMouseListener(this);
		
		add(registerInfoPanel, BorderLayout.CENTER);
		add(jbtPanel, BorderLayout.SOUTH);
	}
	
	/** MouseListeners */
	public void mouseClicked(MouseEvent me){}
	public void mouseEntered(MouseEvent me){}
	public void mouseExited(MouseEvent me){}
	public void mousePressed(MouseEvent me){}
	public void mouseReleased(MouseEvent me){
		if(me.getSource() == jbtRegister){
			if(!txtUsername.getText().equals("")){
				TestGame.Instance.RegisterUsername(txtUsername.getText());
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(txtUsername.getText().equals("")){
				lblUsernameError.setText("*Username Required");
				setUsernameValid(false);
			}
			else if(txtUsername.getText().contains(" ")){
				lblUsernameError.setText("*Spaces not allowed");
				setUsernameValid(false);
			}
			else if(TestGame.Instance.usernameInUse){
				lblUsernameError.setText("*Username already in use");
				setUsernameValid(false);
			}
			else{
				lblUsernameError.setText("");
				setUsernameValid(true);
			}
			
			if(txtPassword.getText().equals("")){
				lblPasswordError.setText("*Password Required");
				setPasswordValid(false);
			}
			else if(txtPassword.getText().contains(" ")){
				lblPasswordError.setText("*Spaces not allowed");
				setPasswordValid(false);
			}
			else{
				lblPasswordError.setText("");
				setPasswordValid(true);
			}
			
			if(!txtConfPass.getText().equals(txtPassword.getText())){
				lblConfPassError.setText("*Passwords must match");
				setPasswordMatch(false);
			}
			else{
				lblConfPassError.setText("");
				setPasswordMatch(true);
			}
			if(isUsernameValid() && isPasswordValid() && isPasswordMatch()){
				TestGame.Instance.Register(txtUsername.getText(), txtPassword.getText());
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.dispose();
			}
		}
		if(me.getSource() == jbtCancel){
			this.dispose();
		}
	}
	
	/** Position for SpringLayout Components */
	public void setSpringLayoutFields(
			SpringLayout layout, JLabel lbl, 
			JTextField txt, int lblX, int y){
		layout.putConstraint(SpringLayout.WEST, lbl,
                45 + lblX,
                SpringLayout.WEST, txt);
		layout.putConstraint(SpringLayout.NORTH, lbl,
                y,
                SpringLayout.NORTH, txt);
	}
	
	/** Position for SpringLayout Components */
	public void setSpringLayoutFields(
			SpringLayout layout, JLabel lbl, 
			JPanel p, int lblX, int y){
		layout.putConstraint(SpringLayout.WEST, lbl,
                45 + lblX,
                SpringLayout.WEST, p);
		layout.putConstraint(SpringLayout.NORTH, lbl,
                y,
                SpringLayout.NORTH, p);
	}
	
	public void setSpringLayoutFields(
			SpringLayout layout, JLabel lbl, 
			JTextField txt, JPanel p, int lblX, int y){
		layout.putConstraint(SpringLayout.WEST, lbl,
                10 + lblX,
                SpringLayout.WEST, p);
		layout.putConstraint(SpringLayout.NORTH, lbl,
                y + 2,
                SpringLayout.NORTH, p);
		layout.putConstraint(SpringLayout.WEST, txt,
                10,
                SpringLayout.EAST, lbl);
		layout.putConstraint(SpringLayout.NORTH, txt,
                y,
                SpringLayout.NORTH, p);		
	} 
	
	public void setTextFieldProps(JTextField txt){
		txt.setBackground( new Color(47, 47, 47) );
		txt.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder( new Color(216, 220, 222) ),
				BorderFactory.createLoweredBevelBorder()));
		txt.setForeground( Color.WHITE );
		txt.setCaretColor( Color.WHITE );
	}
	
	public void setLabelFieldProps(JLabel txt){
		txt.setForeground( new Color(216, 220, 222) );
	}
	
	public void setErrorProps(JLabel txt){
		txt.setForeground( new Color(228, 43, 43) );
		txt.setFont(new Font("SansSerif", Font.PLAIN, 12));
	}
	
	public void setUsernameValid(Boolean valid){
		usernameValid = valid;
	}
	
	public void setPasswordValid(Boolean valid){
		passwordValid = valid;
	}
	
	public void setPasswordMatch(Boolean valid){
		passwordMatch = valid;
	}
	
	public Boolean isUsernameValid(){
		return usernameValid;
	}
	
	public Boolean isPasswordValid(){
		return passwordValid;
	}
	
	public Boolean isPasswordMatch(){
		return passwordMatch;
	}
}

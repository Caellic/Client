import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class TestGame implements Runnable{
	public static TestGame Instance;
	
	Boolean isValidLogin;
	Boolean isDuplicateLogin;
	Boolean usernameInUse = false;
	ClientFrame clientFrame;
	String userList = "";
	GameObj game;
	String clientUser;
	
	public static void main(String[] args){       
		TestGame.Instance = new TestGame();
	}		
	
	private Socket socket;
	private PrintWriter out;
	private ObjectOutputStream oout;
	private ObjectInputStream ois;
	
	public TestGame(){
		System.out.println("Initializing Client.");
		init();
		clientFrame = new ClientFrame();		
	}
	
	private void init(){
		String serverIP = System.getProperty("serverIP");
		try {
			socket = new Socket(serverIP, 40000);
			oout = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
			
			// Start a background thread for receiving messages
			new Thread( this ).start();	
		} catch (Exception ex) {
			ex.printStackTrace();
		}		
	}
	
	@Override
	public void run() {
		// Receive messages one-by-one, forever
		while (true) {
			// Get the next message
			Message input = null;
			
			try{				
				try {
					input = (Message) ois.readObject();
					
				} catch (Exception ex){
					System.out.println(socket);
					ex.printStackTrace();
					System.out.println("We broke, hi.");
					break;
				}
				
				if (input != null) {
					
					switch(input.MessageType){
						case "PASSED": 
							onPassedLogin(input);
							break;
						case "FAILED":
							onIncorrectLogin();
							break;
						case "DUPLICATELOGIN":
							onDuplicateLogin();
							break;
						case "RGSTERUSERPASSED":
							onValidRgsteredUsername();
							break;
						case "RGSTERUSERFAILED":
							onInvalidRgsteredUsername();
							break;
						case "USERNAMES":
							onGetUsernameList(input);
							break;
						case "ENTERED":
							onUserEntered(input);
							break;
						case "STARTEDGAME":
							onStartedGame(input);
							break;
						case "PLAYER2JOINED":
							onPlayer2Joined(input);
							break;
						case "JOINFAILED":
							onFailedJoin();
							break;
						case "UPDATEDGAME":
							onUpdateGame(input);
							break;
						case "GAMEOVER":
							onGameOver(input);
							break;
						case "PLAYERLEFTGAME":
							onPlayerLeftGame(input);
							break;
						case "EXITED":
							onUserExited(input);
							break;
						case "":
							break;
						default:
							onChat(input);
							break;
					}
				}
			}
			catch(Exception ex){
				ex.printStackTrace();
			}	
		}
	}

	/***********************************************************
	 *			 	Receiving Messages Methods		 		   *
	 ***********************************************************/
	public void onPassedLogin(Message input){
		System.out.println("You passed.");
		isDuplicateLogin = false;
		isValidLogin = true;
		clientUser = (String) input.MessageData;
	}
	
	public void onIncorrectLogin(){
		System.out.println("You failed to login.");
		isDuplicateLogin = false;
		isValidLogin = false;
	}
	
	public void onDuplicateLogin(){
		System.out.println("This username is already logged in.");
		isDuplicateLogin = true;
		isValidLogin = false;
	}
	
	public void onValidRgsteredUsername(){
		usernameInUse = false;
	}
	
	public void onInvalidRgsteredUsername(){
		usernameInUse = true;
	}
	
	public void onGetUsernameList(Message input){
		userList = (String) input.MessageData;
		clientFrame.setUsers(userList);
	}
	
	public void onUserEntered(Message input){
		String user = (String) input.MessageData;
		clientFrame.chatPanel.setUserEntered(user);

		System.out.println("This code executed");
	}
	
	public void onStartedGame(Message input){
		game = (GameObj) input.MessageData;
	}
	
	public void onPlayer2Joined(Message input){
		game = (GameObj) input.MessageData;
		clientFrame.mainMenu.gamePanel.setPlayer1Label(game.getPlayer1());
		clientFrame.mainMenu.gamePanel.setPlayer2Label(game.getPlayer2());
		System.out.println("It is players turn: " + game.isTurn );
		
		for(int i = 0; i < game.cells.length; i++){
			for(int j = 0; j < game.cells[i].length; j++){
					clientFrame.mainMenu.gamePanel.cell[i][j].setToken(game.cells[i][j]);
			}
		}
		
		clientFrame.mainMenu.gamePanel.lblStatus.setText("Player 2 has joined! Player 1 moves first.");				
		
	}
	
	public void onFailedJoin(){
		clientFrame.mainMenu.isJoinable = false;
	}
	
	public void onUpdateGame(Message input){
		System.out.println("It is players turn: " + game.isTurn );
		game = (GameObj) input.MessageData;
		for(int i = 0; i < game.cells.length; i++){
			for(int j = 0; j < game.cells[i].length; j++){
					clientFrame.mainMenu.gamePanel.cell[i][j].setToken(game.cells[i][j]);
			}
		}
		
		if(game.isTurn){
			clientFrame.mainMenu.gamePanel.lblStatus.setText("It is your turn.");
		}
		else{
			clientFrame.mainMenu.gamePanel.lblStatus.setText("Waiting for other player to make a move.");
		}		
	}
	
	public void onGameOver(Message input){
		game = (GameObj) input.MessageData;
		
		for(int i = 0; i < game.cells.length; i++){
			for(int j = 0; j < game.cells[i].length; j++){
					clientFrame.mainMenu.gamePanel.cell[i][j].setToken(game.cells[i][j]);
			}
		}
		
		if(game.winner.equals(clientUser)){
			System.out.println("You are the winner");
			clientFrame.mainMenu.gamePanel.lblStatus.setText("You WON!");
		}
		else if(game.winner.equals("Draw")){
			System.out.println("The game was a tie.");
			clientFrame.mainMenu.gamePanel.lblStatus.setText("It's a DRAW!");
		}
		else{
			System.out.println("You are the loser.");
			clientFrame.mainMenu.gamePanel.lblStatus.setText("You LOST!");
		}
	}
	
	public void onPlayerLeftGame(Message input){
		game = (GameObj) input.MessageData;
		for(int i = 0; i < game.cells.length; i++){
			for(int j = 0; j < game.cells[i].length; j++){
					clientFrame.mainMenu.gamePanel.cell[i][j].setToken(game.cells[i][j]);
			}
		}				
		
		clientFrame.mainMenu.gamePanel.setPlayer1Label(game.getPlayer1());
		clientFrame.mainMenu.gamePanel.setPlayer2Label("Waiting for Player 2...");
		clientFrame.mainMenu.gamePanel.lblStatus.setText("Player has left the game.");
	}
	
	public void onUserExited(Message input){
		String user = (String) input.MessageData;
		clientFrame.chatPanel.setUserExited(user);
		
	}
	
	public void onChat(Message input){
		clientFrame.chatPanel.setMsg(input.MessageType);
	}
	
	public String readChat(){
		String result = "";
		try{						
			Message input = (Message) ois.readObject();
			System.out.println("got this: " + input.MessageType);
			
			result = input.MessageType;			

		} catch(Exception ex){
			ex.printStackTrace();
		}
		
		return result;
	}	
	
	/***********************************************************
	 *			 	Sending Messages Methods		 		   *
	 ***********************************************************/
	public void testRegistration(String username){
		SendMessage(username, "VALIDATEREGISTRATION");
	}
	
	public void Register(String username, String pass){
		User testUser = new User();
		testUser.username = username;
		testUser.password = pass;

		SendMessage(testUser, "REGISTER");
	}
	
	public void Login(String username, String pass){
		User testUser = new User();
		testUser.username = username;
		testUser.password = pass;

		SendMessage(testUser, "LOGIN");
	}
	
	public void Chat(String message){
		SendMessage(message, "CHAT");
	}
	
	public void sendMove(String move, int row, int col){
		String move1 = (row + ", " + col);
		if(!game.isOpenGame && game.isTurn){
			if(game.cells[row][col] == ' '){
				System.out.println(game.isTurn);
				SendMessage(move1, "MOVEMADE");
			}
		}
	}	
	
	/***********************************************************
	 *				  Send Message To Server			 	   *
	 ***********************************************************/
	public void SendMessage(Object obj, String messageType){
		Message msg = new Message();
		msg.MessageType = messageType;
		msg.MessageData = obj;

		try{
			oout.writeObject(msg);
			oout.flush();
			oout.reset();
			//Message input = (Message) ois.readObject();
			//System.out.println("got this: " + input.MessageType);
		} catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public void SendMessage(String message){
		out.println(message);
		out.flush();
	}
}

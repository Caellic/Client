import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;



public class TestGame implements Runnable{
	public static TestGame Instance;
	public Boolean validLogin;
	public Boolean duplicateLogin;
	Boolean usernameInUse = false;
	ClientFrame clientFrame;
	String userList = "";
	GameObj game = new GameObj();
	
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
//			out = new PrintWriter(socket.getOutputStream(), true);
			oout = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());	
			// Start a background thread for receiving messages
			new Thread( this ).start();	
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	public void setValidLogin(Boolean isValid){
		validLogin = isValid;
	}
	
	public Boolean isValidLogin(){
		return validLogin;
	}
	
	public void setDuplicateLogin(Boolean isValid){
		duplicateLogin = isValid;
	}
	
	public Boolean isDuplicateLogin(){
		return duplicateLogin;
	}
	
	public void RegisterUsername(String username){
		SendMessage(username, "REGISTERUSER");
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
	
	public void sendMove(int row, int col){
		//SendMessage()
	}
	
	public void readMessage(){			
		try{						
			Message input = (Message) ois.readObject();
			System.out.println("got this: " + input.MessageType);
			
			if(input.MessageType.equals("PASSED")){
				System.out.println("You passed.");
				setDuplicateLogin(false);
				setValidLogin(true);
			}
			else if(input.MessageType.equals("FAILED")){
				System.out.println("You failed.");
				setDuplicateLogin(false);
				setValidLogin(false);
			}
			else if(input.MessageType.equals("DUPLICATELOGIN")){
				System.out.println("This username is already logged in.");
				setDuplicateLogin(true);
				setValidLogin(false);
			}
			else if(input.MessageType.equals("USERPASSED")){
				usernameInUse = false;
			}
			else if(input.MessageType.equals("USERFAILED")){
				usernameInUse = true;
			}				
			else if(input.MessageType.equals("-USERNAMES ")){
				userList = (String) input.MessageData;
				clientFrame.setUsers(userList);
			}
			else if(input.MessageType.equals("-ENTERED ")){
				String user = (String) input.MessageData;
				clientFrame.setUserEntered(user);

				System.out.println("This code executed");
				
			}
			else if(input.MessageType.equals("STARTEDGAME")){
				game.cloneProps((GameObj) input.MessageData);
			}
			else if(input.MessageType.equals("PLAYER2JOINED")){
				game.setPlayer2((String) input.MessageData);
				clientFrame.mainMenu.gamePanel.setPlayer1Label(game.getPlayer1());
				clientFrame.mainMenu.gamePanel.setPlayer2Label(game.getPlayer2());
				
			}
			else if(input.MessageType.equals("JOINFAILED")){
				clientFrame.mainMenu.isJoinable = false;
			}
			else if(input.MessageType.equals("-EXITED ")){
				String user = (String) input.MessageData;
				clientFrame.setUserExited(user);

				System.out.println("This code executed");
				
			}
			else if(!input.MessageType.equals("")){
				clientFrame.setMsg(input.MessageType);
			}

			else{
				System.out.println("We can do this!.");	
			}

		} catch(Exception ex){
			ex.printStackTrace();
		}
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
	
	public void SendMessage(Object obj, String messageType){
		Message msg = new Message();
		msg.MessageType = messageType;
		msg.MessageData = obj;

		try{
			oout.writeObject(msg);
			oout.flush();
			
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

	@Override
	public void run() {
			// Receive messages one-by-one, forever
			while (!socket.isClosed()) {
				// Get the next message
				readMessage();
			}
	}

}

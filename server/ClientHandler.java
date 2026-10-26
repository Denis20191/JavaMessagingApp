import java.net.*;
import java.io.*;
import java.time.LocalTime;

public class ClientHandler extends Thread {
	
	private Socket connection;
	private BufferedReader in;
	private PrintWriter out;
	
	private static int userCount = 0;
	
	private String username;
	
	public ClientHandler(Socket connection) {
		this.connection = connection;
		
		try {
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			out = new PrintWriter(connection.getOutputStream());
		} catch(Exception e) {
			System.out.println("Connection IO error!");
			e.printStackTrace();
		}
		
		username = generateNewUsername();
	}
	
	/* Generates a new username. Increments user count to use for next username generation
	 * @return New username (like "USER_x")
	 * */
	private synchronized String generateNewUsername() {
		userCount++;
		
		return "USER_" + (userCount);
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				String message = in.readLine();
				
				if(message == null || message.equals("DISCONNECT")) break;
				
				type(message);
			} catch(Exception e) {
				System.out.println("Exception encountered!");
				break;
			}
		}
		
		try {
			in.close();
			out.close();
			connection.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Connection Terminated!");
	}
	
	/* Helper method to type a message.
	 * Also type's the server's current time and the user's username
	 * @param message Message body to type
	 * */
	private void type(String message) {
		String currentTime = "[" + LocalTime.now() + "]";
		System.out.println(currentTime + " " + username + ": " + message);
	}
}

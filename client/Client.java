import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client {
	private static Socket connection;
	private static BufferedReader in;
	private static PrintWriter out;
	
	private static Scanner input;
	
	public static final int SERVER_PORT = 54321;
	
	public static void main(String[] args) {
		try {
			connection = new Socket("localhost", SERVER_PORT);
			
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			out = new PrintWriter(connection.getOutputStream());
			input = new Scanner(System.in);
		} catch(Exception e) {
			System.out.println("Client initialization failed!");
			e.printStackTrace();
			return;
		}
		
		String message = "";
		while(true) {
			message = input.nextLine();
			
			if(message.equalsIgnoreCase("exit")) break;
			else sendMessage(message);
		}
	}
	
	private static void sendMessage(String message) {
		out.println(message);
		out.flush();
	}
	
	private static void sendDisconnect() {
		out.print("DISCONNECT");
		out.flush();
	}
}

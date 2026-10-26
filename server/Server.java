import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Server {
	public static final int SERVER_PORT = 54321;
	
	private static ServerSocket sSocket = null;
	
	public static void main(String[] args) {
		try {
			sSocket = new ServerSocket(SERVER_PORT);
		} catch(Exception e) {
			System.out.println("Server initialization failed!");
			e.printStackTrace();
			return;
		}
		
		while(true) {
			try {
				Socket conn = sSocket.accept();
				
				Thread handler = new ClientHandler(conn);
				handler.start();
			} catch(Exception e) {
				System.out.println("Connection failed!");
				e.printStackTrace();
			}
		}
	}
}

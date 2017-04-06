package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * 
 * @author julian Hultgren
 *
 */
public class GameServer implements Runnable{
	private ServerSocket serverSocket;
	private Thread serverThread = new Thread(this);
	
	public GameServer(int port){
		try{
			serverSocket = new ServerSocket(port);
			serverThread.start();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void run() {
		System.out.println("Server running...");
		System.out.println("Listening for clients...");
		while(true){
			try{
				Socket socket = serverSocket.accept();
				System.out.println("Client connected");
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		
	}
	public static void main(String[] args) {
		GameServer ss = new GameServer(3520);
	}
}

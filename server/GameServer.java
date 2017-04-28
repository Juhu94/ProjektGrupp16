package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JLabel;
/**
 * 
 * @author Julian Hultgren
 *
 */
public class GameServer implements Runnable{

	private ServerSocket serverSocket;
	private Thread serverThread = new Thread(this);
//	private ArrayList<Socket> connectedUsers = new ArrayList<Socket>();
	private HashMap<String, ClientHandler> clientMap = new HashMap<String, ClientHandler>();
	
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
//				connectedUsers.add(socket);
				System.out.println("Client connected from..." +socket.getRemoteSocketAddress());
				new ClientHandler(socket).start();
			}catch(IOException e){
				e.printStackTrace();
			}
		}		
	}
	
	private class ClientHandler extends Thread{
		private Socket socket;
		
		public ClientHandler(Socket socket) {
			this.socket = socket;
		}
		
		public void run() {
			while(socket.isConnected()){
				try{
					ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
					Object object = input.readObject();
					if(object instanceof String){
						String username = (String)object;
						clientMap.put(username, this);
					}
					
				}catch (IOException | ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
//		
//		public void Send(Message inMessage) {
//			try{
//				for(Socket user : connectedUsers) {
//					ObjectOutputStream output = new ObjectOutputStream(user.getOutputStream());
//					output.writeObject(inMessage);
//					System.out.println("Skickar ut till alla klienter");
//					output.flush();
//				}
//			}catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
	}
//	public static void main(String[] args) {
//		GameServer ss = new GameServer(3520);
//	}
}
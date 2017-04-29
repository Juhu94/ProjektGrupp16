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
 * Version 0.6
 */
public class GameServer implements Runnable{

	private ServerSocket serverSocket;
	private Thread serverThread = new Thread(this);
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
				System.out.println("Client connected from..." +socket.getRemoteSocketAddress());
				new ClientHandler(socket).start();
			}catch(IOException e){
				e.printStackTrace();
			}
		}		
	}
	
	private class ClientHandler extends Thread{
		private Socket socket;
		ObjectInputStream input;
		ObjectOutputStream output;
		public ClientHandler(Socket socket) {
			this.socket = socket;
			try {
				output = new ObjectOutputStream(socket.getOutputStream());
				input = new ObjectInputStream(socket.getInputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public void run() {
			while(socket.isConnected()){
				try{
					Object object = input.readObject();
					if(object instanceof String){
						String username = (String)object;
						clientMap.put(username, this);
					}
					
				}catch (IOException | ClassNotFoundException e) {
					closeSocket();
					e.printStackTrace();
				}
			}
		}
		public void closeSocket() {
			try {
				socket.close();
				input.close();
				clientMap.remove(this);
				clientMap.toString();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

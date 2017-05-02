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

import client.Tile;
/**
 * 
 * @author Julian Hultgren
 * Version 0.6
 */
public class GameServer implements Runnable{

	private ServerSocket serverSocket;
	private Thread serverThread = new Thread(this);
	private HashMap<String, ClientHandler> clientMap = new HashMap<String, ClientHandler>();
	private HashMap<String, Character> characterMap = new HashMap<String, Character>();
	
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
				if (clientMap.size() <= 6){
					System.out.println("Client connected from..." +socket.getRemoteSocketAddress());
					new ClientHandler(socket).start();
				}else{
					System.out.println("Client can't connected from..." +socket.getRemoteSocketAddress() + " due to game being full");
				}
			}catch(IOException e){
				e.printStackTrace();
			}
		}		
	}
	
	private class ClientHandler extends Thread{
		private Socket socket;
		ObjectInputStream input;
		ObjectOutputStream output;
		private String username;
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
						username = (String)object;
						clientMap.put(username, this);
					}
					
				}catch (IOException | ClassNotFoundException e) {
					closeSocket();
					e.printStackTrace();
				}
			}
		}
		public void createCharacter(){
			//in progress
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

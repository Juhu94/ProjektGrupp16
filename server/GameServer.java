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
import javax.swing.JOptionPane;

import client.Tile;
/**
 * 
 * @author Julian Hultgren, Lukas Persson
 * Version 0.7
 */
public class GameServer implements Runnable{

	private ServerSocket serverSocket;
	private Thread serverThread = new Thread(this);
	private HashMap<String, ClientHandler> clientMap = new HashMap<String, ClientHandler>();
	private HashMap<Integer, String> clientMapid = new HashMap<Integer, String>();
	private HashMap<String, Character> characterMap = new HashMap<String, Character>();
	
	private int counter = 1;
	private int id = 1;
	
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
		private int nbrOfPlayers;
		
		
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
						clientMapid.put(id, username);
						id++;
					}
					if(object instanceof Boolean){
						boolean enableButtons = (boolean)object;
						clientsTurn(enableButtons);
					}
					
				}catch (IOException | ClassNotFoundException e) {
					closeSocket();
					e.printStackTrace();
				}
			}
		}
		/**
		 * Method to enable the buttons for the next player
		 * goes through the number of players and when it reaches
		 * the number of players it goes back down to 1
		 * @param enableButtons boolean
		 */
		public void clientsTurn(boolean enableButtons){
			nbrOfPlayers = clientMap.size();
			if(counter == nbrOfPlayers){
				String username = clientMapid.get(counter);
				ClientHandler ch = clientMap.get(username);
				try {
					ch.output.writeObject(enableButtons);
					ch.output.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				counter = 1;
			}
			else{
				String username = clientMapid.get(counter);
				ClientHandler ch = clientMap.get(username);
				try{
					ch.output.writeObject(enableButtons);
					ch.output.flush();
				}catch(IOException e){
					e.printStackTrace();
				}
				counter++;
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
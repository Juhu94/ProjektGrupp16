package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.text.AbstractDocument.BranchElement;

import client.Tile;
import gui.ServerFrame;
/**
 * 
 * @author Julian Hultgren, Lukas Persson, Erik Johansson, Simon Börjesson
 * Version 0.7
 */
public class GameServer implements Runnable{

	private ServerSocket serverSocket;
	private Thread serverThread = new Thread(this);
	private HashMap<String, ClientHandler> clientMap = new HashMap<String, ClientHandler>();
	private HashMap<Integer, String> clientMapid = new HashMap<Integer, String>();
	private HashMap<String, client.Character> characterMap = new HashMap<String, client.Character>();
	private Random rad = new Random();
	private ServerFrame ui;
	
	private int counter = 1;
	private int id = 1;
	
	public GameServer(int port, ServerFrame ui){
		this.ui = ui;
		try{
			serverSocket = new ServerSocket(port);
			serverThread.start();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void run() {
		System.out.println("Server running...");
		System.out.println("Server: Listening for clients...");
		while(true){
			try{
				Socket socket = serverSocket.accept();
				if (clientMap.size() <= 6){
					System.out.println("Server: Client connected from..." +socket.getRemoteSocketAddress());
					new ClientHandler(socket).start();
				}else{
					System.out.println("Server: Client can't connected from..." +socket.getRemoteSocketAddress() + " due to game being full");
				}
			}catch(IOException e){
				e.printStackTrace();
			}
		}		
	}
	
	public void startGame(){
		System.out.println("Server: starta spelet på servern "+id);
		for(int i = 1; i < id; i++){
			clientMap.get(clientMapid.get(i)).createCharacter(clientMapid.get(i));
		}
		clientMap.get(clientMapid.get(1)).clientsTurn(true);;
	}
	
	private class ClientHandler extends Thread{
		private Socket socket;
		ObjectInputStream input;
		ObjectOutputStream output;
		private String sInput;
		private int nbrOfPlayers;
		private int playerid;
		
		
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
						sInput = (String)object;
						if(sInput.equals("ENDTURN")){
							clientsTurn(true);
						}
						else{
							System.out.println("Server: Mottagit username");
							clientMap.put(sInput, this);
							clientMapid.put(id, sInput);
							playerid = id;
							id++;
							ui.addUser(sInput);
							for(ClientHandler ch : clientMap.values()){
								try{
									ch.output.writeObject(sInput);
									ch.output.flush();
								}catch (IOException e) {
									e.printStackTrace();
								}
							}
						}

					}
					if(object instanceof client.Character) {
						client.Character character = (client.Character) object;
						updateCharPos(character);
					}
					
				}catch (IOException | ClassNotFoundException e) {
					closeSocket();
					Thread.currentThread().stop();
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
			while(characterMap.get(clientMapid.get(counter)).sleeping() > 0){
				if (counter == nbrOfPlayers){
					counter = 1;
				}
				else{
					counter++;
				}
			}
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
		
		/**
		 * Creates a character and places it on a empty starting position
		 */

		public synchronized void createCharacter(String name) {
			System.out.println("createCHarizard");
			client.Character myCharacter = new client.Character(name, -1, -1);
			while (myCharacter.getRow() == -1) {
				System.out.println(myCharacter.getRow());
				int startPos = rad.nextInt(6);
				System.out.println("-------------------------------\n" + startPos);
				switch (startPos) {
				case 0:
					if (!checkCharacterAtPos(9, 4)) {
						myCharacter.setPos(9, 4);
					}
					break;
				case 1:
					if (!checkCharacterAtPos(6, 22)) {
						myCharacter.setPos(6, 22);
					}
					break;
				case 2:
					if (!checkCharacterAtPos(9, 35)){
						myCharacter.setPos(9, 35);
						}
					break;
				case 3:
					if(!checkCharacterAtPos(23, 4)){
						myCharacter.setPos(23, 4);
						}
					break;
				case 4:
					if(!checkCharacterAtPos(30, 44)){
						myCharacter.setPos(30, 44);
						}
					break;
				case 5:
					if(!checkCharacterAtPos(36, 37)){
						myCharacter.setPos(36, 37);
						}
					break;
				}
			}
			System.out.println("Server: Karaktär skapad namn: " + name + " Row: " + myCharacter.getRow() + " Col: " + myCharacter.getCol());
			characterMap.put(name, myCharacter);
			updateCharPos(myCharacter);
		}
		
		/**
		 * Checks if there is a character at the given position, returns true if
		 * there is and false if there is not
		 * @param row	int
		 * @param col	int
		 * @return 		boolean
		 */

		public boolean checkCharacterAtPos(int row, int col) {
			String userID;
			
			if(characterMap.isEmpty()){
				return false;
			}
			
			for (int i = 1; i <= characterMap.size(); i++) {
				System.out.println(i);
				if(clientMapid.containsKey(i)){
					userID = clientMapid.get(i);
					if (characterMap.get(userID).getRow() == row && characterMap.get(userID).getCol() == col) {
						return true;
					}
				}
				
//				if (clientMapid.containsKey(i)) {
//					userID = clientMapid.get(i);
//					if (characterMap.get(userID).getRow() == row && characterMap.get(userID).getCol() == col) {
//						return true;
//					}
//				}  
			}
			System.out.println("Tilldelad plats");
			return false;
		}
		
		public void updateCharPos(client.Character charr) {
			ArrayList<String> connectedUsers = new ArrayList<String>();
			connectedUsers.addAll(clientMap.keySet());
			characterMap.put(charr.getName(), charr);
			for(ClientHandler ch : clientMap.values()){
				try{
					ch.output.writeObject(charr);
					ch.output.flush();
					System.out.println("Server: character uppdaterad");
				}catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		public void closeSocket() {
			try {
				socket.close();
				input.close();
				characterMap.remove(sInput);
				clientMapid.remove(playerid);
				clientMap.remove(sInput);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
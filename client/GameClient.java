package client;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import gui.ExtendedJLabel;
import gui.ViewerListener;
import server.GameServer;

/**
 * 
 * @author Julian Hultgren, Simon Börjesson, Lukas Persson, Erik Johansson
 * Version 1.2
 *
 */


//Klassen behövs utökas med funktioner för den som ska agera spelvärd
public class GameClient implements Serializable{
	
	private ObjectInputStream input;
	private	ObjectOutputStream output;
	private String username = "";
	private int iD;
	private Socket socket;
	private ArrayList<ViewerListener> listeners = new ArrayList<ViewerListener>();
	private int[] tilePos = new int[2];
	private Tile[][] map;
	private HashMap<String, client.Character> characterMap = new HashMap<String, client.Character>(); 
	
	private boolean clientTurn = true;
	private Connection connection;

	public GameClient(){
		System.out.println("Klient Startad");
		map = createMap();
		connection = new Connection();
	}
	public void sendUsername(String username) {
		this.username = username;
	}
	public void connect(String serverIp, int port, String username){
		new Connection(serverIp,port,username).start();
	}
	
	public void addListeners(ViewerListener listener) {
		listeners.add(listener);
	}
	public void startServer(){
		GameServer gs = new GameServer(3520);
	}
	public void startGame(){
		try {
			System.out.println("Startar matchen/väljer vems tur det är");
			output.writeObject("STARTGAME");
			output.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void endTurn(){
		try{
			System.out.println("slutar svänga");
			output.writeObject("ENDTURN");
			output.flush();
		}catch (IOException e){
			e.printStackTrace();
		}
	}
	/**
	 * Method that returns a random number from 1-6
	 * @return int
	 */
	public int throwDice(){
		Random rand = new Random();
		int diceNbr = rand.nextInt(6)+1;
		return diceNbr;
	}
	/**
	 * Method that uses the throwDice-method 
	 * to see if the shot hits another player
	 * @return boolean
	 */
	public boolean shootDice(){
		int roll = throwDice();
		if(roll == 2 || roll == 6){
			return true;
		}
		return false;
	}
	
	/**
	* Method that disconnects the client from the server
	*/
	public void disconnect() {
		try {
			socket.close();
			output.close();
			input.close();
			System.out.println("Uppkoppling avslutad");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Method to enable buttons in ServerFrame and ClientFrame
	 * @param enableButtons boolean
	 */
	public void enableButtons(boolean enableButtons){
		for(ViewerListener listener: listeners){
			listener.updateViewer();
		}
	}
	
	public void moveCharacter(String username, String direction){
		connection.moveChar(characterMap.get(username), direction);
	}
	
	/**
	* Inner class that handles the connection between client and server
	*/
	private class Connection extends Thread{
		private String ipAddress = "";
		private String username = "";
		private int port = 0;
		private int oldColThis;
		private int oldRowThis;
		
		public Connection(String ipAddress, int port, String username){
			this.ipAddress = ipAddress;
			this.port = port;
			this.username = username;
		}
			
		public Connection() {
			
		}

		public void run(){
			System.out.println("Client Running");
			try{
				socket = new Socket(ipAddress,port);	
				output = new ObjectOutputStream(socket.getOutputStream());
				input = new ObjectInputStream(socket.getInputStream());
				output.writeObject(username);
				output.flush();
			}catch (IOException e ){
				e.printStackTrace();
			}
				
			while(!Thread.interrupted()){
				try{
					Object object = input.readObject();
					if(object instanceof Integer){
						iD = (int)object;	
					}
					if(object instanceof Boolean){
						boolean enableButtons = (boolean)object;
						enableButtons(enableButtons);
					}
					if (object instanceof client.Character){
						client.Character character = (client.Character) object;
						updateCharacter(character);
					}
				}catch (IOException | ClassNotFoundException e){
					disconnect();
					Thread.currentThread().stop();
					e.printStackTrace();
				}
			}
		}
		
		
		public void moveChar(client.Character character, String direction){
			System.out.println(character.getCol());
			System.out.println(character.getRow());
			oldColThis = character.getCol();
			oldRowThis = character.getRow();
			System.out.println("i gameclient row: " + oldRowThis);
			System.out.println("i gameclient col: " + oldColThis);
			
			switch(direction){
			case "Right":
				character.setPos(oldRowThis, oldColThis + 1);
				break;
			case "Left":
				character.setPos(oldRowThis, oldColThis - 1);
				break;
			case "Up":
				character.setPos(oldRowThis - 1, oldColThis);
				break;
			case "Down":
				character.setPos(oldRowThis + 1, oldColThis);
				break;
			}
			
			try {
				output.writeObject(character);
				output.flush();
				System.out.println("karaktärobjekt skickat");
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		public void updateCharacter(client.Character character){
			String characterName = character.getName();
			System.out.println(characterName);
			int oldRow = 1;
			int oldCol = 1;
			if(characterMap.containsKey(characterName) && !characterName.equals(username)){
				oldRow = characterMap.get(characterName).getRow();
				oldCol = characterMap.get(characterName).getCol();
			}else if(characterMap.containsKey(characterName) && characterName.equals(username)){
				System.out.println(oldRow = oldRowThis);
				System.out.println(oldCol = oldColThis);
				
			}
			
			characterMap.put(characterName, character);
			for(ViewerListener listener: listeners){

				listener.paintCharacter(character.getRow(), character.getCol(), oldRow, oldCol);
				
				System.out.println("flytta gubbe -> viewer");
			}
		}
			
	}
	

	
	//temp background
	
	public String getTile(int row, int col){
		if(map[row][col].getBoat()){
			return "ORANGE";
		}else if (map[row][col].getRaft() || map[row][col].getCanon()){
			return "BLACK";
		} else {
			return map[row][col].getName();
		}
	}

	/**
	 * Creates a map 
	 * @return	Tile[][]	map array
	 */
	
	public Tile[][] createMap(){
		int[] mapint = new int[1927];
		try {
			Scanner input = new Scanner(new FileReader("files/map.txt"));
			 input.useDelimiter(",");
			for (int i = 0; i < 1927; i++){
				mapint[i] = input.nextInt();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Tile[][] map = new Tile[41][47];
		int temp = 0;
		int temp2 = 0;
		for (int i = 0; i < mapint.length; i++){
			temp2 = temp;
			temp = Math.round((i/47));
			
			if (mapint[i] == 1){
				map[temp][i % 47] = new WaterTile();
			}else if (mapint[i] == 2){
				map[temp][i % 47] = new GroundTile(false, false, false);
			}else if (mapint[i] == 3){
				map[temp][i % 47] = new JungleTile();
			}else if (mapint[i] == 4){
				map[temp][i % 47] = new SpecialTile();
			}
		}
		
		//------Special Tile---------------
		map[2][10].setSuccess(map[2][13]);
		map[2][10].setFail(map[6][11]);
		map[2][12].setSuccess(map[2][10]);
		map[2][12].setFail(map[6][11]);
		map[2][13].setSuccess(map[2][15]);
		map[2][13].setFail(map[6][14]);
		map[2][15].setSuccess(map[2][12]);
		map[2][15].setFail(map[6][14]);
		map[4][25].setSuccess(map[6][25]);
		map[4][25].setFail(map[5][25]);
		map[6][25].setSuccess(map[4][25]);
		map[6][25].setFail(map[5][25]);
		map[6][27].setSuccess(map[6][29]);
		map[6][27].setFail(map[5][28]);
		map[6][29].setSuccess(map[6][27]);
		map[6][29].setFail(map[5][28]);
		map[12][2].setSuccess(map[15][2]);
		map[12][2].setFail(map[13][2]);
		map[14][2].setSuccess(map[12][2]);
		map[14][2].setFail(map[13][2]);
		map[12][6].setSuccess(map[14][6]);
		map[12][6].setFail(map[13][6]);
		map[14][6].setSuccess(map[12][6]);
		map[14][6].setFail(map[13][6]);
		map[15][2].setSuccess(map[17][2]);
		map[15][2].setFail(map[16][2]);
		map[17][2].setSuccess(map[14][2]);
		map[17][2].setFail(map[16][2]);
		map[15][11].setSuccess(map[17][11]);
		map[15][11].setFail(map[16][11]);
		map[17][11].setSuccess(map[15][11]);
		map[17][11].setFail(map[16][11]);
		map[16][19].setSuccess(map[16][21]);
		map[16][19].setFail(map[16][20]);
		map[16][21].setSuccess(map[16][19]);
		map[16][21].setFail(map[16][19]);
		map[16][33].setSuccess(map[16][36]);
		map[16][33].setFail(map[16][34]);
		map[16][35].setSuccess(map[16][33]);
		map[16][35].setFail(map[16][34]);
		map[16][36].setSuccess(map[16][38]);
		map[16][36].setFail(map[16][37]);
		map[16][38].setSuccess(map[16][35]);
		map[16][38].setFail(map[16][37]);
		map[18][9].setSuccess(map[18][11]);
		map[18][9].setFail(map[18][10]);
		map[18][11].setSuccess(map[18][9]);
		map[18][11].setFail(map[18][10]);
		map[19][19].setSuccess(map[21][19]);
		map[19][19].setFail(map[20][19]);
		map[21][19].setSuccess(map[19][19]);
		map[21][19].setFail(map[20][19]);
		map[18][25].setSuccess(map[20][25]);
		map[18][25].setFail(map[19][25]);
		map[27][28].setSuccess(map[27][30]);
		map[27][28].setFail(map[27][29]);
		map[27][30].setSuccess(map[27][28]);
		map[27][30].setFail(map[27][29]);
		
		//------Boat------------------------
		map[9][4].boatOn();
		map[6][22].boatOn();
		map[9][35].boatOn();
		map[23][4].boatOn();
		map[30][44].boatOn();
		map[36][37].boatOn();
		//------Canon-----------------------
		map[15][17].canonOn();
		//------Raft------------------------
		map[9][30].raftOn();
		
		return map;
	}
	
	/**
	 * Returns Null if no characters is in view or an array of characters if there is someone in view
	 * @param Character
	 * @return Null or Character[]
	 */
	
	public Character[] lookingForAShoot(Character character){
		Character[] charArray = new Character[6];
		int charInt = 0;
		int row = character.getRow() - 1;
		int col = character.getCol() - 1;
		//Up to the left
		while(map[row][col].getSeeThrough()){
			if (map[row][col].getCharacter() != null){
				charArray[charInt] = map[row][col].getCharacter();
				charInt++;
			}
			row--;
			col--;
		}
		//down to the right
		row = character.getRow() + 1;
		col = character.getCol() + 1;
		while(map[row][col].getSeeThrough()){
			if (map[row][col].getCharacter() != null){
				charArray[charInt] = map[row][col].getCharacter();
				charInt++;
			}
			row++;
			col++;
		}
		//Up
		row = character.getRow() + 1;
		col = character.getCol();
		while(map[row][col].getSeeThrough()){
			if (map[row][col].getCharacter() != null){
				charArray[charInt] = map[row][col].getCharacter();
				charInt++;
			}
			row++;
		}
		//Down
		row = character.getRow() - 1;
		col = character.getCol();
		while(map[row][col].getSeeThrough()){
			if (map[row][col].getCharacter() != null){
				charArray[charInt] = map[row][col].getCharacter();
				charInt++;
			}
			row--;
		}
		//Up to the right
		row = character.getRow() - 1;
		col = character.getCol() + 1;
		while(map[row][col].getSeeThrough()){
			if (map[row][col].getCharacter() != null){
				charArray[charInt] = map[row][col].getCharacter();
				charInt++;
			}
			row--;
			col++;
		}
		//Down to the left
		row = character.getRow() + 1;
		col = character.getCol() - 1;
		while(map[row][col].getSeeThrough()){
			if (map[row][col].getCharacter() != null){
				charArray[charInt] = map[row][col].getCharacter();
				charInt++;
			}
			row++;
			col--;
		}
		//Left
		row = character.getRow();
		col = character.getCol() - 1;
		while(map[row][col].getSeeThrough()){
			if (map[row][col].getCharacter() != null){
				charArray[charInt] = map[row][col].getCharacter();
				charInt++;
			}
			col--;
		}
		//Right
		row = character.getRow();
		col = character.getCol() + 1;
		while(map[row][col].getSeeThrough()){
			if (map[row][col].getCharacter() != null){
				charArray[charInt] = map[row][col].getCharacter();
				charInt++;
			}
			col++;
		}
		if(charInt == 0){
			return null;
		}else{
			return charArray;
		}
	}
	
	

//	public void theTile(ExtendedJLabel theTile){
//		System.out.println("Från viewern till klienten");
//		try{
//			
//			tilePos[0] = theTile.getCol();
//			tilePos[1] = theTile.getRow();
//			ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
//			output.writeObject(tilePos);
//			output.flush();
//		}catch (IOException e) {
//			e.printStackTrace();
//		}
//	}		
}

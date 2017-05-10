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
 * Version 1.5
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
	private boolean shotTakenThisTurn;
	private Connection connection;
	
	private int steps;
	private int oldColThis;
	private int oldRowThis;

	public GameClient(){
		System.out.println("Klient Startad");
		map = createMap();
		connection = new Connection();
	}
	public void sendUsername(String username) {
		this.username = username;
	}
	public void connect(String serverIp, int port){
		new Connection(serverIp,port).start();
	}
	
	public void addListeners(ViewerListener listener) {
		listeners.add(listener);
	}
	
	public void startGame(){
		try {
			System.out.println("Client: Startar matchen/väljer vems tur det är");
			output.writeObject("STARTGAME");
			output.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void endTurn(){
		try {
			System.out.println("Client: End turn");
			output.writeObject("ENDTURN");
			output.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method that returns a random number from 1-6
	 * 
	 * @return int
	 */
	public int throwDice() {
		Random rand = new Random();
		steps = rand.nextInt(6) + 1;
		for (ViewerListener listener : listeners) {
			listener.updateInfoRuta("Antal steg: " + String.valueOf(steps));
			listener.enableButtons("disable shoot");
		}
		System.out.println("Client: Tärning: " + steps);
		return steps;
	}

	/**
	 * Method that uses the throwDice-method to see if the shot hits another
	 * player
	 * 
	 * @return boolean
	 */
	public boolean shootDice() {
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
			System.out.println("Client: Uppkoppling avslutad");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Method to enable buttons in ClientFrame
	 * @param enableButtons boolean
	 */
	public void enableButtons(boolean enableButtons){
		shotTakenThisTurn = false;
		for(ViewerListener listener: listeners){
			listener.updateViewer();
		}

		if (!lookingForAShoot(characterMap.get(username)).isEmpty()){
			for(ViewerListener listener: listeners){
				listener.enableButtons("shoot");
			}
		}
	}
	
	public void shoot(){
		
		shotTakenThisTurn = true;
	}
	
	public void moveCharacter( String username, String direction){
		connection.moveChar( characterMap.get(username), direction);
		for (ViewerListener listener : listeners) {
			listener.updateInfoRuta("Antal steg: " + String.valueOf(steps));
		}
	}
	
	/**
	* Inner class that handles the connection between client and server
	*/
	
	private class Connection extends Thread{
		private String ipAddress = "";
		private int port = 0;
		
		
		public Connection(String ipAddress, int port){
			this.ipAddress = ipAddress;
			this.port = port;
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
		
		
		public void moveChar(client.Character character, String direction) {

			oldColThis = character.getCol();
			oldRowThis = character.getRow();
			boolean repaintCharacter = true;

			switch (direction) {
			case "Right":
				if (checkMove(character, "RIGHT")){
					character.setPos(oldRowThis, oldColThis + 1);
					steps--;
				}else{
					repaintCharacter = false;
					System.out.println("Client: Väg blockerad");
				}
				break;
			case "Left":
				if (checkMove(character, "LEFT")){
					character.setPos(oldRowThis, oldColThis - 1);
					steps--;
				}else{
					repaintCharacter = false;
					System.out.println("Client: Väg blockerad");
				}
				break;
			case "Up":
				if (checkMove(character, "UP")){
					character.setPos(oldRowThis - 1, oldColThis);
					steps--;
				}else{
					repaintCharacter = false;
					System.out.println("Client: Väg blockerad");
				}
				break;
			case "Down":
				if (checkMove(character, "DOWN")){
					character.setPos(oldRowThis + 1, oldColThis);
					steps--;
				}else{
					repaintCharacter = false;
					System.out.println("Client: Väg blockerad");
				}
				break;
			}
			System.out.println("Client: Steg kvar: " + steps);
			if (repaintCharacter) {
				try {
					output.writeObject(character);
					output.flush();
					System.out.println("Client: karaktärobjekt skickat");

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (steps == 0) {
				System.out.println("Client: disable buttons"); 
				for (ViewerListener listener : listeners) {
					listener.enableButtons("disable move");
				}
				if (!lookingForAShoot(characterMap.get(username)).isEmpty()){
					for(ViewerListener listener: listeners){
						listener.enableButtons("shoot");
					}
				}
			}
		}

		public void updateCharacter(client.Character character){
			String characterName = character.getName();
			System.out.println("Client: " +characterName + " hanteras");
			int oldRow = 2;
			int oldCol = 2;
			if(characterMap.containsKey(characterName) && !characterName.equals(username)){
				oldRow = characterMap.get(characterName).getRow();
				oldCol = characterMap.get(characterName).getCol();
			}else if(characterMap.containsKey(characterName) && characterName.equals(username)){
				oldRow = oldRowThis;
				oldCol = oldColThis;
				
			}
			
			characterMap.put(characterName, character);
			for(ViewerListener listener: listeners){

				listener.paintCharacter(character.getRow(), character.getCol(), oldRow, oldCol);
				
				System.out.println("Client: flytta gubbe i viewer");
			}
		}
			
	}
	
	/**
	 * Checks the tiles to the specified side of your character for available path 
	 * @param 	String dir
	 * @return	boolean
	 */

	public boolean checkMove(client.Character me, String dir) {
		boolean ret = false;
		switch (dir) {
		case "LEFT":
			if(map[me.getRow()][me.getCol() - 1].getAccessible()){
				if(map[me.getRow()][me.getCol() - 1].containsCharacter()){
					if (map[me.getRow()][me.getCol() - 1].getCharacter().sleeping() < 1){
						ret = true;
					}else{
						ret = false;
					}
				}else{
					ret = true;
				}
			}
			break;
		case "RIGHT":
			if(map[me.getRow()][me.getCol() + 1].getAccessible()){
				if(map[me.getRow()][me.getCol() + 1].containsCharacter()){
					if (map[me.getRow()][me.getCol() + 1].getCharacter().sleeping() < 1){
						ret = true;
					}else{
						ret = false;
					}
				}else{
					ret = true;
				}
			}
			break;
		case "UP":
			if(map[me.getRow() - 1][me.getCol()].getAccessible()){
				if(map[me.getRow() - 1][me.getCol()].containsCharacter()){
					if (map[me.getRow() - 1][me.getCol()].getCharacter().sleeping() < 1){
						ret = true;
					}else{
						ret = false;
					}
				}else{
					ret = true;
				}
			}
			break;
		case "DOWN":
			if(map[me.getRow() + 1][me.getCol()].getAccessible()){
				if(map[me.getRow() + 1][me.getCol() - 1].containsCharacter()){
					if (map[me.getRow() + 1][me.getCol() - 1].getCharacter().sleeping() < 1){
						ret = true;
					}else{
						ret = false;
					}
				}else{
					ret = true;
				}
			}
			break;

		default:
			ret = false;
			break;
		}
		return ret;
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
	
	public ArrayList<client.Character> lookingForAShoot(client.Character character){
		ArrayList<client.Character> charArray = new ArrayList<>();
		int charInt = 0;
		int row = character.getRow() - 1;
		int col = character.getCol() - 1;
		//Up to the left
		while(map[row][col].getSeeThrough() && row != 1 && col != 1 && row != 40 && col != 46){
			if (map[row][col].containsCharacter()){
				charArray.add(map[row][col].getCharacter());
				charInt++;
			}
			row--;
			col--;
		}
		//down to the right
		row = character.getRow() + 1;
		col = character.getCol() + 1;
		while(map[row][col].getSeeThrough() && row != 1 && col != 1 && row != 40 && col != 46){
			if (map[row][col].containsCharacter()){
				charArray.add(map[row][col].getCharacter());
				charInt++;
			}
			row++;
			col++;
		}
		//Up
		row = character.getRow() + 1;
		col = character.getCol();
		while (map[row][col].getSeeThrough() && row != 1 && col != 1 && row != 40 && col != 46) {
			if (map[row][col].containsCharacter()) {
				charArray.add(map[row][col].getCharacter());
				charInt++;
			}
			row--;
		}
		// Down
		row = character.getRow() - 1;
		col = character.getCol();
		while (map[row][col].getSeeThrough() && row != 1 && col != 1 && row != 40 && col != 46) {
			if (map[row][col].containsCharacter()) {
				charArray.add(map[row][col].getCharacter());
				charInt++;
			}
			row++;
		}
		// Up to the right
		row = character.getRow() - 1;
		col = character.getCol() + 1;
		while(map[row][col].getSeeThrough() && row != 1 && col != 1 && row != 40 && col != 46){
			if (map[row][col].containsCharacter()){
				charArray.add(map[row][col].getCharacter());
				charInt++;
			}
			row--;
			col++;
		}
		//Down to the left
		row = character.getRow() + 1;
		col = character.getCol() - 1;
		while(map[row][col].getSeeThrough() && row != 1 && col != 1 && row != 40 && col != 46){
			if (map[row][col].containsCharacter()){
				charArray.add(map[row][col].getCharacter());
				charInt++;
			}
			row++;
			col--;
		}
		//Left
		row = character.getRow();
		col = character.getCol() - 1;
		while(map[row][col].getSeeThrough() && row != 1 && col != 1 && row != 40 && col != 46){
			if (map[row][col].containsCharacter()){
				charArray.add(map[row][col].getCharacter());
				charInt++;
			}
			col--;
		}
		//Right
		row = character.getRow();
		col = character.getCol() + 1;
		while(map[row][col].getSeeThrough() && row != 1 && col != 1 && row != 40 && col != 46){
			if (map[row][col].containsCharacter()){
				charArray.add(map[row][col].getCharacter());
				charInt++;
			}
			col++;
		}
		if(charInt == 0){
			return charArray;
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

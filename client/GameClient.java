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
	private Socket socket;
	private ArrayList<ViewerListener> listeners = new ArrayList<ViewerListener>();
	private int[] tilePos = new int[2];
	private Tile[][] map;
	private HashMap<String, client.Character> characterMap = new HashMap<String, client.Character>(); 
	private String character = "";
	
	
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
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setCharacter(String character){
		connection.setCharacter(character);
		this.character = character;
	}
	public String getCharacter(){
		return this.character;
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
		Random rand = new Random();
		int roll = rand.nextInt(6)+1;
		
		System.out.println("Client: shootDice: " + roll);
		
		if(roll == 2 || roll == 6){
			return true;
		}
		return false;
	}
	
	public boolean jumpDice() {
		Random rand = new Random();
		int roll = rand.nextInt(6)+1;
		if(roll == 1|| roll == 2 || roll == 3 || roll == 4){
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
		
		if (map[characterMap.get(username).getRow()][characterMap.get(username).getCol()].getName().equals("Special")){
			for(ViewerListener listener: listeners){
				listener.enableButtons("jump");
			}
		}

		if (!lookingForAShot(characterMap.get(username)).isEmpty()){
			for(ViewerListener listener: listeners){
				listener.enableButtons("shoot");
			}
		}
	}
	
	public void jump(){
		map[characterMap.get(username).getRow()][characterMap.get(username).getCol()].removeCharacter();
		if (jumpDice()){
			characterMap.get(username).setPos(characterMap.get(username).getRow() + map[characterMap.get(username).getRow()][characterMap.get(username).getCol()].successRow(), 
					characterMap.get(username).getCol() + map[characterMap.get(username).getRow()][characterMap.get(username).getCol()].successCol());
			
		}else{
			characterMap.get(username).setPos(characterMap.get(username).getRow() + map[characterMap.get(username).getRow()][characterMap.get(username).getCol()].failRow(), 
					characterMap.get(username).getCol() + map[characterMap.get(username).getRow()][characterMap.get(username).getCol()].failCol());
		}
		for(ViewerListener listener: listeners){
			listener.enableButtons("disable jump");
			listener.enableButtons("disable move");
		}
		connection.flushCharacter(characterMap.get(username));
	}
	
	public void shoot() {
		Character target;

		for (ViewerListener listener : listeners) {
			listener.enableButtons("disable shoot");
		}
		shotTakenThisTurn = true;
		
		
		
		if (!lookingForAShot(characterMap.get(username)).isEmpty()) {
			System.out.println("Shot-attempt taken by: " + username);
			for (int i = 0; i < lookingForAShot(characterMap.get(username)).size(); i++) {
				for (ViewerListener listener : listeners) {
					target = lookingForAShot(characterMap.get(username)).get(i);
					
					listener.setAvailableTarget(target.getCharacterName(), target.getName());
				}
			}

			for (ViewerListener listener : listeners) {
				listener.getTarget();
			}
		}
	}
	public void shoot(String character){
		String targetName = "";
		boolean dice = shootDice();
		System.out.println("Shoot dice: " + dice + " for: " + username);

		if (dice) {
			targetName = character;
			System.out.println("Shot taken by: " + username + " at: " + targetName);
			
			map[characterMap.get(targetName).getRow()][characterMap.get(targetName).getCol()].moveCharacterToSleeping();
			connection.shootTarget(targetName);
		}

	}
	
	public void moveCharacter( String username, String direction){
		if(steps > 0){
			connection.moveChar( characterMap.get(username), direction);
			for (ViewerListener listener : listeners) {
				listener.updateInfoRuta("Antal steg: " + String.valueOf(steps));
			}
		}
	}
	
	/**
	* Inner class that handles the connection between client and server
	*/
	
	private class Connection extends Thread{
		private String ipAddress = "";
		private int port = 0;
		boolean svullo;
		boolean tjoPang;
		boolean theRat;
		boolean markisen;
		boolean hannibal;
		boolean hook;
		
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
						int row = (int)object;
						int col =  (int)input.readObject();
						map[row][col].removeSleepingChatacter();
						
						client.Character character = (client.Character)input.readObject();
						if(character.sleeping() > 0){
							for(ViewerListener listener: listeners){
								listener.setIconSleep(character.getCharacterName(), false);
							}
						}
						if(character.sleeping() == 0){
							for(ViewerListener listener: listeners){
								listener.setIconSleep(character.getCharacterName(), true);
							}
						}
						System.out.println("CLIENT: mottaget Character-objekts sleeping: " + character.sleeping());
						updateCharacter(character);
						
					}
	
					else if (object instanceof client.Character){
						client.Character character = (client.Character) object;
						if(character.sleeping() > 0){
							for(ViewerListener listener: listeners){
								listener.setIconSleep(character.getCharacterName(), false);
							}
						}
						if(character.sleeping() == 0){
							for(ViewerListener listener: listeners){
								listener.setIconSleep(character.getCharacterName(), true);
							}
						}
						character.passATurn();
						System.out.println("CLIENT: mottaget Character-objekts sleeping: " + character.sleeping());
						updateCharacter(character);
					}
					else if (object instanceof String){
						
						if(object.equals("Enable buttons")){
							boolean enableButtons = input.readBoolean();
							enableButtons(enableButtons);
						}
						
						else if(object.equals("Choose character")){
							for(ViewerListener listener: listeners){
								svullo = input.readBoolean();
								tjoPang = input.readBoolean();
								theRat = input.readBoolean();
								markisen = input.readBoolean();
								hannibal = input.readBoolean();
								hook = input.readBoolean();
								
								listener.updateChooseCharFrame(svullo, tjoPang, theRat, markisen, hannibal, hook);
								listener.chooseCharFrame();
							}
						}else if(object.equals("updateUserInfo")){
							for(ViewerListener listener: listeners){

								svullo = input.readBoolean();
								tjoPang = input.readBoolean();
								theRat = input.readBoolean();
								markisen = input.readBoolean();
								hannibal = input.readBoolean();
								hook = input.readBoolean();
								
								
								listener.updateChooseCharFrame(svullo, tjoPang, theRat, markisen, hannibal, hook);
								
							}
						}else{
							for(ViewerListener listener: listeners){
								System.out.println("Client: mottagit ny user/users uppdaterar \"ConnectedUserList\"");
								listener.removeConnectedUsers();
							}
							while(object != null){
								for(ViewerListener listener: listeners){
									listener.addConnectedUser((String) object);
								}
								object = input.readObject();
							}
						}
					}

				}catch (IOException | ClassNotFoundException e){
					disconnect();
					e.printStackTrace();
					Thread.currentThread().stop();					
				}
			}
		}
		
		public void setCharacter(String character){
			try {
				output.writeObject("set character");
				output.writeObject(character);
				output.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} 
		
		public void shootTarget(String target){
			
			characterMap.get(target).shot();
			try {
				output.writeObject(characterMap.get(target));
				output.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public void flushCharacter(client.Character character){
			try{
				output.writeObject(character);
				output.flush();
			} catch (IOException e) {
				e.printStackTrace();
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
			
			map[oldRowThis][oldColThis].removeCharacter();
			
			if (steps == 0) {
				System.out.println("Client: disable buttons"); 
				for (ViewerListener listener : listeners) {
					listener.enableButtons("disable move");
				}
				System.out.println("can take a shot: " + !lookingForAShot(characterMap.get(username)).isEmpty() + " & " +!shotTakenThisTurn );
				if (!lookingForAShot(characterMap.get(username)).isEmpty() && !shotTakenThisTurn){
					for(ViewerListener listener: listeners){
						listener.enableButtons("shoot");
					}
				}
			}
		}

		public void updateCharacter(client.Character character){
			String characterName = character.getName();
			System.out.println("Client: " +characterName + " hanteras");
			int oldRow = 1;
			int oldCol = 1;
			int tempMapPieces = 1;
			boolean mapPieceChange = false;
			if(characterMap.containsKey(characterName) && !characterName.equals(username)){
				if (characterMap.get(characterName).getRow() == character.getRow() && characterMap.get(characterName).getCol() == character.getCol()){ //Kollar om caratären har rört sig från senast
					System.out.println("Client: \"" + characterName  + "\" uppdadters men har inte rört sig");
				}else if(map[character.getRow()][character.getCol()].containsCharacter()){
					System.out.println("Client: Tilen \"" + characterName  + "\" lämnar innehåller en skadad karaktär");
				}else{
					oldRow = characterMap.get(characterName).getRow();
					oldCol = characterMap.get(characterName).getCol();
				}
			}else if(characterMap.containsKey(characterName) && characterName.equals(username)){
				oldRow = oldRowThis;
				oldCol = oldColThis;
				
			}
			
			map[oldRow][oldCol].removeCharacter();
			System.out.println("Client: " + oldRow + ", " + oldCol + " Character borttagen");
			if (character.sleeping() == 0){
				if(map[character.getRow()][character.getCol()].containsSleepingCharacter()){
					tempMapPieces = map[character.getRow()][character.getCol()].getSleepingCharacter().stealPieces();
					System.out.println("Client: Character \"" + character + "\" snodde " + character.getPieces() + " stycken kart-delar från Character \"" + map[character.getRow()][character.getCol()].getSleepingCharacter() +"\"!");
					mapPieceChange = true;
				}
				map[character.getRow()][character.getCol()].setCharacter(character);
				System.out.println("Client: " + character.getRow() + ", " + character.getCol() + " Character tillagd");
			}
			
			characterMap.put(characterName, character);
			if(mapPieceChange){
				characterMap.get(characterName).givePieces(tempMapPieces);
			}
			for(ViewerListener listener: listeners){

				listener.paintCharacter(character.getRow(), character.getCol(), oldRow, oldCol);
				listener.moveIcon(character.getCharacterName(), character.getRow(), character.getCol());
				
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
						ret = false;
					}else{
						ret = true;
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
						ret = false;
					}else{
						ret = true;
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
						ret = false;
					}else{
						ret = true;
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
						ret = false;
					}else{
						ret = true;
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
	
//	public String getTile(int row, int col){
//		if(map[row][col].getBoat()){
//			return "ORANGE";
//		}else if (map[row][col].getRaft() || map[row][col].getCanon()){
//			return "BLACK";
//		} else {
//			return map[row][col].getName();
//		}
//	}

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
		map[2][10].setSuccess(0, 3);
		map[2][10].setFail(4, 1);
		
		map[2][12].setSuccess(0, 2);
		map[2][12].setFail(4, 1);
		
		map[2][13].setSuccess(0, 2);
		map[2][13].setFail(4, 1);
		
		map[2][15].setSuccess(0, -3);
		map[2][15].setFail(4, -1);
		
		map[4][25].setSuccess(2, 0);
		map[4][25].setFail(1, 0);
		
		map[6][25].setSuccess(-2, 0);
		map[6][25].setFail(-1, 0);
		
		map[6][27].setSuccess(0, 2);
		map[6][27].setFail(-1, 1);
		
		map[6][29].setSuccess(0, -2);
		map[6][29].setFail(-1, -1);
		
		map[12][2].setSuccess(3, 0);
		map[12][2].setFail(1, 0);
		
		map[14][2].setSuccess(-2, 0);
		map[14][2].setFail(-1, 0);
		
		map[12][6].setSuccess(2, 0);
		map[12][6].setFail(1, 0);
		
		map[14][6].setSuccess(-2, 0);
		map[14][6].setFail(-1, 0);
		
		map[15][2].setSuccess(2, 0);
		map[15][2].setFail(1, 0);
		
		map[17][2].setSuccess(-3, 0);
		map[17][2].setFail(-1, 0);
		
		map[15][11].setSuccess(2, 0);
		map[15][11].setFail(1, 0);
		
		map[17][11].setSuccess(-2, 0);
		map[17][11].setFail(-1, 0);
		
		map[16][19].setSuccess(0, 2);
		map[16][19].setFail(0, 1);
		
		map[16][21].setSuccess(0, -2);
		map[16][21].setFail(0, -1);
		
		map[16][33].setSuccess(0, 3);
		map[16][33].setFail(0, 1);
		
		map[16][35].setSuccess(0, -2);
		map[16][35].setFail(0, -1);
		
		map[16][36].setSuccess(0, 2);
		map[16][36].setFail(0, 1);
		
		map[16][38].setSuccess(0, -3);
		map[16][38].setFail(0, -1);
		
		map[18][9].setSuccess(0, 2);
		map[18][9].setFail(0, 1);
		
		map[18][11].setSuccess(0, -2);
		map[18][11].setFail(0, -1);
		
		map[19][19].setSuccess(2, 0);
		map[19][19].setFail(1, 0);
		
		map[21][19].setSuccess(-2, 0);
		map[21][19].setFail(-1, 0);
		
		map[18][25].setSuccess(2, 0);
		map[18][25].setFail(1, 0);
		
		map[20][25].setSuccess(-2, 0);
		map[20][25].setFail(-1, 0);
		
		map[27][28].setSuccess(0, 2);
		map[27][28].setFail(5, 1);
		
		map[27][30].setSuccess(0, -2);
		map[27][30].setFail(5, -1);
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
	 * @return ArrayList<client.Character>
	 */
	
	public ArrayList<client.Character> lookingForAShot(client.Character character){
		ArrayList<client.Character> charArray = new ArrayList<>();
		int charInt = 0;
		int row = character.getRow() - 1;
		int col = character.getCol() - 1;
		//Up to the left
		while(map[row][col].getSeeThrough() && row != 0 && col != 0 && row != 40 && col != 46){
			if (map[row][col].containsCharacter()){
				System.out.println("Client: character " + map[row][col].getCharacter().getName() + " hittad på position " +row + ", " + col);
				charArray.add(map[row][col].getCharacter());
				charInt++;
			}
			row--;
			col--;
		}
		//down to the right
		row = character.getRow() + 1;
		col = character.getCol() + 1;
		while(map[row][col].getSeeThrough() && row != 0 && col != 0 && row != 40 && col != 46){
			if (map[row][col].containsCharacter()){
				System.out.println("Client: character " + map[row][col].getCharacter().getName() + " hittad på position " +row + ", " + col);
				charArray.add(map[row][col].getCharacter());
				charInt++;
			}
			row++;
			col++;
		}
		//Up
		row = character.getRow() - 1;
		col = character.getCol();
		while (map[row][col].getSeeThrough() && row != 0 && col != 0 && row != 40 && col != 46) {
			if (map[row][col].containsCharacter()) {
				System.out.println("Client: character " + map[row][col].getCharacter().getName() + " hittad på position " +row + ", " + col);
				charArray.add(map[row][col].getCharacter());
				charInt++;
			}
			row--;
		}
		// Down
		row = character.getRow() + 1;
		col = character.getCol();
		while (map[row][col].getSeeThrough() && row != 0 && col != 0 && row != 40 && col != 46) {
			if (map[row][col].containsCharacter()) {
				System.out.println("Client: character " + map[row][col].getCharacter().getName() + " hittad på position " +row + ", " + col);
				charArray.add(map[row][col].getCharacter());
				charInt++;
			}
			row++;
		}
		// Up to the right
		row = character.getRow() - 1;
		col = character.getCol() + 1;
		while(map[row][col].getSeeThrough() && row != 0 && col != 0 && row != 40 && col != 46){
			if (map[row][col].containsCharacter()){
				System.out.println("Client: character " + map[row][col].getCharacter().getName() + " hittad på position " +row + ", " + col);
				charArray.add(map[row][col].getCharacter());
				charInt++;
			}
			row--;
			col++;
		}
		//Down to the left
		row = character.getRow() + 1;
		col = character.getCol() - 1;
		while(map[row][col].getSeeThrough() && row != 0 && col != 0 && row != 40 && col != 46){
			if (map[row][col].containsCharacter()){
				System.out.println("Client: character " + map[row][col].getCharacter().getName() + " hittad på position " +row + ", " + col);
				charArray.add(map[row][col].getCharacter());
				charInt++;
			}
			row++;
			col--;
		}
		//Left
		row = character.getRow();
		col = character.getCol() - 1;
		while(map[row][col].getSeeThrough() && row != 0 && col != 0 && row != 40 && col != 46){
			if (map[row][col].containsCharacter()){
				System.out.println("Client: character " + map[row][col].getCharacter().getName() + " hittad på position " +row + ", " + col);
				charArray.add(map[row][col].getCharacter());
				charInt++;
			}
			col--;
		}
		//Right
		row = character.getRow();
		col = character.getCol() + 1;
		while(map[row][col].getSeeThrough() && row != 0 && col != 0 && row != 40 && col != 46){
			if (map[row][col].containsCharacter()){
				System.out.println("Client: character " + map[row][col].getCharacter().getName() + " hittad på position " +row + ", " + col);
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
	
			
}
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
	private int oldColThis = 2;
	private int oldRowThis = 2;
	private int tempMapPieces = 1;
	
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
			listener.updateInfoRutaSteps("Antal steg: " + String.valueOf(steps));
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
	 * 
	 * @param enableButtons
	 *            boolean
	 */
	public void enableButtons(boolean enableButtons) {
		shotTakenThisTurn = false;
		if (!map[characterMap.get(username).getRow()][characterMap.get(username).getCol()].getName().equals("Water")) {
			for (ViewerListener listener : listeners) {
				listener.updateViewer();
			}

			if (map[characterMap.get(username).getRow()][characterMap.get(username).getCol()].getName().equals("Special")) {
				for (ViewerListener listener : listeners) {
					listener.enableButtons("jump");
				}
			}

			if (!lookingForAShot(characterMap.get(username)).isEmpty()) {
				for (ViewerListener listener : listeners) {
					listener.enableButtons("shoot");
				}
			}
			
			if(map[characterMap.get(username).getRow()][characterMap.get(username).getCol()].getTreasure()){
				map[characterMap.get(username).getRow()][characterMap.get(username).getCol()].treasureOff();
				characterMap.get(username).giveTreasure();
				System.out.println("Client: " + username + " har tagit upp skatten");
			}
		} else {
			inWater();
		}
	}

	public void inWater() {
		Character me = characterMap.get(username);
		boolean canJump = false;
		Random rand = new Random();
		int dice = rand.nextInt(6)+1;
		
		System.out.println("Client: removing " + me.getRow() + ", " + me.getCol());
		map[me.getRow()][me.getCol()].removeCharacter();
		
		for (ViewerListener listener : listeners) {
			listener.setWaterIcon(me.getCharacterName());
		}
		
		if (map[me.getRow() - 1][me.getCol()].getAccessible()) {
			canJump = true;
		}
		if (map[me.getRow() + 1][me.getCol()].getAccessible()) {
			canJump = true;
		}
		if (map[me.getRow()][me.getCol() - 1].getAccessible()) {
			canJump = true;
		}
		if (map[me.getRow()][me.getCol() + 1].getAccessible()) {
			canJump = true;
		}
		
		if(canJump){
			System.out.println("Client: " + username + " befinner sig på " + me.getRow() + ", " + me.getCol());
			if(jumpDice()){
				
				System.out.println("Client: " + username + " lyckades ta sig upp ur floden");
				for(ViewerListener listener: listeners){
					listener.enableButtons("update");
				}
			}else{
				System.out.println("Client: " + username + " lyckades inte ta sig upp ur floden och flyter med floden " + dice + " rutor" );
				for(int i = 0; i < dice; i++){
					me.setPos(me.getRow() + map[me.getRow()][me.getCol()].nextRow(),me.getCol() + map[me.getRow()][me.getCol()].nextCol());
				}
				connection.flushCharacter(me);
				for(ViewerListener listener: listeners){
					listener.enableButtons("disable move");
				}
			}
		}else{
			System.out.println("Client: " + username + " kan inte ta sig upp ur floden och flyter med floden " + dice + " rutor");
			for(int i = 0; i < dice; i++){
				me.setPos(me.getRow() + map[me.getRow()][me.getCol()].nextRow(),me.getCol() + map[me.getRow()][me.getCol()].nextCol());
			}
			connection.flushCharacter(me);
			for(ViewerListener listener: listeners){
				listener.enableButtons("disable move");
			}
		}
	}

	public void jump() {
		map[characterMap.get(username).getRow()][characterMap.get(username).getCol()].removeCharacter();
		if (jumpDice()) {
			characterMap.get(username).setPos(characterMap.get(username).getRow()
					+ map[characterMap.get(username).getRow()][characterMap.get(username).getCol()].successRow(),
					characterMap.get(username).getCol()
							+ map[characterMap.get(username).getRow()][characterMap.get(username).getCol()]
									.successCol());

		} else {
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
				listener.updateInfoRutaSteps("Antal steg: " + String.valueOf(steps));
				listener.updateInfoRutaMap(String.valueOf(characterMap.get(username).getPieces()));
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
		private int treasurePos;
		
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
						map[row][col].removeSleepingCharacter();
						
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
						character.passATurn();
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
					else if (object instanceof String){
						
						if(object.equals("Enable buttons")){
							boolean enableButtons = input.readBoolean();
							enableButtons(enableButtons);
						}else if(object.equals("treasure position")){
							this.treasurePos = (int)input.readObject();
							switch(treasurePos){
							case 1:
								map[2][12].treasureOn();
								for(ViewerListener listener: listeners){
									listener.moveIcon("Treasure", 2, 12, input.readBoolean());
								}
								break;
							case 2:
								map[14][9].treasureOn();
								for(ViewerListener listener: listeners){
									listener.moveIcon("Treasure", 14, 9, input.readBoolean());
								}
								break;
							case 3:
								map[31][9].treasureOn();
								for(ViewerListener listener: listeners){
									listener.moveIcon("Treasure", 31, 9, input.readBoolean());
								}
								break;
							case 4:
								map[31][20].treasureOn();
								for(ViewerListener listener: listeners){
									listener.moveIcon("Treasure", 31, 20, input.readBoolean());
								}
								break;
							case 5:
								map[16][22].treasureOn();
								for(ViewerListener listener: listeners){
									listener.moveIcon("Treasure", 16, 22, input.readBoolean());
								}
								break;
							case 6:
								map[2][27].treasureOn();
								for(ViewerListener listener: listeners){
									listener.moveIcon("Treasure", 2, 27, input.readBoolean());
								}
								break;
							case 7:
								map[7][31].treasureOn();
								for(ViewerListener listener: listeners){
									listener.moveIcon("Treasure", 7, 31, input.readBoolean());
								}
								break;
							case 8:
								map[15][31].treasureOn();
								for(ViewerListener listener: listeners){
									listener.moveIcon("Treasure", 15, 31, input.readBoolean());
								}
								break;
							case 9:
								map[29][33].treasureOn();
								for(ViewerListener listener: listeners){
									listener.moveIcon("Treasure", 29, 33, input.readBoolean());
								}
								break;	
							}
							
							
						}else if(object.equals("Choose character")){
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
						}else if(object.equals("time out")){
							for(ViewerListener listener: listeners){
								listener.enableButtons("time out");
							}
						}else if(object.equals("steal pieces")){
							characterMap.get(input.readObject()).setPieces(0);
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
					for(ViewerListener listener: listeners){
						listener.enableButtons("disable all");
					}
				}else{
					repaintCharacter = false;
					System.out.println("Client: Väg blockerad");
				}
				break;
			case "Left":
				if (checkMove(character, "LEFT")){
					character.setPos(oldRowThis, oldColThis - 1);
					steps--;
					for(ViewerListener listener: listeners){
						listener.enableButtons("disable all");
					}
				}else{
					repaintCharacter = false;
					System.out.println("Client: Väg blockerad");
				}
				break;
			case "Up":
				if (checkMove(character, "UP")){
					character.setPos(oldRowThis - 1, oldColThis);
					steps--;
					for(ViewerListener listener: listeners){
						listener.enableButtons("disable all");
					}
				}else{
					repaintCharacter = false;
					System.out.println("Client: Väg blockerad");
				}
				break;
			case "Down":
				if (checkMove(character, "DOWN")){
					character.setPos(oldRowThis + 1, oldColThis);
					steps--;
					for(ViewerListener listener: listeners){
						listener.enableButtons("disable all");
					}
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
			if(steps == 0){
				for(ViewerListener listener: listeners){
					listener.enableButtons("disable move");
				}
			}
		}
		public void showTreasure(boolean status){
			try {
				if(status){
					System.out.println("Visar skatten");
					output.writeObject("show treasure");
					output.flush();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void updateCharacter(client.Character character){
			String characterName = character.getName();
			System.out.println("Client: " +characterName + " hanteras");
			int oldRow = 2;
			int oldCol = 2;
			boolean mapPieceChange = false;
			if(characterMap.containsKey(characterName) && !characterName.equals(username)){
				if (characterMap.get(characterName).getRow() == character.getRow() && characterMap.get(characterName).getCol() == character.getCol()){ //Kollar om caratären har rört sig från senast
					System.out.println("Client: \"" + characterName  + "\" uppdaterats men har inte rört sig");
				}else if(map[character.getRow()][character.getCol()].containsCharacter()){
					System.out.println("Client: Tilen som \"" + characterName  + "\" går till innehåller en skadad karaktär");
				}else{
					oldRow = characterMap.get(characterName).getRow();
					oldCol = characterMap.get(characterName).getCol();
				}
			}else if(characterMap.containsKey(characterName) && characterName.equals(username)){
				for(ViewerListener listener: listeners){
					if(steps > 0){
						listener.enableButtons("move");
					}
				}
				oldRow = oldRowThis;
				oldCol = oldColThis;
				
			}
			
			map[oldRow][oldCol].removeCharacter();
			System.out.println("Client: " + oldRow + ", " + oldCol + " Character borttagen");
			if (character.sleeping() == 0){
				if(map[character.getRow()][character.getCol()].containsSleepingCharacter()){
					tempMapPieces = map[character.getRow()][character.getCol()].getSleepingCharacter().stealPieces();
					map[character.getRow()][character.getCol()].getSleepingCharacter().takeTreasure();
					character.giveTreasure();
					System.out.println("Client: " + characterName + " har tagit upp skatten");
					try {
						output.writeObject("pieces stolen");
						output.writeObject(map[character.getRow()][character.getCol()].getSleepingCharacter().getName());
						output.flush();
					} catch (IOException e) {
						e.printStackTrace();
					}
					System.out.println("Client: Character \"" + character + "\" snodde " + character.getPieces() + " stycken kart-delar från Character \"" + map[character.getRow()][character.getCol()].getSleepingCharacter() +"\"!");
					System.out.println("Client: " +map[character.getRow()][character.getCol()].getSleepingCharacter()+ " har " +map[character.getRow()][character.getCol()].getSleepingCharacter().getPieces()+ " kartdelar kvar");
					System.out.println("                     " + characterMap.get(username).getCharacterName() + " har " + tempMapPieces + "!");
					mapPieceChange = true;
				}
				map[character.getRow()][character.getCol()].setCharacter(character);
				System.out.println("Client: " + character.getRow() + ", " + character.getCol() + " Character tillagd");
			}
			
			characterMap.put(characterName, character);
			if(mapPieceChange){
				characterMap.get(characterName).givePieces(tempMapPieces);
				if(characterMap.get(characterName).getPieces() == characterMap.size()){
					showTreasure(true);
				}
			}
			
//			for (ViewerListener listener : listeners) {
//				listener.updateInfoRutaTreasure(username);
//			}
			
			for(ViewerListener listener: listeners){
				
				listener.paintCharacter(character.getRow(), character.getCol(), oldRow, oldCol);
				listener.moveIcon(character.getCharacterName(), character.getRow(), character.getCol(), true);
				
				System.out.println("Client: flytta gubbe i viewer");
			}
			if (character.getCharacterName().equals(username) && steps == 0) {
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
				if(map[me.getRow() + 1][me.getCol()].containsCharacter()){
					if (map[me.getRow() + 1][me.getCol()].getCharacter().sleeping() < 1){
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
		
		//------Water Tile-----------------
		map[5][25].setNext(0, 1);
		map[5][26].setNext(0, 1);
		map[5][27].setNext(0, 1);
		map[4][27].setNext(1, 1); //
		map[5][28].setNext(1, 0);
		map[6][28].setNext(4, 0);
		map[10][28].setNext(0, 1);
		map[10][29].setNext(0, 1);
		map[10][30].setNext(0, 1);
		map[10][31].setNext(1, 0);
		map[11][31].setNext(1, 0);
		map[12][31].setNext(0, 1);
		map[12][32].setNext(1, 0);
		map[13][32].setNext(0, 1);
		map[13][33].setNext(1, 0);
		map[14][33].setNext(0, 1);
		map[14][34].setNext(1, 0);
		map[15][34].setNext(1, 0);
		map[16][34].setNext(1, 0);
		map[17][34].setNext(1, 0);
		map[17][35].setNext(1, -1); //
		map[17][36].setNext(0, -1); //
		map[17][37].setNext(0, -1); //
		map[16][37].setNext(1, 0); //
		map[18][34].setNext(0, -1);
		map[18][33].setNext(1, 0);
		map[19][33].setNext(0, -1);
		map[19][32].setNext(0, -1);
		map[19][31].setNext(-1, 0);
		map[18][31].setNext(0, -1);
		map[18][30].setNext(1, -1);
		map[19][29].setNext(0, -1);
		map[19][28].setNext(0, -1);
		map[19][27].setNext(0, -1);
		map[19][26].setNext(0, -1);
		map[19][25].setNext(0, -1);
		map[19][24].setNext(0, -1);
		map[19][23].setNext(0, -1);
		map[19][22].setNext(0, -1);
		map[18][22].setNext(1, 0); //-
		map[17][22].setNext(1, 0); //-
		map[17][21].setNext(0, -1); //
		map[17][20].setNext(-1, 0); //
		map[16][20].setNext(-1, 0); //
		map[15][20].setNext(-1, 0); //
		map[14][20].setNext(-1, 0); //
		map[13][20].setNext(0, -1); //
		map[13][19].setNext(-1, 0); //
		map[12][19].setNext(0, -1); //
		map[12][18].setNext(-1, 0); //
		map[11][18].setNext(0, -1); //
		map[11][17].setNext(0, -1); //
		map[11][16].setNext(1, 0); //
		map[12][16].setNext(0, -1); //
		map[12][15].setNext(0, -1); //
		map[12][14].setNext(1, 0); //
		map[13][14].setNext(0, -1); //
		map[13][13].setNext(1, 0); //
		map[14][13].setNext(0, -1); //
		map[14][12].setNext(1, 0); //
		map[15][12].setNext(0, -1); //
		map[15][11].setNext(0, -1); //
		map[15][10].setNext(1, -1); //
		map[19][21].setNext(0, -1);
		map[19][20].setNext(1, 0);
		map[20][20].setNext(0, -1);
		map[20][19].setNext(0, -1);
		map[20][18].setNext(1, 0);
		map[21][18].setNext(0, -1);
		map[21][17].setNext(1, 0);
		map[22][17].setNext(0, -1);
		map[22][16].setNext(0, -1);
		map[22][15].setNext(0, -1);
		map[22][14].setNext(0, -1);
		map[22][13].setNext(-1, 0);
		map[21][13].setNext(0, -1);
		map[21][12].setNext(0, -1);
		map[21][11].setNext(-1, 0);
		map[20][11].setNext(-1, 0);
		map[19][11].setNext(0, -1);
		map[19][10].setNext(-1, 0);
		map[18][10].setNext(-1, 0);
		map[17][10].setNext(0, -1);
		map[17][9].setNext(0, -1);
		map[17][8].setNext(-1, 0);
		map[16][8].setNext(0, -1);
		map[16][7].setNext(-1, 0);
		map[15][7].setNext(-1, 0);
		map[14][7].setNext(-1, 0);
		map[13][7].setNext(0, -1);
		map[13][6].setNext(0, -1);
		map[13][5].setNext(1, 0);
		map[14][5].setNext(0, -1);
		map[14][4].setNext(0, -1);
		map[14][3].setNext(1, 0);
		map[13][3].setNext(0, -1); //
		map[13][2].setNext(-1, 0); //
//		map[13][1].setNext(0, -1); //
//		map[13][0].setNext(0, -1); //
		map[15][3].setNext(1, 0);
		map[16][3].setNext(0, -1);
		map[16][2].setNext(1, 0);
//		map[16][1].setNext(0, -1);
//		map[16][0].setNext(0, -1);
		
		//------Special Tile---------------
		map[2][10].setSuccess(0, 3);
		map[2][10].setFail(4, 1);
		
		map[2][12].setSuccess(0, -2);
		map[2][12].setFail(4, -1);
		
		map[2][13].setSuccess(0, 2);
		map[2][13].setFail(4, 1);
		
		map[2][15].setSuccess(0, -3);
		map[2][15].setFail(4, -1);
		
		map[4][25].setSuccess(2, 0);
		map[4][25].setFail(1, 0);
		
		map[6][25].setSuccess(-2, 0);
		map[6][25].setFail(-1, 0);
		
		map[6][27].setSuccess(0, 2);
		map[6][27].setFail(0, 1);
		
		map[6][29].setSuccess(0, -2);
		map[6][29].setFail(0, -1);
		
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
		System.out.println("Client: LFS " + character.getRow() + ", " + character.getCol());
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

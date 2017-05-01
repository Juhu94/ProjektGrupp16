package client;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JLabel;
import gui.ExtendedJLabel;
import gui.ViewerListener;
import server.GameServer;

/**
 * 
 * @author Julian Hultgren, Simon Börjesson
 * Version 1.1.5
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

	public GameClient(){
		System.out.println("Klient Startad");
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
	public int throwDice(){
		Random rand = new Random();
		int diceNbr = rand.nextInt(6)+1;
		return diceNbr;
	}
	public boolean shootDice(){
		int roll = throwDice();
		if(roll == 2 || roll == 6){
			return true;
		}
		return false;
	}
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
	
	private class Connection extends Thread{
		private String ipAddress = "";
		private String username = "";
		private int port = 0;
			
		public Connection(String ipAddress, int port, String username){
			this.ipAddress = ipAddress;
			this.port = port;
			this.username = username;
		}
			
		public void run(){
			System.out.println("Client Running");
			try{
				socket = new Socket(ipAddress,port);	
				output = new ObjectOutputStream(socket.getOutputStream());
				input = new ObjectInputStream(socket.getInputStream());
				output.writeObject(username);
			}catch (IOException e ){
				e.printStackTrace();
			}
				
			while(!Thread.interrupted()){
				try{
						
					Object object = input.readObject();
					if(object instanceof Integer){
						iD = (int)object;	
					}
				}catch (IOException | ClassNotFoundException e){
					disconnect();
					e.printStackTrace();
				}
			}
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
		map[31][44].boatOn();
		map[24][37].boatOn();
		//------Canon-----------------------
		map[15][17].canonOn();
		//------Raft------------------------
		map[16][30].raftOn();
		
		return map;
	}

	public void theTile(ExtendedJLabel theTile){
		System.out.println("Från viewern till klienten");
		try{
			tilePos[0] = theTile.getCol();
			tilePos[1] = theTile.getRow();
			ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
			output.writeObject(tilePos);
			output.flush();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}		
}

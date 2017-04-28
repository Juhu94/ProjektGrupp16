package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JLabel;

import gui.ExtendedJLabel;
import gui.ViewerListener;

/**
 * 
 * @author Julian Hultgren
 * Version 1.1
 *
 */


//Klassen behövs utökas med funktioner för den som ska agera spelvärd
public class GameClient implements Serializable{

	private String username = "";
	private int iD;
	private Socket socket;
	private ArrayList<ViewerListener> listeners = new ArrayList<ViewerListener>();
	private int[] tilePos = new int[2];
	private static int[] mapArray = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,
					1,1,1,1,1,1,1,1,1,2,1,1,1,1,1,2,2,1,1,1,1,1,1,1,1,1,1,3,3,3,3,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,
					1,1,1,1,1,1,1,1,2,2,4,1,4,1,4,2,2,2,2,1,1,1,1,1,1,1,3,2,2,2,2,3,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,
					1,1,1,1,1,1,1,3,2,2,3,1,3,1,3,3,2,3,2,2,1,1,1,1,1,2,2,2,3,3,2,2,3,1,1,1,1,1,1,1,1,1,1,1,1,1,1,
					1,1,1,1,1,1,1,2,2,3,3,1,3,1,3,3,3,3,2,2,3,1,1,1,3,4,2,1,3,3,3,2,2,3,3,1,1,1,1,1,1,1,1,1,1,1,1,
					1,1,1,1,1,1,1,2,2,3,3,3,3,3,3,3,3,3,2,2,3,3,1,1,3,1,1,1,1,3,2,2,3,3,3,1,1,1,1,1,1,1,1,1,1,1,1,
					1,1,1,1,1,1,3,2,3,3,3,2,3,2,3,3,3,3,3,2,3,2,2,3,3,4,2,4,1,4,2,3,3,3,3,1,1,1,1,1,1,1,1,1,1,1,1,
					1,1,1,1,1,1,2,2,3,3,3,2,2,2,2,3,3,3,3,2,2,2,3,3,2,2,2,3,1,3,2,2,3,3,3,1,1,1,1,1,1,1,1,1,1,1,1,
					1,1,1,1,1,3,2,2,3,3,3,3,3,3,2,2,2,3,3,2,2,2,3,2,2,3,3,3,1,3,2,3,3,3,3,1,1,1,1,1,1,1,1,1,1,1,1,
					1,1,1,1,2,2,2,2,3,3,3,3,3,3,3,3,2,3,2,2,2,2,2,2,3,3,3,3,1,3,2,3,3,3,2,2,1,1,1,1,1,1,1,1,1,1,1,
					1,1,2,2,2,2,2,2,2,2,2,3,2,3,2,2,2,2,2,2,2,2,2,3,2,2,2,2,1,1,1,1,3,3,2,2,3,1,1,1,1,1,1,1,1,1,1,
					1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,3,2,2,2,2,2,2,3,2,1,1,1,3,3,3,1,3,3,2,2,2,2,1,1,1,1,1,1,1,1,1,
					1,1,4,2,2,2,4,3,3,3,3,2,2,2,3,3,1,1,1,3,3,2,2,3,2,1,1,3,3,3,3,1,1,3,3,2,2,2,2,2,2,1,1,1,1,1,1,
					1,1,1,1,2,1,1,1,3,3,3,3,2,3,1,1,1,3,1,1,3,2,2,2,2,2,3,2,2,2,3,3,1,1,3,3,2,3,2,2,2,2,1,1,1,1,1,
					1,1,4,1,1,1,4,1,3,2,2,2,2,1,1,3,3,3,3,1,1,2,2,2,2,2,2,2,2,2,3,3,3,1,1,3,3,3,3,2,2,2,1,1,1,1,1,
					1,1,1,1,3,3,2,1,3,2,2,4,1,1,3,3,2,2,3,2,1,2,2,3,2,2,2,2,2,2,3,2,3,3,1,3,3,3,2,2,2,2,2,1,1,1,1,
					1,1,4,2,3,3,2,1,1,3,1,1,1,3,3,3,2,3,3,4,1,4,2,3,2,2,2,2,2,2,2,2,2,4,1,4,1,4,2,2,2,2,2,1,1,1,1,
					1,2,2,2,2,3,2,2,1,1,1,4,2,2,3,3,2,2,2,2,1,1,1,3,3,2,2,2,3,3,3,2,2,2,1,1,1,3,2,2,2,2,2,1,1,1,1,
					1,2,2,2,2,2,2,2,2,4,1,4,2,2,2,2,2,2,2,2,1,3,1,3,3,4,3,3,3,1,1,1,2,1,1,3,3,3,2,2,3,2,2,1,1,1,1,
					1,2,2,2,2,2,2,2,2,2,1,1,2,2,2,2,2,2,2,4,1,1,1,1,1,1,1,1,1,1,3,1,1,1,3,3,3,2,2,2,3,3,2,2,1,1,1,
					1,1,2,3,2,2,2,2,2,2,2,1,2,3,3,2,2,2,1,1,1,2,2,2,2,4,3,3,3,3,3,3,3,2,2,2,2,2,2,2,2,2,2,2,1,1,1,
					1,1,3,3,3,2,2,2,2,2,3,1,1,1,3,3,3,1,1,4,2,2,2,2,2,2,3,3,3,3,3,3,2,2,2,2,2,2,2,2,2,2,2,2,1,1,1,
					1,1,3,3,3,2,2,3,3,3,3,3,2,1,1,1,1,1,2,2,2,2,2,2,2,2,3,3,3,3,3,3,3,3,3,2,2,2,2,2,2,2,2,2,1,1,1,
					1,1,1,1,2,2,2,2,3,3,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,3,3,3,3,3,3,3,3,3,3,2,2,2,2,2,2,2,2,2,1,1,1,
					1,1,1,1,2,2,3,2,2,2,2,2,2,2,2,2,2,2,2,2,3,3,2,2,3,3,3,3,3,3,3,3,3,2,2,2,2,2,2,2,2,3,2,2,2,1,1,
					1,1,1,1,2,3,3,2,2,2,2,2,3,3,2,2,2,2,3,3,3,3,2,2,3,3,3,3,3,3,3,3,2,2,2,2,2,3,2,2,3,3,2,2,2,2,1,
					1,1,1,1,1,3,3,2,2,2,2,2,3,3,2,2,2,2,3,3,3,3,2,2,3,2,2,2,3,3,3,2,2,2,2,2,2,3,2,2,3,3,3,2,2,2,1,
					1,1,1,1,1,1,3,2,2,3,2,2,2,2,2,2,2,3,3,3,3,3,2,2,2,2,2,2,4,3,4,2,2,2,2,2,3,3,2,2,2,3,3,2,2,2,1,
					1,1,1,1,1,1,1,2,3,3,3,2,2,2,2,2,2,3,3,3,3,2,2,2,2,2,2,3,3,3,3,3,2,2,3,3,3,3,2,2,3,3,3,2,2,2,1,
					1,1,1,1,1,1,1,2,3,3,3,3,2,2,2,2,2,3,3,3,2,2,2,2,3,2,3,3,3,3,3,3,3,2,3,3,3,3,3,3,3,3,3,2,2,1,1,
					1,1,1,1,1,1,1,2,3,3,3,3,2,2,2,2,3,3,3,3,2,2,2,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,2,2,1,1,
					1,1,1,1,1,1,1,2,3,2,2,3,2,2,3,2,3,3,3,3,2,2,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,2,2,3,3,1,
					1,1,1,1,1,1,1,2,2,2,2,3,2,3,3,2,3,3,3,3,3,3,3,3,3,3,3,3,3,2,3,3,3,3,3,3,3,3,3,3,3,2,2,2,3,3,1,
					1,1,1,1,1,1,1,2,2,2,3,2,2,3,3,2,3,3,3,3,3,3,3,3,3,3,3,3,3,2,2,3,3,3,3,3,2,2,2,2,2,2,2,2,2,1,1,
					1,1,1,1,1,1,1,1,2,2,2,2,1,3,3,2,3,3,3,2,2,2,3,2,3,2,3,3,3,3,2,2,3,3,3,2,2,2,2,2,2,1,1,2,2,1,1,
					1,1,1,1,1,1,1,1,2,2,1,1,1,1,1,2,2,3,3,2,2,2,2,2,2,2,2,2,3,3,3,2,2,2,2,2,2,2,2,2,1,1,1,1,1,1,1,
					1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,2,2,2,2,1,1,1,1,2,2,2,2,2,3,3,2,2,2,2,2,2,2,1,1,1,1,1,1,1,1,1,
					1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,2,2,1,1,1,1,1,1,2,2,2,2,3,2,2,2,1,2,2,2,1,1,1,1,1,1,1,1,1,1,
					1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,2,2,2,2,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,
					1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,2,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,
					1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};
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
	
	private class Connection extends Thread{
		private String ipAddress = "";
		private String username = "";
		private int port = 0;
		ObjectInputStream input;
		ObjectOutputStream output;
			
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
					e.printStackTrace();
				}
			}
		}	
			
	}
	
	/**
	 * Creates a map from a int array
	 * @param 	int[]		int array
	 * @return	Tile[][]	map array
	 * 
	 * @author Simon Börjesson
	 */
	
	public Tile[][] createMap(int[] array){
		Tile[][] map = new Tile[41][47];
		int temp = 0;
		int temp2 = 0;
		for (int i = 0; i < array.length; i++){
			temp2 = temp;
			temp = Math.round((i/47));
			
//			if (!(temp == temp2)){
//				System.out.println(";" );
//			}else{
//				System.out.print(",");
//			}
			if (array[i] == 1){
				map[temp][i % 47] = new WaterTile();
//				System.out.print("-");
			}else if (array[i] == 2){
				map[temp][i % 47] = new GroundTile(false, false, false);
//				System.out.print("#");
			}else if (array[i] == 3){
				map[temp][i % 47] = new JungleTile();
//				System.out.print("▓");
			}else if (array[i] == 4){
				map[temp][i % 47] = new SpecialTile();
//				System.out.print("♥");
			}
			
//			System.out.print("Map [" + temp + "] [" + i % 47 +"] = " + array[i] + " " + i + " ");
		}
		return map;
	}
	

	
	public void theTile(ExtendedJLabel theTile){
		System.out.println("Från viewern till klienten");
		try{
//			Message m = new Message(theTile);
			tilePos[0] = theTile.getCol();
			tilePos[1] = theTile.getRow();
			ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
			output.writeObject(tilePos);
			output.flush();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

//
//	public static void main(String[] args) {
//		GameClient cc = new GameClient();
//		Viewer vv = new Viewer(cc);
//	}			
}

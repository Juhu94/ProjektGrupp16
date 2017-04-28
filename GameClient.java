package projekt;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JLabel;
import java.io.FileReader;
import java.util.Scanner;
import java.io.FileNotFoundException;

/**
 * 
 * @author Julian Hultgren
 *
 */
public class GameClient implements Serializable{

	private int iD;
	private Socket socket;
	private ArrayList<ViewerListener> listeners = new ArrayList<ViewerListener>();
	private int[] tilePos = new int[2];
	
	public GameClient(String serverIp, int port){
		new Connection(serverIp,port).start();
	}
	
	public void addListeners(ViewerListener listener) {
		listeners.add(listener);
	}
	
	private class Connection extends Thread{
		private String ipAddress = "";
		private int port = 0;
		ObjectInputStream input;
			
		public Connection(String ipAddress, int port){
			this.ipAddress = ipAddress;
			this.port = port;
		}
			
		public void run(){
			System.out.println("Client Running");
			try{
				socket = new Socket(ipAddress,port);
					
				input = new ObjectInputStream(socket.getInputStream());
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
	 * Creates a map with help from map.txt
	 * @return	Tile[][]	map array
	 * 
	 * @author Simon Börjesson
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
			
//			System.out.print("Map [" + temp + "] [" + i % 47 +"] = " + mapint[i] + " " + i + " ");
		}
		return map;
	}
	
	//Behöver modifieras!
	public void updateBoard(Message inMessage) {
		for(ViewerListener listener : listeners) {
			listener.updateViewer(inMessage.getTheLabel());
			System.out.println("Uppdatera viewern med vad som kommer in från server");
		}
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


	public static void main(String[] args) {
		GameClient cc = new GameClient("127.0.0.1" ,3520);
		Viewer vv = new Viewer(cc);
	}			
}

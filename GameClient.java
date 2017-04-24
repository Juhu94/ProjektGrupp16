package projekt;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JLabel;

/**
 * 
 * @author Julian Hultgren
 *
 */
public class GameClient implements Serializable{

	private transient Socket socket;
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
		
		public Connection(String ipAddress, int port){
			this.ipAddress = ipAddress;
			this.port = port;
		}
		
		public void run(){
			System.out.println("Client Running");
			try{
				socket = new Socket(ipAddress,port);
				
			}catch (IOException e ){
				e.printStackTrace();
			}
			
			while(!Thread.interrupted()){
				try{
					ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
					Object object = input.readObject();
					if(object instanceof Message){
						Message inMessage = (Message) object;
						updateBoard(inMessage);
						System.out.println("Från servern till alla ansluta klienter");
					}
				}catch (IOException | ClassNotFoundException e){
					e.printStackTrace();
				}
			}
		}	
		
	}
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

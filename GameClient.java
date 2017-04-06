package klient;

import java.awt.Color;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import javax.swing.JLabel;

import BoardGame.Viewer;
/**
 * 
 * @author Julian Hultgren
 *
 */
public class GameClient {
	private Socket socket;
	private Viewer viewer;
	
	public GameClient(String serverIp, int port){
		new Connection(serverIp,port).start();
	}
	
	private class Connection extends Thread {
		private String ipAddress;
		private int port;
		private boolean connected = false;
		
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
			
			while(!this.currentThread().isInterrupted()){
				try{
					
					ObjectInputStream oInput = new ObjectInputStream(socket.getInputStream());
					DataInputStream dInput = new DataInputStream(socket.getInputStream());
					
					
				}catch (IOException e){
					e.printStackTrace();
				}
			}
		}
	}
	
	public void changeTileColor(JLabel theLabel){
		theLabel.setBackground(Color.RED);
	}
	
	public static void main(String[] args) {
		GameClient cc = new GameClient("127.0.0.1" ,3520);
		Viewer vv = new Viewer();
	}
}

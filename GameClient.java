package klient;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
/**
 * 
 * @author Julian Hultgren
 *
 */
public class GameClient {
	private Socket socket;
	
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
}

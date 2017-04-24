package projekt;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Server implements Runnable{

	
	private Thread serverThread = new Thread(this);
	private ServerSocket serverSocket;
	private ArrayList<Socket> users = new ArrayList<Socket>();
	private ArrayList<String> username = new ArrayList<String>();
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd - HH:mm:ss");
	
	
	public Server(int port){	
		try{
		    SimpleDateFormat log = new SimpleDateFormat("");
		    String d = log.format(new Date());

			serverSocket = new ServerSocket(port);
			serverThread.start();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void run(){
		System.out.println("Server running...");
		System.out.println("Listening for clients...");
		while(true){
			try {
				Socket socket = serverSocket.accept();
				users.add(socket);
				System.out.println("Client Connected from... " +socket.getRemoteSocketAddress());
				new ClientHandler(socket).start();

			}catch(IOException e){
				System.out.println(e);
				
			}
		}
	}
	
	private class ClientHandler extends Thread{
		private Socket socket;
		public ClientHandler(Socket socket){
			this.socket = socket;
		}
		public void run(){
			boolean error = false;
			while(socket.isConnected() && !error){
				try {
					ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
					Message inMessage = (Message)input.readObject();
					SendMessage(inMessage);
//					if(object instanceof Message){
//						Message inMessage = (Message) object;
//						SendMessage(inMessage);
//					}
					
				} catch (IOException | ClassNotFoundException e) {
//					e.printStackTrace();
				}

			}
					
		}
		private void SendMessage(Message inMessage) {
			try {
				for(Socket clients : users){
					ObjectOutputStream output = new ObjectOutputStream(clients.getOutputStream());
					output.writeObject(inMessage);
					output.flush();
					System.out.println(inMessage);
				}	
			} catch (IOException e) {
				e.printStackTrace();
			}		
		}	
	}
	
	public static void main(String[] args) {
		Server s = new Server(3520);
	}
}

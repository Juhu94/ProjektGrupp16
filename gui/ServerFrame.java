package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import client.GameClient;
/**
 * 
 * @author Julian Hultgren
 * Version 1.0
 *
 */
public class ServerFrame extends JPanel implements MouseListener, ActionListener, ViewerListener{
	
	private JPanel panel = new JPanel();
	private JPanel leftPanel = new JPanel(new BorderLayout());
	private JPanel leftGridPanel = new JPanel(new GridLayout(3,1));
	private JPanel rightPanel = new JPanel(new BorderLayout());
	private JPanel centerPanel = new JPanel();
	private JPanel flowPanel = new JPanel();
	private JPanel inputPanel = new JPanel(new BorderLayout(50,0));
	private JPanel inputLeftPanel = new JPanel(new FlowLayout());
	private JPanel inputRightPanel = new JPanel(new GridBagLayout());
	private JPanel inputMiddlePanel = new JPanel(new GridBagLayout());
	
	private JList listUsers = new JList();
	private JTextArea infoArea = new JTextArea(
			"Du är spelvärd för en spelomgång.\n"
			+"Skriv in vilket username du vill använda i rutan ovanför.\n"
			+"Klicka sedan på 'Start server' för att starta servern.\n"
			+"För att starta en spelomgång måste minst 1 till klient\n"
			+"utöver spelvärden (du) vara ansluten.\n"
			+"Klicka sedan på 'Starta spelomgång'.\n"
			+"-----------------------------------------------------------------------------------\n");
	
	private JTextField username = new JTextField();
	
	private JButton bStartGame = new JButton("Starta spelomgång");
	private JButton bDisconnect = new JButton("Disconnect");
	private JButton bClose = new JButton("Close");
	private JButton bStartServer = new JButton("Start server");
	private JButton bLeft = new JButton("<<");
	private JButton bRight = new JButton(">>");
	private JButton bUp = new JButton("^");
	private JButton bDown = new JButton("v");
	private JButton bMove = new JButton("Move");
	private JButton bShoot = new JButton("Shoot");
	
	private JFrame frame = new JFrame("Host");
	
	private JLabel[][] boardArray = new JLabel[41][47];
	
	private GameClient client;
	
	public ServerFrame(GameClient client) {
		this.client = client;
		client.addListeners(this);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLayout(new BorderLayout());
		flowPanel.setLayout(new FlowLayout());
		flowPanel.add(centerPanel);
		
		panel.setLayout(new BorderLayout());
		panel.add(inputPanel, BorderLayout.SOUTH);
		panel.add(flowPanel, BorderLayout.CENTER);
		panel.add(leftPanel, BorderLayout.WEST);
		panel.add(rightPanel, BorderLayout.EAST);
		
		JScrollPane listUsersSP = new JScrollPane(listUsers);
		listUsersSP.setBorder(BorderFactory.createTitledBorder("Connected Users:"));
		listUsersSP.setPreferredSize(new Dimension(180, 300));
		leftPanel.add(listUsersSP, BorderLayout.SOUTH);
		
		username.setBorder(BorderFactory.createTitledBorder("Username"));
		infoArea.setEditable(false);
		infoArea.setBorder(BorderFactory.createTitledBorder("Info ruta"));
		leftGridPanel.add(username);
		leftGridPanel.add(bStartServer);
		leftGridPanel.add(bStartGame);
		leftPanel.add(leftGridPanel, BorderLayout.NORTH);
		leftPanel.add(infoArea, BorderLayout.CENTER);
		
		inputPanel.setPreferredSize(new Dimension(100,50));
		inputPanel.add(inputLeftPanel, BorderLayout.WEST);
		inputPanel.add(inputMiddlePanel, BorderLayout.CENTER);
		inputPanel.add(inputRightPanel, BorderLayout.EAST);
		
		inputMiddlePanel.add(bMove);
		inputMiddlePanel.add(bShoot);
		inputMiddlePanel.add(bLeft);
		inputMiddlePanel.add(bRight);
		
		inputRightPanel.add(bDisconnect);
		inputRightPanel.add(bClose);
		
		centerPanel.setLayout(new GridLayout(41,47,1,1));
		
		for(int i = 0; i < 41; i++){
			for(int j = 0; j < 47; j++){
				boardArray[i][j] = new ExtendedJLabel(i,j,Color.RED);
				boardArray[i][j].setBackground(Color.WHITE);
				boardArray[i][j].setHorizontalAlignment(JLabel.CENTER);
				boardArray[i][j].setOpaque(true);
				boardArray[i][j].addMouseListener(this);
				boardArray[i][j].setPreferredSize(new Dimension(22,22));
				centerPanel.add(boardArray[i][j]);
			}
		}
		bStartGame.addActionListener(this);
		bStartServer.addActionListener(this);
		bDisconnect.addActionListener(this);
		bClose.addActionListener(this);
		bMove.addActionListener(this);
		bShoot.addActionListener(this);
		bUp.addActionListener(this);
		bDown.addActionListener(this);
		bLeft.addActionListener(this);
		bRight.addActionListener(this);
		
		bStartGame.setEnabled(false);
		bMove.setEnabled(false);
		bShoot.setEnabled(false);
		bUp.setEnabled(false);
		bDown.setEnabled(false);
		bLeft.setEnabled(false);
		bRight.setEnabled(false);
		
		frame.add(panel);
		frame.pack();
		frame.setLocationRelativeTo(null);
	}
	public void updateViewer(JLabel in) {
		in.setBackground(Color.RED);		
	}
	/**
	 * Method to enable buttons
	 * @param enableButtons boolean
	 */
	public void updateViewer(boolean enableButtons){
		enableButtons(enableButtons);
	}
	public void paintCharacter(int row, int col) {
		
	}
	/**
	 * method to enable or disable all buttons
	 * @param state boolean
	 */
	public void enableButtons(boolean state){
		bMove.setEnabled(state);
		bShoot.setEnabled(state);
		bUp.setEnabled(state);
		bDown.setEnabled(state);
		bLeft.setEnabled(state);
		bRight.setEnabled(state);
	}
	
	public void updateInfoRuta(String text) {
		infoArea.append(text+"\n");
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == bStartGame) {
			//Anropa start metoden i GameClient
			client.startGame();
			bStartGame.setEnabled(false);
		}
		if(e.getSource() == bStartServer) {
			client.startServer();
			try {
				client.connect(InetAddress.getLocalHost().getHostAddress(), 3520, username.getText());
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			infoArea.append("Du har valt '"+username.getText()+"' som ditt username");
			client.sendUsername(username.getText());
			username.setText("");
			bStartServer.setEnabled(false);
			bStartGame.setEnabled(true);
		}
		if(e.getSource() == bDisconnect) {
			client.disconnect();
		}
		if(e.getSource() == bClose) {
			System.exit(0);
		}
		if(e.getSource() == bMove){
			client.startGame();
			enableButtons(false);
		}
		if(e.getSource() == bShoot){
			
		}
	}
	public void mouseClicked(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1){
//			JLabel theLabel = new JLabel();
			ExtendedJLabel theLabel = (ExtendedJLabel)e.getSource();
//			client.theTile(theLabel);
			System.out.println("Någon har tryckt på en ruta på spelbrädet,(row="+theLabel.getRow());
			theLabel.setBackground(Color.BLUE);
			theLabel.repaint();
		}	
		if(e.getButton() == MouseEvent.BUTTON3){
			JLabel theLabel = (JLabel) e.getSource();
			theLabel.setBackground(Color.BLUE);
		}	
	}
	public void mouseEntered(MouseEvent e) {	
	}
	public void mouseExited(MouseEvent e) {	
	}
	public void mousePressed(MouseEvent e) {		
	}
	public void mouseReleased(MouseEvent e) {		
	}
	@Override
	public void updateViewer(ExtendedJLabel theLabel) {
		// TODO Auto-generated method stub
		
	}
}
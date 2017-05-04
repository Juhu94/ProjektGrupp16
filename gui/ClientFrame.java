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
 * Version 1.1.3
 *
 */
public class ClientFrame extends JPanel implements MouseListener, ActionListener, ViewerListener{
	
	private JPanel panel = new JPanel();
	private JPanel leftPanel = new JPanel(new BorderLayout());
	private JPanel leftGridPanel = new JPanel(new GridLayout(4,1));
	private JPanel rightPanel = new JPanel(new BorderLayout());
	private JPanel centerPanel = new JPanel();
	private JPanel flowPanel = new JPanel();
	private JPanel inputPanel = new JPanel(new BorderLayout(50,0));
	private JPanel inputLeftPanel = new JPanel(new FlowLayout());
	private JPanel inputRightPanel = new JPanel(new GridBagLayout());
	private JPanel inputMiddlePanel = new JPanel(new GridBagLayout());
	
	private JList listUsers = new JList();
	private JTextArea infoArea = new JTextArea(
			 "För att ansluta till en server:\n"
			+"Skriv in serverns ip och port ovanför.\n"
			+"Skriv också in ett användarnamn du vill använda\n"
			+"----------------------------------------------------------------------\n");
	
	private JTextField serverIp = new JTextField();
	private JTextField serverPort = new JTextField();
	private JTextField username = new JTextField();
	
	private JButton bConnect = new JButton("Connect");
	private JButton bDisconnect = new JButton("Disconnect");
	private JButton bClose = new JButton("Close");
	
	private JButton bLeft = new JButton("<<");
	private JButton bRight = new JButton(">>");
	private JButton bUp = new JButton("^");
	private JButton bDown = new JButton("v");
	private JButton bMove = new JButton("Move");
	private JButton bShoot = new JButton("Shoot");
	
	private JFrame frame = new JFrame("Client");
	
	private JLabel[][] boardArray = new JLabel[41][47];
	
	private GameClient client;
	
	public ClientFrame(GameClient client) {
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
		
		serverIp.setBorder(BorderFactory.createTitledBorder("Server IP"));
		serverIp.setPreferredSize(new Dimension(180,45));
		serverPort.setBorder(BorderFactory.createTitledBorder("Server Port"));
		username.setBorder(BorderFactory.createTitledBorder("Username"));
		infoArea.setEditable(false);
		infoArea.setBorder(BorderFactory.createTitledBorder("Info ruta"));
		leftGridPanel.add(serverIp);
		leftGridPanel.add(serverPort);
		leftGridPanel.add(username);
		leftGridPanel.add(bConnect);
		leftPanel.add(leftGridPanel, BorderLayout.NORTH);
		leftPanel.add(infoArea, BorderLayout.CENTER);
		
		inputPanel.setPreferredSize(new Dimension(100,50));
		inputPanel.add(inputLeftPanel, BorderLayout.WEST);
		inputPanel.add(inputMiddlePanel, BorderLayout.CENTER);
		inputPanel.add(inputRightPanel, BorderLayout.EAST);
		
		inputMiddlePanel.add(bMove);
		inputMiddlePanel.add(bShoot);
		inputMiddlePanel.add(bLeft);
		inputMiddlePanel.add(bUp);
		inputMiddlePanel.add(bDown);
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
		bDisconnect.addActionListener(this);
		bConnect.addActionListener(this);
		bClose.addActionListener(this);
		bMove.addActionListener(this);
		bShoot.addActionListener(this);
		bUp.addActionListener(this);
		bDown.addActionListener(this);
		bLeft.addActionListener(this);
		bRight.addActionListener(this);
		
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
	public void updateViewer(JLabel theLabel) {
		theLabel.setBackground(Color.RED);		
	}
	/**
	 * Method to enable buttons
	 * @param enableButtons boolean
	 */
	public void updateViewer(boolean enableButtons){
		enableButtons("update", enableButtons);
	}
	public void updateInfoRuta(String text) {
		infoArea.append(text+"\n");
	}
	/**
	 * method to enable or disable all buttons
	 * @param state boolean
	 */
	public void enableButtons(String buttons, boolean state){
		if(buttons == "update"){
			bMove.setEnabled(state);
			bShoot.setEnabled(false);
			bUp.setEnabled(false);
			bDown.setEnabled(false);
			bLeft.setEnabled(false);
			bRight.setEnabled(false);
		} else if(buttons == "move"){
			bUp.setEnabled(state);
			bDown.setEnabled(state);
			bLeft.setEnabled(state);
			bRight.setEnabled(state);
		} else if(buttons == "shoot"){
			bShoot.setEnabled(state);
		}
		
//		bUp.setEnabled(state);
//		bDown.setEnabled(state);
//		bLeft.setEnabled(state);
//		bRight.setEnabled(state);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == bConnect) {
			client.connect(serverIp.getText(), Integer.parseInt(serverPort.getText()),username.getText());
			bConnect.setEnabled(false);
		}
		if(e.getSource() == bDisconnect) {
			client.disconnect();
		}
		if(e.getSource() == bClose) {
			System.exit(0);
		}	
		/**
		 * har just nu lagt till att det bytar spelare på move
		 * bara tillfälligt för att testa så att det fungerade
		 */
		if(e.getSource() == bMove){
			enableButtons("move", true);
		}
		if(e.getSource() == bShoot){
			
		}
	}
	public void mouseClicked(MouseEvent e) {
//		if(e.getButton() == MouseEvent.BUTTON1){
//			ExtendedJLabel theLabel = (ExtendedJLabel)e.getSource();
//			client.theTile(theLabel);
//			System.out.println("Någon har tryckt på en ruta på spelbrädet,(row="+theLabel.getRow());
//			theLabel.setBackground(Color.BLUE);
//			theLabel.repaint();
//		}	
//		if(e.getButton() == MouseEvent.BUTTON3){
//			JLabel theLabel = (JLabel) e.getSource();
//			theLabel.setBackground(Color.BLUE);
//		}	
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
	@Override
	public void paintCharacter(int row, int col) {
		// TODO Auto-generated method stub
		
	}
}
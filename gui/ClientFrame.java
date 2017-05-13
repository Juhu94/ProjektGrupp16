package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import client.GameClient;

/**
 * 
 * @author Julian Hultgren, Erik Johansson, Simon Börjesson, Lukas Persson
 * Version 1.2
 *
 */
public class ClientFrame extends JPanel implements ActionListener, ViewerListener,KeyListener {

	private JPanel panel = new JPanel();
	private JPanel leftPanel = new JPanel(new BorderLayout());
	private JPanel leftGridPanel = new JPanel(new GridLayout(4, 1));
	private JPanel rightPanel = new JPanel(new BorderLayout());
	private JPanel centerPanel = new JPanel();
	private JPanel flowPanel = new JPanel();
	private JPanel inputPanel = new JPanel(new BorderLayout(50, 0));
	private JPanel inputLeftPanel = new JPanel(new FlowLayout());
	private JPanel inputRightPanel = new JPanel(new GridBagLayout());
	private JPanel inputMiddlePanel = new JPanel(new GridBagLayout());
	private JLayeredPane mapPane = new JLayeredPane();
	private JLabel mapLabel = new JLabel();
	
	private JLabel svullo = new JLabel(new ImageIcon(new ImageIcon("images/Svullo.png").getImage().getScaledInstance(22, 40, Image.SCALE_SMOOTH)));
	private JLabel tjoPang = new JLabel(new ImageIcon(new ImageIcon("images/TjoPang.png").getImage().getScaledInstance(22, 40, Image.SCALE_SMOOTH)));
	private JLabel råttan = new JLabel(new ImageIcon(new ImageIcon("images/Råttan.png").getImage().getScaledInstance(22, 40, Image.SCALE_SMOOTH)));
	private JLabel hannibal = new JLabel(new ImageIcon(new ImageIcon("images/Hannibal.png").getImage().getScaledInstance(22, 40, Image.SCALE_SMOOTH)));
	private JLabel markisen = new JLabel(new ImageIcon(new ImageIcon("images/Markisen.png").getImage().getScaledInstance(22, 40, Image.SCALE_SMOOTH)));
	private JLabel hook = new JLabel(new ImageIcon(new ImageIcon("images/Hook.png").getImage().getScaledInstance(22, 40, Image.SCALE_SMOOTH)));

	private DefaultListModel model = new DefaultListModel();
	private JList listUsers = new JList(model);
	private JTextArea infoArea = new JTextArea("För att ansluta till en server:\n"
			+ "Skriv in serverns ip och port ovanför.\n" + "Skriv också in ett användarnamn du vill använda\n"
			+ "----------------------------------------------------------------------\n"
			+ "Antal steg:   \n"
			+ "----------------------------------------------------------------------\n");

	private JTextField serverIp = new JTextField("");
	private JTextField serverPort = new JTextField("3520");
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
	private JButton bEndTurn = new JButton("End turn");
	private JButton bJump = new JButton("Jump");

	private JFrame frame = new JFrame("Client");

	private ExtendedJLabel[][] boardArray = new ExtendedJLabel[41][47];

	private GameClient client;

	public ClientFrame(GameClient client) {
		this.client = client;
		client.addListeners(this);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLayout(new BorderLayout());
		flowPanel.setLayout(new FlowLayout());
		
		mapPane.setPreferredSize(new Dimension(1034, 820));
		flowPanel.add(mapPane);
		mapLabel.setIcon(new ImageIcon("images/mapNewConcept.png"));
		mapPane.add(centerPanel, new Integer(1));
		mapPane.add(mapLabel, new Integer(2));
		
		mapPane.add(svullo, new Integer(3));
		mapPane.add(tjoPang, new Integer(4));
		mapPane.add(råttan, new Integer(5));
		mapPane.add(markisen, new Integer(6));
		mapPane.add(hannibal, new Integer(7));
		mapPane.add(hook, new Integer(8));
		
		centerPanel.setBounds(0, 0, 1034, 820);
		mapLabel.setBounds(0, 0, 1034, 820);
		
		svullo.setVisible(false);
		tjoPang.setVisible(false);
		råttan.setVisible(false);
		markisen.setVisible(false);
		hannibal.setVisible(false);
		hook.setVisible(false);
		
		råttan.setBounds(0, 0, 22, 40);
		tjoPang.setBounds(0, 0, 22, 40);
		svullo.setBounds(0, 0, 22, 40);
		markisen.setBounds(0, 0, 22, 40);
		hannibal.setBounds(0, 0, 22, 40);
		hook.setBounds(0, 0, 22, 40);

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
		serverIp.setPreferredSize(new Dimension(180, 45));
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

		inputPanel.setPreferredSize(new Dimension(100, 50));
		inputPanel.add(inputLeftPanel, BorderLayout.WEST);
		inputPanel.add(inputMiddlePanel, BorderLayout.CENTER);
		inputPanel.add(inputRightPanel, BorderLayout.EAST);

		inputMiddlePanel.add(bMove);
		inputMiddlePanel.add(bJump);
		inputMiddlePanel.add(bShoot);
		inputMiddlePanel.add(bEndTurn);
		inputMiddlePanel.add(bLeft);
		inputMiddlePanel.add(bUp);
		inputMiddlePanel.add(bDown);
		inputMiddlePanel.add(bRight);

		inputRightPanel.add(bDisconnect);
		inputRightPanel.add(bClose);

		centerPanel.setLayout(new GridLayout(41, 47, 0, 0));
		String s;
		for (int i = 0; i < 41; i++) {
			for (int j = 0; j < 47; j++) {
				boardArray[i][j] = new ExtendedJLabel(i, j, Color.RED);
				//------------------------------------------------------TEMP
				s = client.getTile(i, j);
				switch (s) {
				case "Ground":
					boardArray[i][j].setBackground(Color.GRAY);
					break;
				case "Water":
					boardArray[i][j].setBackground(Color.BLUE);
					break;
				case "Jungle":
					boardArray[i][j].setBackground(Color.GREEN);
					break;
				case "BLACK":
					boardArray[i][j].setBackground(Color.BLACK);
					break;
				case "ORANGE":
					boardArray[i][j].setBackground(Color.ORANGE);
					break;
					
				case "Special":
					boardArray[i][j].setBackground(Color.DARK_GRAY);
					break;

				default:
					break;
				}
				//------------------------------------------------------
				//boardArray[i][j].setBackground(Color.GRAY);
				boardArray[i][j].setHorizontalAlignment(JLabel.CENTER);
				boardArray[i][j].setOpaque(true);
				boardArray[i][j].setPreferredSize(new Dimension(22, 20));
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
		bEndTurn.addActionListener(this);
		bJump.addActionListener(this);

		bMove.setEnabled(false);
		bShoot.setEnabled(false);
		bUp.setEnabled(false);
		bDown.setEnabled(false);
		bLeft.setEnabled(false);
		bRight.setEnabled(false);
		bEndTurn.setEnabled(false);
		bJump.setEnabled(false);
		

		frame.add(panel);
		frame.pack();
		frame.addKeyListener(this);
		frame.setFocusable(true);
		frame.setLocationRelativeTo(null);
	}
	
	public void removeConnectedUsers(){
		model.clear();
	}
	
	public void addConnectedUser(String name){
		model.addElement(name);
	}

	public void updateViewer(ExtendedJLabel theLabel) {
		theLabel.setBackground(Color.RED);
	}

	/**
	 * Method to enable buttons
	 * 
	 * @param enableButtons
	 *            boolean
	 */
	public void updateViewer() {
		enableButtons("update");
	}

	public void updateInfoRuta(String text) {
//		infoArea.append(text + "\n");
		infoArea.replaceRange(text, 190, 203);
	}
	
	public void moveIcon(String name, int row, int col){
		
		switch (name) {
		case "Svullo":
			svullo.setLocation((col * 22)-22, (row *20)-40);
			svullo.setVisible(true);
			break;
		case "TjoPang":
			tjoPang.setLocation((col * 22)-22, (row *20)-40);
			tjoPang.setVisible(true);
			break;
		case "Råttan":
			råttan.setLocation((col * 22)-22, (row *20)-40);
			råttan.setVisible(true);
			break;
		case "Hannibal":
			hannibal.setLocation((col * 22)-22, (row *20)-40);
			hannibal.setVisible(true);
			break;
		case "Markisen":
			markisen.setLocation((col * 22)-22, (row *20)-40);
			markisen.setVisible(true);
			break;
		case "Hook":
			hook.setLocation((col * 22)-22, (row *20)-40);
			hook.setVisible(true);
			break;
		default:
			break;
		}
	}
	
	
	public void paintCharacter(int newRow, int newCol, int oldRow, int oldCol) {
		boardArray[newRow][newCol].setBackground(Color.RED);
		boardArray[newRow][newCol].repaint();
		String colorOfTile = client.getTile(oldRow, oldCol);
		switch (colorOfTile) {
		case "Ground":
			boardArray[oldRow][oldCol].setBackground(Color.GRAY);
			break;
		case "Water":
			boardArray[oldRow][oldCol].setBackground(Color.BLUE);
			break;
		case "Jungle":
			boardArray[oldRow][oldCol].setBackground(Color.GREEN);
			break;
		case "BLACK":
			boardArray[oldRow][oldCol].setBackground(Color.BLACK);
			break;
		case "ORANGE":
			boardArray[oldRow][oldCol].setBackground(Color.ORANGE);
			break;
			
		case "Special":
			boardArray[oldRow][oldCol].setBackground(Color.DARK_GRAY);
			break;

		default:
			break;
		}
	}

	/**
	 * method to enable or disable all buttons
	 * 
	 * @param state
	 *            boolean
	 */
	public void enableButtons(String buttons) {
		if(buttons.equals("disable all")){
			bMove.setEnabled(false);
			bShoot.setEnabled(false);
			bUp.setEnabled(false);
			bDown.setEnabled(false);
			bLeft.setEnabled(false);
			bRight.setEnabled(false);
			bEndTurn.setEnabled(false);
		}
		if (buttons.equals("update")) {
			bMove.setEnabled(true);
			bShoot.setEnabled(false);
			bUp.setEnabled(false);
			bDown.setEnabled(false);
			bLeft.setEnabled(false);
			bRight.setEnabled(false);
		} else if (buttons.equals("move")) {
			bMove.setEnabled(false);
			bUp.setEnabled(true);
			bDown.setEnabled(true);
			bLeft.setEnabled(true);
			bRight.setEnabled(true);
			bJump.setEnabled(true);
		} else if(buttons.equals("disable move")){
			bUp.setEnabled(false);
			bDown.setEnabled(false);
			bLeft.setEnabled(false);
			bRight.setEnabled(false);
			bEndTurn.setEnabled(true);
		} else if (buttons.equals("shoot")) {
			bShoot.setEnabled(true);
		} else if (buttons.equals("disable shoot")) {
			bShoot.setEnabled(false);
		} else if (buttons.equals("disconnect")) {
			bDisconnect.setEnabled(false);
			bConnect.setEnabled(true);
		} else if (buttons.equals("end turn")) {
			bEndTurn.setEnabled(true);
			bMove.setEnabled(false);
			bShoot.setEnabled(false);
			bUp.setEnabled(false);
			bDown.setEnabled(false);
			bLeft.setEnabled(false);
			bRight.setEnabled(false);
		}

		// bUp.setEnabled(state);
		// bDown.setEnabled(state);
		// bLeft.setEnabled(state);
		// bRight.setEnabled(state);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == bConnect) {
			client.sendUsername(username.getText());
			frame.setTitle(username.getText());
			client.connect(serverIp.getText(), Integer.parseInt(serverPort.getText()));
			bConnect.setEnabled(false);
		}
		if (e.getSource() == bDisconnect) {
			enableButtons("disconnect");
			client.disconnect();
		}
		if (e.getSource() == bClose) {
			System.exit(0);
		}
		
		if (e.getSource() == bMove) {
			frame.requestFocus();
			enableButtons("move");
			client.throwDice();
		}
		if(e.getSource() == bJump) {
			System.exit(0);
		}
		if (e.getSource() == bShoot) {
			client.shoot();
		}
		if (e.getSource() == bEndTurn) {
			client.endTurn();
			enableButtons("disable all");
		}
		if (e.getSource() == bLeft) {
			frame.requestFocus();	
			client.moveCharacter(username.getText(), "Left");
			System.out.println("ClientFrame: Left");
		}
		if (e.getSource() == bRight) {
			frame.requestFocus();
			client.moveCharacter(username.getText(), "Right");
			System.out.println("ClientFrame: Right");
		}
		if (e.getSource() == bUp) {
			frame.requestFocus();
			client.moveCharacter(username.getText(), "Up");
			System.out.println("ClientFrame: Up");
		}
		if (e.getSource() == bDown) {
			frame.requestFocus();
			client.moveCharacter(username.getText(), "Down");
			System.out.println("ClientFrame: Down");
		}
	}
	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		if(code == KeyEvent.VK_UP){
			client.moveCharacter(username.getText(), "Up");
		}
		if(code == KeyEvent.VK_DOWN){
			client.moveCharacter(username.getText(), "Down");
		}
		if(code == KeyEvent.VK_LEFT){
			client.moveCharacter(username.getText(), "Left");
		}
		if(code == KeyEvent.VK_RIGHT){
			client.moveCharacter(username.getText(), "Right");
		}
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}

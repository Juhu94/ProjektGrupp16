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
public class ClientFrame extends JPanel implements ActionListener, ViewerListener,KeyListener, MouseListener {

	private JPanel panel = new JPanel();
	private JPanel leftPanel = new JPanel(new BorderLayout());
	private JPanel leftGridPanel = new JPanel(new GridLayout(5, 1));
	private JPanel rightPanel = new JPanel(new BorderLayout());
	private JPanel centerPanel = new JPanel();
	private JPanel flowPanel = new JPanel();
	private JPanel inputPanel = new JPanel(new BorderLayout(50, 0));
	private JPanel inputLeftPanel = new JPanel(new FlowLayout());
	private JPanel inputRightPanel = new JPanel(new GridBagLayout());
	private JPanel inputMiddlePanel = new JPanel(new GridBagLayout());
	private JPanel charChoices = new JPanel();
	private JLayeredPane mapPane = new JLayeredPane();
	private JLabel mapLabel = new JLabel();
	
	private JLabel svullo = new JLabel(new ImageIcon(new ImageIcon("images/Svullo.png").getImage().getScaledInstance(22, 40, Image.SCALE_SMOOTH)));
	private JLabel tjoPang = new JLabel(new ImageIcon(new ImageIcon("images/TjoPang.png").getImage().getScaledInstance(22, 40, Image.SCALE_SMOOTH)));
	private JLabel theRat = new JLabel(new ImageIcon(new ImageIcon("images/TheRat.png").getImage().getScaledInstance(22, 40, Image.SCALE_SMOOTH)));
	private JLabel hannibal = new JLabel(new ImageIcon(new ImageIcon("images/Hannibal.png").getImage().getScaledInstance(22, 40, Image.SCALE_SMOOTH)));
	private JLabel markisen = new JLabel(new ImageIcon(new ImageIcon("images/Markisen.png").getImage().getScaledInstance(22, 40, Image.SCALE_SMOOTH)));
	private JLabel hook = new JLabel(new ImageIcon(new ImageIcon("images/Hook.png").getImage().getScaledInstance(22, 40, Image.SCALE_SMOOTH)));
	
	private JLabel svulloBtn = new JLabel(new ImageIcon("images/Svullo.png"));
	private JLabel tjoPangBtn = new JLabel(new ImageIcon("images/TjoPang.png"));
	private JLabel theRatBtn = new JLabel(new ImageIcon("images/TheRat.png"));
	private JLabel hannibalBtn = new JLabel(new ImageIcon("images/Hannibal.png"));
	private JLabel markisenBtn = new JLabel(new ImageIcon("images/Markisen.png"));
	private JLabel hookBtn = new JLabel(new ImageIcon("images/Hook.png"));
	
	private JButton chooseChar = new JButton("Connect");

	private DefaultListModel model = new DefaultListModel();
	private JList listUsers = new JList(model);
	private JTextArea infoArea = new JTextArea("För att ansluta till en server:\n"
			+ "Skriv in serverns ip och port ovanför.\n" + "Skriv också in ett användarnamn du vill använda\n"
			+ "----------------------------------------------------------------------\n"
			+ "Antal steg:   \n"
			+ "----------------------------------------------------------------------\n");

	private JTextField serverIp = new JTextField("");
	private JTextField serverPort = new JTextField("3520");
	private JTextField username = new JTextField("");
	private JTextArea character = new JTextArea("");

	private JButton bConnect = new JButton("Choose caracter");
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
	private JFrame chooseCharFrame = new JFrame("Choose character");

	private ExtendedJLabel[][] boardArray = new ExtendedJLabel[41][47];

	private GameClient client;
	

	public ClientFrame(GameClient client) {
		this.client = client;
		client.addListeners(this);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLayout(new BorderLayout());
		flowPanel.setLayout(new FlowLayout());
		
		chooseCharFrame.setVisible(false);
		chooseCharFrame.setLayout(new BorderLayout());
		charChoices.setLayout(new FlowLayout());
		charChoices.setPreferredSize(new Dimension(300, 100));
		chooseCharFrame.add(charChoices, BorderLayout.CENTER);
		charChoices.add(svulloBtn);
		charChoices.add(tjoPangBtn);
		charChoices.add(theRatBtn);
		charChoices.add(markisenBtn);
		charChoices.add(hannibalBtn);
		charChoices.add(hookBtn);
		chooseCharFrame.add(chooseChar, BorderLayout.SOUTH);
		
		svulloBtn.setBounds(0, 0, 44, 80);
		tjoPangBtn.setBounds(44, 0, 44, 80);
		theRatBtn.setBounds(88, 0, 44, 80);
		markisenBtn.setBounds(132, 0, 44, 80);
		hannibalBtn.setBounds(176, 0, 44, 80);
		hookBtn.setBounds(220, 0, 44, 80);
		
		svulloBtn.addMouseListener(this);
		tjoPangBtn.addMouseListener(this);
		theRatBtn.addMouseListener(this);
		markisenBtn.addMouseListener(this);
		hannibalBtn.addMouseListener(this);
		hookBtn.addMouseListener(this);
		chooseChar.addActionListener(this);
		
		
		mapPane.setPreferredSize(new Dimension(1034, 820));
		flowPanel.add(mapPane);
		mapLabel.setIcon(new ImageIcon("images/mapNewConcept.png"));
		mapPane.add(centerPanel, new Integer(1));
		mapPane.add(mapLabel, new Integer(2));
		
		mapPane.add(svullo, new Integer(3));
		mapPane.add(tjoPang, new Integer(4));
		mapPane.add(theRat, new Integer(5));
		mapPane.add(markisen, new Integer(6));
		mapPane.add(hannibal, new Integer(7));
		mapPane.add(hook, new Integer(8));
		
		centerPanel.setBounds(0, 0, 1034, 820);
		mapLabel.setBounds(0, 0, 1034, 820);
		
		svullo.setVisible(false);
		tjoPang.setVisible(false);
		theRat.setVisible(false);
		markisen.setVisible(false);
		hannibal.setVisible(false);
		hook.setVisible(false);
		
		theRat.setBounds(0, 0, 22, 40);
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
		character.setBorder(BorderFactory.createTitledBorder("Character"));
		character.setEditable(false);
		infoArea.setEditable(false);
		infoArea.setBorder(BorderFactory.createTitledBorder("Info ruta"));
		leftGridPanel.add(serverIp);
		leftGridPanel.add(serverPort);
		leftGridPanel.add(username);
		leftGridPanel.add(character);
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
		
		chooseCharFrame.pack();
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
			svullo.setLocation((col * 22)-4, (row *20)-25);
			svullo.setVisible(true);
			break;
		case "TjoPang":
			tjoPang.setLocation((col * 22)-4, (row *20)-25);
			tjoPang.setVisible(true);
			break;
		case "TheRat":
			theRat.setLocation((col * 22)-4, (row *20)-25);
			theRat.setVisible(true);
			break;
		case "Hannibal":
			hannibal.setLocation((col * 22)-4, (row *20)-25);
			hannibal.setVisible(true);
			break;
		case "Markisen":
			markisen.setLocation((col * 22)-4, (row *20)-25);
			markisen.setVisible(true);
			break;
		case "Hook":
			hook.setLocation((col * 22)-4, (row *20)-25);
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
			frame.requestFocus();
			bMove.setEnabled(true);
			bShoot.setEnabled(false);
			bJump.setEnabled(false);
			bUp.setEnabled(false);
			bDown.setEnabled(false);
			bLeft.setEnabled(false);
			bRight.setEnabled(false);
		} else if (buttons.equals("move")) {
			bMove.setEnabled(false);
			bJump.setEnabled(false);
			bUp.setEnabled(true);
			bDown.setEnabled(true);
			bLeft.setEnabled(true);
			bRight.setEnabled(true);
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
		} else if (buttons.equals("jump")){
			bJump.setEnabled(true);
		} else if (buttons.equals("disable jump")){
			bJump.setEnabled(false);
		}

		// bUp.setEnabled(state);
		// bDown.setEnabled(state);
		// bLeft.setEnabled(state);
		// bRight.setEnabled(state);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == bConnect) {
			client.setUsername(username.getText());
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
			client.jump();
		}
		if (e.getSource() == bShoot) {
			client.shoot();
		}
		if (e.getSource() == bEndTurn) {
			frame.requestFocus();
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
		if (e.getSource() == chooseChar){
			
			if(!character.getText().equals("")){
				client.setCharacter(character.getText());
				chooseCharFrame.setVisible(false);
			}	
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
		if(code == KeyEvent.VK_ENTER){
			client.endTurn();
		}
		if(code == KeyEvent.VK_SPACE){
			if(bMove.isEnabled()) {
				enableButtons("move");
				client.throwDice();
			}
			else if(bEndTurn.isEnabled()){
				client.endTurn();
				enableButtons("disable all");
			}
		}
		
	}

	
	public void keyReleased(KeyEvent arg0) {
		
		
	}

	
	public void keyTyped(KeyEvent arg0) {
		
		
	}
	
	
	public void mouseClicked(MouseEvent e) {
		JLabel source = (JLabel)e.getSource();
		
		if(source.equals(svulloBtn)){
			if(svulloBtn.isEnabled()){
//				svulloBtn.requestFocus();
				character.setText("Svullo");
			}
		}
		if(source.equals(theRatBtn)){
			if(theRatBtn.isEnabled()){
//				theRatBtn.requestFocus();
				character.setText("TheRat");
			}
		}
		if(source.equals(tjoPangBtn)){
			if(tjoPangBtn.isEnabled()){
//				tjoPangBtn.requestFocus();
				character.setText("TjoPang");
			}
		}
		if(source.equals(markisenBtn)){
			if(markisenBtn.isEnabled()){
//				markisenBtn.requestFocus();
				character.setText("Markisen");
			}
		}
		if(source.equals(hannibalBtn)){
			if(hannibalBtn.isEnabled()){
//				hannibalBtn.requestFocus();
				character.setText("Hannibal");
			}
		}
		if(source.equals(hookBtn)){
			if(hookBtn.isEnabled()){
//				hookBtn.requestFocus();
				character.setText("Hook");
			}
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void chooseCharFrame() {
		chooseCharFrame.setVisible(true);
		
	}

	@Override
	public void updateChooseCharFrame(boolean svullo, boolean tjoPang, boolean theRat, boolean markisen,
			boolean hannibal, boolean hook) {
		
		svulloBtn.setEnabled(svullo);
		tjoPangBtn.setEnabled(tjoPang);
		theRatBtn.setEnabled(theRat);
		markisenBtn.setEnabled(markisen);
		hannibalBtn.setEnabled(hannibal);
		hookBtn.setEnabled(hook);
				
		System.out.println("GUI: " + svullo);
		System.out.println("GUI: " + tjoPang);
		System.out.println("GUI: uppdaterat characterfönster" + username.getText());
		
	}

	
}
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
import javax.swing.SwingConstants;

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
	private JPanel targetChoices = new JPanel();
	
	private JLayeredPane mapPane = new JLayeredPane();
	private JLabel mapLabel = new JLabel();
	
	private ImageIcon svulloIcon = new ImageIcon(new ImageIcon("images/Svullo.png").getImage().getScaledInstance(22, 40, Image.SCALE_SMOOTH));
	private ImageIcon tjoPangIcon = new ImageIcon(new ImageIcon("images/TjoPang.png").getImage().getScaledInstance(22, 40, Image.SCALE_SMOOTH));
	private ImageIcon theRatIcon = new ImageIcon(new ImageIcon("images/TheRat.png").getImage().getScaledInstance(22, 40, Image.SCALE_SMOOTH));
	private ImageIcon hannibalIcon = new ImageIcon(new ImageIcon("images/Hannibal.png").getImage().getScaledInstance(22, 40, Image.SCALE_SMOOTH));
	private ImageIcon markisenIcon = new ImageIcon(new ImageIcon("images/Markisen.png").getImage().getScaledInstance(22, 40, Image.SCALE_SMOOTH));
	private ImageIcon hookIcon = new ImageIcon(new ImageIcon("images/Hook.png").getImage().getScaledInstance(22, 40, Image.SCALE_SMOOTH));
	private ImageIcon treasureIcon = new ImageIcon(new ImageIcon("images/Treasure.png").getImage().getScaledInstance(22, 20, Image.SCALE_SMOOTH));
	
	private JLabel svullo = new JLabel(svulloIcon);
	private JLabel tjoPang = new JLabel(tjoPangIcon);
	private JLabel theRat = new JLabel(theRatIcon);
	private JLabel hannibal = new JLabel(hannibalIcon);
	private JLabel markisen = new JLabel(markisenIcon);
	private JLabel hook = new JLabel(hookIcon);
	private JLabel treasure = new JLabel(treasureIcon);
	
	private ImageIcon svulloSleeping = new ImageIcon(new ImageIcon("images/WoundedSvullo.png").getImage().getScaledInstance(22, 40, Image.SCALE_SMOOTH));
	private ImageIcon tjoPangSleeping = new ImageIcon(new ImageIcon("images/WoundedTjoPang.png").getImage().getScaledInstance(22, 40, Image.SCALE_SMOOTH));
	private ImageIcon theRatSleeping = new ImageIcon(new ImageIcon("images/WoundedTheRat.png").getImage().getScaledInstance(22, 40, Image.SCALE_SMOOTH));
	private ImageIcon hannibalSleeping = new ImageIcon(new ImageIcon("images/WoundedHannibal.png").getImage().getScaledInstance(22, 40, Image.SCALE_SMOOTH));
	private ImageIcon markisenSleeping = new ImageIcon(new ImageIcon("images/WoundedMarkisen.png").getImage().getScaledInstance(22, 40, Image.SCALE_SMOOTH));
	private ImageIcon hookSleeping = new ImageIcon(new ImageIcon("images/WoundedHook.png").getImage().getScaledInstance(22, 40, Image.SCALE_SMOOTH));
	
	private ImageIcon svulloDrowning = new ImageIcon(new ImageIcon("images/DrowningSvullo.png").getImage().getScaledInstance(22, 40, Image.SCALE_SMOOTH));
	private ImageIcon tjoPangDrowning = new ImageIcon(new ImageIcon("images/DrowningTjoPang.png").getImage().getScaledInstance(22, 40, Image.SCALE_SMOOTH));
	private ImageIcon theRatDrowning = new ImageIcon(new ImageIcon("images/DrowningTheRat.png").getImage().getScaledInstance(22, 40, Image.SCALE_SMOOTH));
	private ImageIcon hannibalDrowning = new ImageIcon(new ImageIcon("images/DrowningHannibal.png").getImage().getScaledInstance(22, 40, Image.SCALE_SMOOTH));
	private ImageIcon markisenDrowning = new ImageIcon(new ImageIcon("images/DrowningMarkisen.png").getImage().getScaledInstance(22, 40, Image.SCALE_SMOOTH));
	private ImageIcon hookDrowning = new ImageIcon(new ImageIcon("images/DrowningHook.png").getImage().getScaledInstance(22, 40, Image.SCALE_SMOOTH));
	
	private JLabel svulloBtn = new JLabel(new ImageIcon("images/Svullo.png"));
	private JLabel tjoPangBtn = new JLabel(new ImageIcon("images/TjoPang.png"));
	private JLabel theRatBtn = new JLabel(new ImageIcon("images/TheRat.png"));
	private JLabel hannibalBtn = new JLabel(new ImageIcon("images/Hannibal.png"));
	private JLabel markisenBtn = new JLabel(new ImageIcon("images/Markisen.png"));
	private JLabel hookBtn = new JLabel(new ImageIcon("images/Hook.png"));
	
	private JLabel svulloTargetBtn = new JLabel(new ImageIcon("images/Svullo.png"));
	private JLabel tjoPangTargetBtn = new JLabel(new ImageIcon("images/TjoPang.png"));
	private JLabel theRatTargetBtn = new JLabel(new ImageIcon("images/TheRat.png"));
	private JLabel hannibalTargetBtn = new JLabel(new ImageIcon("images/Hannibal.png"));
	private JLabel markisenTargetBtn = new JLabel(new ImageIcon("images/Markisen.png"));
	private JLabel hookTargetBtn = new JLabel(new ImageIcon("images/Hook.png"));
	
	private JButton chooseChar = new JButton("Connect");
	private JButton shootTarget = new JButton("shoot");

	private DefaultListModel model = new DefaultListModel();
	private JList listUsers = new JList(model);
	private JTextArea infoArea = new JTextArea("För att ansluta till en server:\n"
			+ "Skriv in serverns ip och port ovanför.\n" + "Skriv också in ett användarnamn du vill använda\n"
			+ "----------------------------------------------------------------------\n"
			+ "Antal steg:   \n"
			+ "----------------------------------------------------------------------\n"
			+ "Antal kartbitar:  \n"
			+ "----------------------------------------------------------------------\n"
			+ "Skatten befinner sig hos:                     \n"
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
	private JButton bMove = new JButton("Move (Q)");
	private JButton bShoot = new JButton("Shoot (E)");
	private JButton bEndTurn = new JButton("End turn (R)");
	private JButton bJump = new JButton("Jump (W)");

	private JFrame frame = new JFrame("Client");
	private JFrame chooseCharFrame = new JFrame("Choose character");
	private JFrame chooseTarget = new JFrame("Choose target");

	private ExtendedJLabel[][] boardArray = new ExtendedJLabel[41][47];

	private GameClient client;
	
	private String target = "";
	

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
		
		svulloTargetBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
		tjoPangTargetBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
		theRatTargetBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
		markisenTargetBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
		hannibalTargetBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
		hookTargetBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
		
		chooseTarget.setVisible(false);
		chooseTarget.setLayout(new BorderLayout());
		chooseTarget.setBackground(Color.WHITE);
		targetChoices.setLayout(new FlowLayout());
		targetChoices.setPreferredSize(new Dimension(300, 100));
		chooseTarget.add(targetChoices, BorderLayout.CENTER);
		chooseTarget.add(shootTarget, BorderLayout.SOUTH);
		targetChoices.add(svulloTargetBtn);
		targetChoices.add(tjoPangTargetBtn);
		targetChoices.add(theRatTargetBtn);
		targetChoices.add(markisenTargetBtn);
		targetChoices.add(hannibalTargetBtn);
		targetChoices.add(hookTargetBtn);
		
		shootTarget.addActionListener(this);
		
		svulloBtn.setBounds(0, 0, 44, 80);
		tjoPangBtn.setBounds(44, 0, 44, 80);
		theRatBtn.setBounds(88, 0, 44, 80);
		markisenBtn.setBounds(132, 0, 44, 80);
		hannibalBtn.setBounds(176, 0, 44, 80);
		hookBtn.setBounds(220, 0, 44, 80);
		
		svulloTargetBtn.addMouseListener(this);
		tjoPangTargetBtn.addMouseListener(this);
		theRatTargetBtn.addMouseListener(this);
		markisenTargetBtn.addMouseListener(this);
		hannibalTargetBtn.addMouseListener(this);
		hookTargetBtn.addMouseListener(this);
		
		svulloBtn.addMouseListener(this);
		tjoPangBtn.addMouseListener(this);
		theRatBtn.addMouseListener(this);
		markisenBtn.addMouseListener(this);
		hannibalBtn.addMouseListener(this);
		hookBtn.addMouseListener(this);
		chooseChar.addActionListener(this);
		
		flowPanel.setBackground(Color.BLUE);
		
		mapPane.setPreferredSize(new Dimension(1034, 820));
		flowPanel.add(mapPane);
		mapLabel.setIcon(new ImageIcon("images/map.png"));
		mapPane.add(centerPanel, new Integer(1));
		mapPane.add(mapLabel, new Integer(2));
		
		mapPane.add(treasure, new Integer(3));
		mapPane.add(svullo, new Integer(4));
		mapPane.add(tjoPang, new Integer(5));
		mapPane.add(theRat, new Integer(6));
		mapPane.add(markisen, new Integer(7));
		mapPane.add(hannibal, new Integer(8));
		mapPane.add(hook, new Integer(9));
		
		centerPanel.setBounds(0, 0, 1034, 820);
		mapLabel.setBounds(0, 0, 1034, 820);
		
		svullo.setVisible(false);
		tjoPang.setVisible(false);
		theRat.setVisible(false);
		markisen.setVisible(false);
		hannibal.setVisible(false);
		hook.setVisible(false);
		treasure.setVisible(false);
		
		theRat.setBounds(0, 0, 22, 40);
		tjoPang.setBounds(0, 0, 22, 40);
		svullo.setBounds(0, 0, 22, 40);
		markisen.setBounds(0, 0, 22, 40);
		hannibal.setBounds(0, 0, 22, 40);
		hook.setBounds(0, 0, 22, 40);
		treasure.setBounds(0, 0, 22, 20);

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

//		centerPanel.setLayout(new GridLayout(41, 47, 0, 0));
//		String s;
//		for (int i = 0; i < 41; i++) {
//			for (int j = 0; j < 47; j++) {
//				boardArray[i][j] = new ExtendedJLabel(i, j, Color.RED);
				//------------------------------------------------------TEMP
//				s = client.getTile(i, j);
//				switch (s) {
//				case "Ground":
//					boardArray[i][j].setBackground(Color.GRAY);
//					break;
//				case "Water":
//					boardArray[i][j].setBackground(Color.BLUE);
//					break;
//				case "Jungle":
//					boardArray[i][j].setBackground(Color.GREEN);
//					break;
//				case "BLACK":
//					boardArray[i][j].setBackground(Color.BLACK);
//					break;
//				case "ORANGE":
//					boardArray[i][j].setBackground(Color.ORANGE);
//					break;
//					
//				case "Special":
//					boardArray[i][j].setBackground(Color.DARK_GRAY);
//					break;
//
//				default:
//					break;
//				}
				//------------------------------------------------------
				//boardArray[i][j].setBackground(Color.GRAY);
//				boardArray[i][j].setHorizontalAlignment(JLabel.CENTER);
//				boardArray[i][j].setOpaque(true);
//				boardArray[i][j].setPreferredSize(new Dimension(22, 20));
//				centerPanel.add(boardArray[i][j]);
//			}
//		}
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
		frame.addMouseListener(this);
		frame.setFocusable(true);
		frame.setLocationRelativeTo(null);
		
		chooseCharFrame.pack();
		chooseTarget.pack();
	}
	
	public void removeConnectedUsers(){
		model.clear();
	}
	
	public void setIconSleep(String name, boolean sleeping){
		
		switch (name) {
		case "Svullo":
			if(!sleeping){
				svullo.setIcon(svulloSleeping);
			} else{
				svullo.setIcon(svulloIcon);
			}
//			svullo.setEnabled(sleeping);
			svullo.repaint();
			break;
		case "TjoPang":
			if(!sleeping){
				tjoPang.setIcon(tjoPangSleeping);
			} else{
				tjoPang.setIcon(tjoPangIcon);
			}
//			tjoPang.setEnabled(sleeping);
			tjoPang.repaint();
			break;
		case "TheRat":
			if(!sleeping){
				theRat.setIcon(theRatSleeping);
			} else{
				theRat.setIcon(theRatIcon);
			}
//			theRat.setEnabled(sleeping);
			theRat.repaint();
			break;
		case "Hannibal":
			if(!sleeping){
				hannibal.setIcon(hannibalSleeping);
			} else{
				hannibal.setIcon(hannibalIcon);
			}
//			hannibal.setEnabled(sleeping);
			hannibal.repaint();
			break;
		case "Markisen":
			if(!sleeping){
				markisen.setIcon(markisenSleeping);
			} else{
				markisen.setIcon(markisenIcon);
			}
//			markisen.setEnabled(sleeping);
			markisen.repaint();
			break;
		case "Hook":
			if(!sleeping){
				hook.setIcon(hookSleeping);
			} else{
				hook.setIcon(hookIcon);
			}
//			hook.setEnabled(sleeping);
			hook.repaint();
			break;
		case "Treasure":
			if(!sleeping){
				treasure.setVisible(false);
			} else{
				treasure.setVisible(true);
			}
			break;
		default:
			break;
		}
	}
	
	public void setWaterIcon(String name){
		
		switch (name) {
		case "Svullo":
			svullo.setIcon(svulloDrowning);
			svullo.repaint();
			break;
		case "TjoPang":
			tjoPang.setIcon(tjoPangDrowning);
			tjoPang.repaint();
			break;
		case "TheRat":
			
			theRat.setIcon(theRatDrowning);
			theRat.repaint();
			break;
		case "Hannibal":
			hannibal.setIcon(hannibalDrowning);
			hannibal.repaint();
			break;
		case "Markisen":
			markisen.setIcon(markisenDrowning);
			markisen.repaint();
			break;
		case "Hook":
			hook.setIcon(hookDrowning);
			hook.repaint();
			break;
		default:
			break;
		}
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

	public void updateInfoRutaSteps(String text) {
		infoArea.replaceRange(text, 190, 203);
		
	}
	public void updateInfoRutaMap(String text) {
		infoArea.replaceRange(text, 292, 293);
	}
	
	public void updateInfoRutaTreasure(String text) {
		infoArea.replaceRange(text, 392, 412);
	}
	
	public void moveIcon(String name, int row, int col, boolean visible){
		
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
		case "Treasure":
			treasure.setLocation((col * 22), (row * 20));
			treasure.setVisible(visible);
			if(visible){
				System.out.println("ClientFrame: Visar skatten för " + client.getCharacter() + "!!!");
			}
			
			break;
		default:
			break;
		}
	}
	
	
	public void paintCharacter(int newRow, int newCol, int oldRow, int oldCol) {
//		boardArray[newRow][newCol].setBackground(Color.RED);
//		boardArray[newRow][newCol].repaint();
//		String colorOfTile = client.getTile(oldRow, oldCol);
//		switch (colorOfTile) {
//		case "Ground":
//			boardArray[oldRow][oldCol].setBackground(Color.GRAY);
//			break;
//		case "Water":
//			boardArray[oldRow][oldCol].setBackground(Color.BLUE);
//			break;
//		case "Jungle":
//			boardArray[oldRow][oldCol].setBackground(Color.GREEN);
//			break;
//		case "BLACK":
//			boardArray[oldRow][oldCol].setBackground(Color.BLACK);
//			break;
//		case "ORANGE":
//			boardArray[oldRow][oldCol].setBackground(Color.ORANGE);
//			break;
//			
//		case "Special":
//			boardArray[oldRow][oldCol].setBackground(Color.DARK_GRAY);
//			break;
//
//		default:
//			break;
//		}
	}
	
	public void setAvailableTarget(String character, String username){
		switch(character){
		case "Svullo":
			svulloTargetBtn.setEnabled(true);
			svulloTargetBtn.setText(username);
			chooseTarget.pack();
			break;
		case "TjoPang":
			tjoPangTargetBtn.setEnabled(true);
			tjoPangTargetBtn.setText(username);
			chooseTarget.pack();
			break;
		case "TheRat":
			theRatTargetBtn.setEnabled(true);
			theRatTargetBtn.setText(username);
			chooseTarget.pack();
			break;
		case "Hannibal":
			hannibalTargetBtn.setEnabled(true);
			hannibalTargetBtn.setText(username);
			chooseTarget.pack();
			break;
		case "Markisen":
			markisenTargetBtn.setEnabled(true);
			markisenTargetBtn.setText(username);
			chooseTarget.pack();
			break;
		case "Hook":
			hookTargetBtn.setEnabled(true);
			hookTargetBtn.setText(username);
			chooseTarget.pack();
			break;
		}
	}
	public void getTarget(){
		chooseTarget.setVisible(true);		
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
			bShoot.setEnabled(false);
			bUp.setEnabled(true);
			bDown.setEnabled(true);
			bLeft.setEnabled(true);
			bRight.setEnabled(true);
		} else if(buttons.equals("disable move")){
			bMove.setEnabled(false);
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
			
		}else if(buttons.equals("time out")){
			bEndTurn.setEnabled(true);
		}

		// bUp.setEnabled(state);
		// bDown.setEnabled(state);
		// bLeft.setEnabled(state);
		// bRight.setEnabled(state);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == bConnect) {
			if (!username.getText().equals("")) {
				client.setUsername(username.getText());
				frame.setTitle(username.getText());
				client.connect(serverIp.getText(), Integer.parseInt(serverPort.getText()));
				bConnect.setEnabled(false);
			}
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
			svulloTargetBtn.setEnabled(false);
			tjoPangTargetBtn.setEnabled(false);
			theRatTargetBtn.setEnabled(false);
			markisenTargetBtn.setEnabled(false);
			hannibalTargetBtn.setEnabled(false);
			hookTargetBtn.setEnabled(false);
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
		if (e.getSource() == shootTarget) {
			if (!target.equals("")) {
				chooseTarget.setVisible(false);
				client.shoot(target);
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
//		if(code == KeyEvent.VK_ENTER){
//			client.endTurn();
//		}
//		if(code == KeyEvent.VK_SPACE){
//			if(bMove.isEnabled()) {
//				enableButtons("move");
//				client.throwDice();
//			}
//			else if(bEndTurn.isEnabled()){
//				client.endTurn();
//				enableButtons("disable all");
//			}
//		}
		if(code == KeyEvent.VK_Q){
			if(bMove.isEnabled()) {
				enableButtons("move");
				client.throwDice();
			}
		}
		if(code == KeyEvent.VK_W){
			if(bJump.isEnabled()) {
				client.jump();
			}
		}
		if(code == KeyEvent.VK_E){
			if(bShoot.isEnabled()) {
				svulloTargetBtn.setEnabled(false);
				tjoPangTargetBtn.setEnabled(false);
				theRatTargetBtn.setEnabled(false);
				markisenTargetBtn.setEnabled(false);
				hannibalTargetBtn.setEnabled(false);
				hookTargetBtn.setEnabled(false);
				
				client.shoot();
			}
		}
		if(code == KeyEvent.VK_R){
			if(bEndTurn.isEnabled()) {
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
		JLabel source = new JLabel();
		JFrame mainFrame = new JFrame();
		
		if(e.getSource() instanceof JLabel){
			source = (JLabel) e.getSource();
		}else if(e.getSource() instanceof JFrame){
			mainFrame = (JFrame)e.getSource();
		}

		if (source.equals(svulloBtn)) {
			if (svulloBtn.isEnabled()) {
				character.setText("Svullo");
			}
		}
		if (source.equals(theRatBtn)) {
			if (theRatBtn.isEnabled()) {
				character.setText("TheRat");
			}
		}
		if (source.equals(tjoPangBtn)) {
			if (tjoPangBtn.isEnabled()) {
				character.setText("TjoPang");
			}
		}
		if (source.equals(markisenBtn)) {
			if (markisenBtn.isEnabled()) {
				character.setText("Markisen");
			}
		}
		if (source.equals(hannibalBtn)) {
			if (hannibalBtn.isEnabled()) {
				character.setText("Hannibal");
			}
		}
		if (source.equals(hookBtn)) {
			if (hookBtn.isEnabled()) {
				character.setText("Hook");
			}
		}

		if (source.equals(svulloTargetBtn)) {
			if (svulloTargetBtn.isEnabled()) {
				svulloTargetBtn.setBackground(Color.RED);
				theRatTargetBtn.setBackground(Color.WHITE);
				tjoPangTargetBtn.setBackground(Color.WHITE);
				markisenTargetBtn.setBackground(Color.WHITE);
				hannibalTargetBtn.setBackground(Color.WHITE);
				hookTargetBtn.setBackground(Color.WHITE);
				svulloTargetBtn.repaint();
				theRatTargetBtn.repaint();
				tjoPangTargetBtn.repaint();
				markisenTargetBtn.repaint();
				hannibalTargetBtn.repaint();
				hookTargetBtn.repaint();
				
				this.target = svulloTargetBtn.getText();
				System.out.println(target);
			}
		}
		if (source.equals(theRatTargetBtn)) {
			if (theRatTargetBtn.isEnabled()) {
				svulloTargetBtn.setBackground(Color.WHITE);
				theRatTargetBtn.setBackground(Color.RED);
				tjoPangTargetBtn.setBackground(Color.WHITE);
				markisenTargetBtn.setBackground(Color.WHITE);
				hannibalTargetBtn.setBackground(Color.WHITE);
				hookTargetBtn.setBackground(Color.WHITE);
				svulloTargetBtn.repaint();
				theRatTargetBtn.repaint();
				tjoPangTargetBtn.repaint();
				markisenTargetBtn.repaint();
				hannibalTargetBtn.repaint();
				hookTargetBtn.repaint();
				this.target = theRatTargetBtn.getText();
				System.out.println(target);
			}
		}
		if (source.equals(tjoPangTargetBtn)) {
			if (tjoPangTargetBtn.isEnabled()) {
				svulloTargetBtn.setBackground(Color.WHITE);
				theRatTargetBtn.setBackground(Color.WHITE);
				tjoPangTargetBtn.setBackground(Color.RED);
				markisenTargetBtn.setBackground(Color.WHITE);
				hannibalTargetBtn.setBackground(Color.WHITE);
				hookTargetBtn.setBackground(Color.WHITE);
				svulloTargetBtn.repaint();
				theRatTargetBtn.repaint();
				tjoPangTargetBtn.repaint();
				markisenTargetBtn.repaint();
				hannibalTargetBtn.repaint();
				hookTargetBtn.repaint();
				this.target = tjoPangTargetBtn.getText();
				System.out.println(target);
			}
		}
		if (source.equals(markisenTargetBtn)) {
			if (markisenTargetBtn.isEnabled()) {
				svulloTargetBtn.setBackground(Color.WHITE);
				theRatTargetBtn.setBackground(Color.WHITE);
				tjoPangTargetBtn.setBackground(Color.WHITE);
				markisenTargetBtn.setBackground(Color.RED);
				hannibalTargetBtn.setBackground(Color.WHITE);
				hookTargetBtn.setBackground(Color.WHITE);
				svulloTargetBtn.repaint();
				theRatTargetBtn.repaint();
				tjoPangTargetBtn.repaint();
				markisenTargetBtn.repaint();
				hannibalTargetBtn.repaint();
				hookTargetBtn.repaint();
				this.target = markisenTargetBtn.getText();
				System.out.println(target);
			}
		}
		if (source.equals(hannibalTargetBtn)) {
			if (hannibalTargetBtn.isEnabled()) {
				svulloTargetBtn.setBackground(Color.WHITE);
				theRatTargetBtn.setBackground(Color.WHITE);
				tjoPangTargetBtn.setBackground(Color.WHITE);
				markisenTargetBtn.setBackground(Color.WHITE);
				hannibalTargetBtn.setBackground(Color.RED);
				hookTargetBtn.setBackground(Color.WHITE);
				svulloTargetBtn.repaint();
				theRatTargetBtn.repaint();
				tjoPangTargetBtn.repaint();
				markisenTargetBtn.repaint();
				hannibalTargetBtn.repaint();
				hookTargetBtn.repaint();
				this.target = hannibalTargetBtn.getText();
				System.out.println(target);
			}
		}
		if (source.equals(hookTargetBtn)) {
			if (hookTargetBtn.isEnabled()) {
				svulloTargetBtn.setBackground(Color.WHITE);
				theRatTargetBtn.setBackground(Color.WHITE);
				tjoPangTargetBtn.setBackground(Color.WHITE);
				markisenTargetBtn.setBackground(Color.WHITE);
				hannibalTargetBtn.setBackground(Color.WHITE);
				hookTargetBtn.setBackground(Color.RED);
				svulloTargetBtn.repaint();
				theRatTargetBtn.repaint();
				tjoPangTargetBtn.repaint();
				markisenTargetBtn.repaint();
				hannibalTargetBtn.repaint();
				hookTargetBtn.repaint();
				this.target = hookTargetBtn.getText();
				System.out.println(target);
			}
		}
		if(mainFrame.equals(frame)){
			frame.requestFocus();
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
		
	}

	
}

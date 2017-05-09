package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListModel;

import client.GameClient;
import server.GameServer;
/**
 * 
 * @author Julian Hultgren
 * Version 1.0
 *
 */
public class ServerFrame extends JPanel implements ActionListener{
	
	private JPanel panel = new JPanel();
	private JPanel centerPanel = new JPanel(new BorderLayout());
	private JPanel leftGridPanel = new JPanel(new GridLayout(3,1));


	private JPanel flowPanel = new JPanel();
	private JPanel inputPanel = new JPanel(new BorderLayout(50,0));
	private JPanel inputcenterPanel = new JPanel(new FlowLayout());

	private DefaultListModel model = new DefaultListModel();
	private JList listUsers = new JList(model);
	private JTextArea infoArea = new JTextArea(
			"Du är spelvärd för en spelomgång.\n"
			+"Skriv in vilket username du vill använda i rutan ovanför.\n"
			+"Klicka sedan på 'Start server' för att starta servern.\n"
			+"För att starta en spelomgång måste minst 1 till klient\n"
			+"utöver spelvärden (du) vara ansluten.\n"
			+"Klicka sedan på 'Starta spelomgång'.\n"
			+"-----------------------------------------------------------------------------------\n");
	
	
	private JButton bStartGame = new JButton("Starta spelomgång");

	
	private JFrame frame = new JFrame("Server");
	
	private GameServer server = new GameServer(3520, this);
	
	public ServerFrame() {
		
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLayout(new BorderLayout());
		flowPanel.setLayout(new FlowLayout());

		panel.setLayout(new BorderLayout());

		panel.add(centerPanel, BorderLayout.CENTER);

		
		JScrollPane listUsersSP = new JScrollPane(listUsers);
		listUsersSP.setBorder(BorderFactory.createTitledBorder("Connected Users:"));
		listUsersSP.setPreferredSize(new Dimension(180, 300));
		centerPanel.add(listUsersSP, BorderLayout.SOUTH);
		
		infoArea.setEditable(false);
		infoArea.setBorder(BorderFactory.createTitledBorder("Info ruta"));

		leftGridPanel.add(bStartGame);
		centerPanel.add(leftGridPanel, BorderLayout.NORTH);
		centerPanel.add(infoArea, BorderLayout.CENTER);
		
		inputPanel.setPreferredSize(new Dimension(100,50));
		inputPanel.add(inputcenterPanel, BorderLayout.CENTER);

		bStartGame.addActionListener(this);

		bStartGame.setEnabled(false);

		frame.add(panel);
		frame.pack();
		frame.setLocationRelativeTo(null);
	}
	
	public void addUser(String name){
		model.addElement(name);
		bStartGame.setEnabled(true);
	}

	public void updateInfoRuta(String text) {
		infoArea.append(text+"\n");
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == bStartGame) {
			//Anropa start metoden i GameClient
			server.startGame();
			bStartGame.setEnabled(false);
		}
	}
}
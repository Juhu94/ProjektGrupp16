package gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import client.GameClient;

/**
 * Class that creates the menu window for the user. 
 * The user can choose to click on three buttons
 * Host: Creates a game server that multiple users can then connect to
 * Join: Connect to an existing game server
 * Quit: Exit application
 * @author Julian Hultgren, Lukas Persson, Erik Johansson, Simon Börjesson
 * Version 2.0
 *
 */

public class MenuFrame implements ActionListener{
	private	IconPanel iconPanel;
	private	JButton host = new JButton("Host");
	private	JButton join = new JButton("Join");
	private	JButton quit = new JButton("Quit");
	private GridBagLayout layout = new GridBagLayout();	
	private	JPanel panel = new JPanel(new GridLayout(4,1,10,10));
	JFrame frame = new JFrame("Main Menu");
	
	/**
	 * Constructor who receives an ImageIcon object.
	 * The constructor also builds the menu box
	 * @param	ImageIcon	icon
	 */
	
	public MenuFrame(ImageIcon icon) {
		
		iconPanel = new IconPanel(icon);
		iconPanel.setLayout(layout);
		panel.setPreferredSize(new Dimension(250,150));
		panel.setOpaque(false);
		panel.add(host);
		panel.add(join);
		panel.add(quit);
	
		iconPanel.add(panel,new GridBagConstraints());
		iconPanel.setPreferredSize(new Dimension(800,600));
		
		host.addActionListener(this);
		join.addActionListener(this);
		quit.addActionListener(this);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(iconPanel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}	
	
	/**
	 * Method is called for when a user clicks on any of the buttons. 
	 * Host: Creates a game server that multiple users can then join. 
	 * Join: Connecting to an existing game server. 
	 * Quit: Closes the application
	 */
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == host) {
			System.out.println("Någon har klickat på 'Host'"); //Kommentar för White box-testning - Julian Hultgren
			GameClient gc = new GameClient();
			ClientFrame gf = new ClientFrame(gc);
			ServerFrame sf = new ServerFrame();
			frame.dispose();
		}
		if(e.getSource() == join) {
			System.out.println("Någon har klickat på 'Join'"); //Kommentar för White box-testning - Julian Hultgren
			GameClient gc = new GameClient();
			ClientFrame gf = new ClientFrame(gc);
			frame.dispose();
		}
		if(e.getSource() == quit) {
			System.out.println("Någon har klickat på 'Quit'"); //Kommentar för White box-testning - Julian Hultgren
			System.exit(0);
		}
	}	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MenuFrame(new ImageIcon("images/bg.jpg"));
			}
		});
	}
}

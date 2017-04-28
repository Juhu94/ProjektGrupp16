package Gui;

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

import Client.GameClient;
/**
 * Klass som skapar meny fönstret för användaren. 
 * Användaren kan välja att klicka på tre knappar
 * Host: Skapar en spelserver som flera användare sedan kan ansluta till
 * Join: Ansluter till en befintlig spelserver
 * Quit: Avslutar applikationen
 * @author Julian Hultgren
 * Version 1.1
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
	 * Konstruktor som tar emot ett ImageIcon objekt.
	 * Konstruktor bygger också upp menu rutan
	 * @param icon
	 * ImageIcon objektet skickas med som en parameter när en ny instans av klassen IconPanel skapas
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
	 * Metod som anropas om en användaren klickar på någon av knapparna.
	 * Metoden måste implementeras eftersom klassen implementeras interfacet ActionListener
	 * Host: Skapar en spelserver som flera användare sedan kan ansluta till
	 * Join: Ansluter till en befintlig spelserver
	 * Quit: Avslutar applikationen
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == host) {
			GameClient gc = new GameClient();
			ServerFrame sf = new ServerFrame(gc);
			frame.dispose();
		}
		if(e.getSource() == join) {
			GameClient gc = new GameClient();
			ClientFrame gf = new ClientFrame(gc);
			frame.dispose();
		}
		if(e.getSource() == quit) {
			System.exit(0);
		}
	}	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MenuFrame(new ImageIcon("images/jack.jpg"));
			}
		});
	}
}

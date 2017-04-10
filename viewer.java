package klient;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
/**
 * 
 * @author Julian Hultgren
 * Klass som visar en grov planering för hur spelplanen är tänkt att se ut
 *
 */
public class Viewer extends JPanel implements MouseListener, ActionListener, ViewerListener{
	
	private JFrame frame;
	
	private JPanel centerPanel;
	private JPanel hittepo;
	private JPanel southPanel;
	private JPanel northPanel;
	private JPanel westPanel;
	private JPanel eastPanel;
	
	private Border blackline = BorderFactory.createLineBorder(Color.BLACK);
	
	private JButton disconnect;
	private JButton connect;
	private JButton close;
	
	private GameClient client;
	
	private JLabel[][] boardArray = new JLabel[41][47];
	
	/**
	 * Konstruktor som skapar ett grafiskt fönster
	 */
	public Viewer(GameClient client){
		
		this.client = client;
		client.addListeners(this);
		frame = new JFrame("BoardGame");
		
		centerPanel = new JPanel();
		southPanel = new JPanel();
		northPanel = new JPanel();
		westPanel = new JPanel();
		eastPanel = new JPanel();
		hittepo = new JPanel();
		
		disconnect = new JButton("Disconnect");
		connect = new JButton("Connect");
		close = new JButton("Close");
		
		disconnect.addActionListener(this);
		connect.addActionListener(this);
		close.addActionListener(this);
		
		frame.setLayout(new BorderLayout());
		frame.setPreferredSize(new Dimension(1920,1080));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		hittepo.setLayout(new FlowLayout());
		hittepo.add(centerPanel);
		
		centerPanel.setLayout(new GridLayout(41,47,1,1));
		
		southPanel.setLayout(new GridLayout(1,4,200,200));
		southPanel.setPreferredSize(new Dimension(20,20));
		southPanel.add(connect, BorderLayout.CENTER);
		southPanel.add(disconnect,BorderLayout.CENTER);
		southPanel.add(close, BorderLayout.CENTER);
		
		
		for(int i = 0; i < 41; i++){
			for(int j = 0; j < 47; j++){
				boardArray[i][j] = new JLabel();
				boardArray[i][j].setBackground(Color.WHITE);
				boardArray[i][j].setHorizontalAlignment(JLabel.CENTER);
//				boardArray[i][j].setBorder(blackline);
				boardArray[i][j].setOpaque(true);
				boardArray[i][j].addMouseListener(this);
				boardArray[i][j].setPreferredSize(new Dimension(22,22));
				centerPanel.add(boardArray[i][j]);
			}
		}
		
		frame.add(hittepo,BorderLayout.CENTER);
		frame.add(eastPanel, BorderLayout.EAST);
		frame.add(westPanel, BorderLayout.WEST);
		frame.add(northPanel, BorderLayout.NORTH);
		frame.add(southPanel, BorderLayout.SOUTH);
		
//		addListeners();
		
		frame.pack();
		frame.setVisible(true);
	}

	public void mouseClicked(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1){
			
			client.changeTileColor((JLabel)e.getSource());
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
	
	public void setTileColor(JLabel theLabel){
		
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == close){
			System.exit(0);
		}
		
	}	
//		
//	public static void main(String[] args) {
//		Viewer v = new Viewer();
//	}

	@Override
	public void updateViewer(JLabel in) {
		in.setBackground(Color.RED);
		
	}
}

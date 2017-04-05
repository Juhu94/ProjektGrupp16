package BoardGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
public class viewer extends JPanel implements MouseListener, ActionListener{
	
	private JFrame frame;
	
	private JPanel centerPanel;
	private JPanel southPanel;
	private JPanel northPanel;
	private JPanel westPanel;
	private JPanel eastPanel;
	
	private Border blackline = BorderFactory.createLineBorder(Color.BLACK);
	
	private JButton disconnect;
	private JButton connect;
	private JButton close;
	
	private JLabel[][] boardArray = new JLabel[10][10];
	
	/**
	 * Konstruktor som skapar ett grafiskt fönster
	 */
	public viewer(){
		
		frame = new JFrame("BoardGame");
		
		centerPanel = new JPanel();
		southPanel = new JPanel();
		northPanel = new JPanel();
		westPanel = new JPanel();
		eastPanel = new JPanel();	
		
		disconnect = new JButton("Disconnect");
		connect = new JButton("Connect");
		close = new JButton("Close");
		
		disconnect.addActionListener(this);
		connect.addActionListener(this);
		close.addActionListener(this);
		
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		centerPanel.setPreferredSize(new Dimension(1280,900));
		centerPanel.setLayout(new GridLayout(10,10,1,1));
		
		southPanel.setLayout(new GridLayout(1,4,200,200));
		southPanel.setPreferredSize(new Dimension(20,20));
		southPanel.add(connect, BorderLayout.CENTER);
		southPanel.add(disconnect,BorderLayout.CENTER);
		southPanel.add(close, BorderLayout.CENTER);
		
		
		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 10; j++){
				boardArray[i][j] = new JLabel();
				boardArray[i][j].setBackground(Color.WHITE);
				boardArray[i][j].setHorizontalAlignment(JLabel.CENTER);
				boardArray[i][j].setBorder(blackline);
				boardArray[i][j].setOpaque(true);
				boardArray[i][j].addMouseListener(this);
				centerPanel.add(boardArray[i][j]);
			}
		}
		
		frame.add(centerPanel,BorderLayout.CENTER);
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
			JLabel theLabel = (JLabel) e.getSource();
			theLabel.setBackground(Color.RED);
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

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == close){
			System.exit(0);
		}
		
	}	
	public static void main(String[] args) {
		viewer v = new viewer();
	}
}

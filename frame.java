package BoardGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
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
public class frame extends JPanel{
	
	private JFrame frame;
	private JPanel centerPanel;
	private JPanel southPanel;
	private JPanel northPanel;
	private JPanel westPanel;
	private JPanel eastPanel;
	private Border blackline = BorderFactory.createLineBorder(Color.BLACK);
	private JLabel[][] boardArray = new JLabel[50][50];
	
	public frame(){
		frame = new JFrame("BoardGame");
		centerPanel = new JPanel();
		southPanel = new JPanel();
		northPanel = new JPanel();
		westPanel = new JPanel();
		eastPanel = new JPanel();		
		
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		centerPanel.setPreferredSize(new Dimension(1280,900));
		centerPanel.setLayout(new GridLayout(50,50,1,1));
		
		for(int i = 0; i < 50; i++){
			for(int j = 0; j < 50; j++){
				boardArray[i][j] = new JLabel();
				boardArray[i][j].setBackground(Color.WHITE);
				boardArray[i][j].setHorizontalAlignment(JLabel.CENTER);
				boardArray[i][j].setBorder(blackline);
				boardArray[i][j].setOpaque(true);
				centerPanel.add(boardArray[i][j]);
			}
		}
		
		frame.add(centerPanel,BorderLayout.CENTER);
		frame.add(eastPanel, BorderLayout.EAST);
		frame.add(westPanel, BorderLayout.WEST);
		frame.add(northPanel, BorderLayout.NORTH);
		frame.add(southPanel, BorderLayout.SOUTH);
		frame.pack();
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		frame f = new frame();
	}
}

package client;

import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

import gui.MenuFrame;
/**
 * 
 * @author Julian Hultgren
 * Version 1.0.1
 *
 */
public class StartGame {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MenuFrame(new ImageIcon("images/yoda.jpg"));
			}
		});
	}
}

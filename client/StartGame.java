package client;

import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

import gui.MenuFrame;

/**
 * 
 * @author Julian Hultgren, Lukas Persson, Erik Johansson, Simon Börjesson
 * Version 2.0
 *
 */

public class StartGame {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MenuFrame(new ImageIcon("images/bg.jpg"));
			}
		});
	}
}

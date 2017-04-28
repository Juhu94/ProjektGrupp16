package Client;

import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

import Gui.MenuFrame;
/**
 * 
 * @author Julian Hultgren
 * Version 1.0
 *
 */
public class StartClient {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MenuFrame(new ImageIcon("images/jack.jpg"));
			}
		});
	}
}

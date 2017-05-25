package gui;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * 
 * @author Julian Hultgren
 * Version 2.0
 *
 */

public class IconPanel extends JPanel {
	private ImageIcon icon;
	
	/**
	 * Sets the icon
	 * 
	 * @param	IconImage	icon
	 */
	public IconPanel(ImageIcon icon) {
		this.icon = icon;
	}
	
	/**
	 * Paints the component
	 */
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Image im = icon.getImage();
		g.drawImage(im, 0, 0, getWidth(), getHeight(), null);
	}
}
 
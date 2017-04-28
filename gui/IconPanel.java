package gui;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
/**
 * 
 * @author Julian Hultgren
 * Version 1.0
 *
 */
public class IconPanel extends JPanel {
	private ImageIcon icon;
	
	public IconPanel(ImageIcon icon) {
		this.icon = icon;
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Image im = icon.getImage();
		g.drawImage(im, 0, 0, getWidth(), getHeight(), null);
	}
}
 
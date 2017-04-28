package Gui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JLabel;
/**
 * 
 * @author Julian Hultgren
 * Version 1.0
 */
public class ExtendedJLabel extends JLabel {
	private int row;
	private int col;
	private Color color;
	private boolean occupied;
	
	public ExtendedJLabel(int row, int col, Color c) {
		this.row = row;
		this.col = col;
		this.color = c;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}
	
	public void reset(){
		
	}
	
	public void setOccupied(boolean state){
		this.occupied = state;
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int width = this.getWidth();
		int height = this.getHeight();

	}
}

package gui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JLabel;

/**
 * 
 * @author Julian Hultgren
 * Version 2.0
 *
 */

public class ExtendedJLabel extends JLabel {
	private int row;
	private int col;
	private Color color;
	private boolean occupied;
	
	/**
	 * Constructor
	 * 
	 * @param 	int		row
	 * @param	int		col
	 * @param 	Color	c
	 */
	
	public ExtendedJLabel(int row, int col, Color c) {
		this.row = row;
		this.col = col;
		this.color = c;
	}
	
	/**
	 * Returns the row
	 * 
	 * @return	int
	 */
	
	public int getRow() {
		return row;
	}
	
	/**
	 * Returns the column
	 * 
	 * @return	int
	 */
	
	public int getCol() {
		return col;
	}
	
	/**
	 * Reset
	 */
	
	public void reset(){
		
	}
	
	/**
	 * Returns true if its occupied
	 * 
	 * @param 	boolean	state
	 */
	
	public void setOccupied(boolean state){
		this.occupied = state;
	}
	
	/**
	 * Paints the component
	 */
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int width = this.getWidth();
		int height = this.getHeight();
		

	}
}

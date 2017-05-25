package client;

/**
 * 
 * @author Julian Hultgren, Lukas Persson, Erik Johansson, Simon Börjesson
 * Version 2.0
 *
 */

public class WaterTile extends Tile {
	
	int nextRow;
	int nextCol;
	
	/**
	 *Constructor
	 */
	
	public WaterTile(){
		super("Water", false, true, false);
	}
	
	/**
	 * Sets the next row and column you will travel form hear
	 * @param	int	row
	 * @param	int	col
	 */
	
	public void setNext(int row, int col){
		nextRow = row;
		nextCol = col;
	}
	
	/**
	 * Returns the next row
	 */
	
	public int nextRow(){
		return nextRow;
	}
	
	/**
	 * Returns the next column
	 */
	
	public int nextCol(){
		return nextCol;
	}
}

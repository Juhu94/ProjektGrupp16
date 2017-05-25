package client;

/**
 * 
 * @author Julian Hultgren, Lukas Persson, Erik Johansson, Simon Börjesson
 * Version 2.0
 *
 */

public class SpecialTile extends Tile{
	private int successRow;
	private int successCol;
	private int failRow;
	private int failCol;
	
	public SpecialTile(){
		super("Special", true, true, false);
	}
	
	/**
	 * Returns the row the character will get to if its a good roll
	 * @return int
	 */
	
	public int successRow(){
		return successRow;
	}
	
	/**
	 * Returns the column the character will get to if its a good roll
	 * @return int
	 */
	
	public int successCol(){
		return successCol;
	}
	
	/**
	 * Sets the row and column the character will get to if success() get called
	 * @param int row
	 * @param int col
	 */
	
	public void setSuccess(int row, int col){
		successRow = row;
		successCol = col;
	}
	
	/**
	 * Returns the row the character will get to if its a bad roll
	 * @return int
	 */
	
	public int failRow(){
		return failRow;
	}
	
	/**
	 * Returns the column the character will get to if its a bad roll
	 * @return int
	 */
	
	public int failCol(){
		return failCol;
	}
	
	/**
	 * Sets the row and column the character will get to if fail() get called
	 * @param int row
	 * @param int col
	 */
	
	public void setFail(int row, int col){
		failRow = row;
		failCol = col;
	}
}

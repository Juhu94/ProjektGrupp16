package client;
/**
 * 
 * @author
 * Version 1.0
 *
 */
public class Character {
	private String namn;
	private int row;
	private int col;
	private boolean sleeping;
	private int mapPieces;
	private boolean hasTreasure;
	
	public Character(String namn, int row, int col){
		this.namn = namn;
		this.row = row;
		this.col = col;
		sleeping = false;
		mapPieces = 1;
		hasTreasure = false;
	}
	
	/**
	 * Sets the position of the character
	 * @param int	row
	 * @param int	column
	 */
	
	public void setPos(int row, int col){
		this.row = row;
		this.col = col;
		
	}
	
	/**
	 * Returns the column of the character
	 * @return int
	 */
	
	public int getCol(){
		return col;
		
	}
	
	/**
	 * Returns the row of the character
	 * @return int
	 */
	
	public int getRow(){
		return row;
	}
}

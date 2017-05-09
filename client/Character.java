package client;

import java.io.Serializable;

/**
 * 
 * @author
 * Version 1.0
 *
 */
public class Character implements Serializable{
	private String name;
	private int row;
	private int col;
	private int sleeping;
	private int mapPieces;
	private boolean hasTreasure;
	
	public Character(String name, int row, int col){
		this.name = name;
		this.row = row;
		this.col = col;
		sleeping = 0;
		mapPieces = 1;
		hasTreasure = false;
	}
	
	/**
	 * Returns a int that tells how many turns this character is sleeping
	 * @return int
	 */
	
	public int sleeping(){
		return sleeping;
	}
	
	public String getName(){
		return name;
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

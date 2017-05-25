package client;

import java.io.Serializable;

/**
 * 
 * @author Julian Hultgren, Lukas Persson, Erik Johansson, Simon Börjesson
 * Version 2.0
 *
 */

public class Character implements Serializable{
	private String name;
	private String characterName;
	private int row;
	private int col;
	private int sleeping;
	private int mapPieces;
	private boolean hasTreasure;
	
	/**
	 * Constructor fills up basic information to the character
	 * 
	 * @param 	String	name
	 * @param 	int		row
	 * @param 	int		col
	 */
	
	public Character(String name, int row, int col){
		this.name = name;
		this.row = row;
		this.col = col;
		sleeping = 0;
		mapPieces = 1;
		hasTreasure = false;
	}
	
	/**
	 * This method subtracts from the sleeping/wounded int unless its at 0
	 */
	
	public void passATurn(){
		System.out.println("Character: sleeping variabeln: " + sleeping);
		if(this.sleeping > 0){
			this.sleeping--;
		} else{
			this.sleeping = 0;
		}
	}
	
	/**
	 * Give him the treasure
	 */
	
	public void giveTreasure(){
		hasTreasure = true;
	}
	
	/**
	 * Take away his treasure
	 */
	public void takeTreasure(){
		hasTreasure = false;
	}
	
	/**
	 * Returns true if the character has the treasure
	 * @return
	 */
	
	public boolean hasTreasure(){
		return hasTreasure;
	}
	
	/**
	 * Makes this character wounded for 4 turns
	 */
	
	public void shot(){
		sleeping = 4;
	}
	
	/**
	 * Returns a int that tells how many turns this character is sleeping
	 * 
	 * @return int
	 */
	
	public void setCharacter(String character){
		this.characterName = character;
	}
	
	/**
	 * Returns the name of this character
	 * 
	 * @return	String
	 */
	
	public String getCharacterName(){
		return this.characterName;
	}
	
	/**
	 * Returns an int that represents how many turns the character is sleeping/wounded
	 * @return int
	 */
	
	public int sleeping(){
		return sleeping;
	}
	
	/**
	 * Returns the name of the character
	 * @return String
	 */
	
	public String getName(){
		return name;
	}
	
	/**
	 * Takes away this characters map pieces and gives them to you
	 * @return 	int
	 */
	
	public int stealPieces(){
		int mapPiecesTemp = this.mapPieces;
		this.mapPieces = 0;
		return mapPiecesTemp;
	}
	
	/**
	 * Gives the character the given number of map pieces
	 * @param	int	pieces
	 */
	
	public void givePieces(int pieces){
		this.mapPieces += pieces;
	}
	
	/**
	 * Returns the number of map pieces
	 * @return	int
	 */
	
	// Används ej! endast för system out print-testning!!!
	public int getPieces(){
		return this.mapPieces;
	}
	
	/**
	 * Sets the map pieces to a given number
	 * 
	 * @param 	int	pieces
	 */
	
	public void setPieces(int pieces){
		this.mapPieces = pieces;
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

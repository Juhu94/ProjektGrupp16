package client;

import java.io.Serializable;

/**
 * 
 * @author
 * Version 1.9
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
	
	public Character(String name, int row, int col){
		this.name = name;
		this.row = row;
		this.col = col;
		sleeping = 0;
		mapPieces = 1;
		hasTreasure = false;
	}
	
	public void passATurn(){
		System.out.println("Character: sleeping variabeln: " + sleeping);
		if(this.sleeping > 0){
			this.sleeping--;
		} else{
			this.sleeping = 0;
		}
	}
	
	/**
	 * Makes this character wounded for 4 turns
	 */
	
	public void shot(){
		sleeping = 4;
	}
	
	/**
	 * Returns a int that tells how many turns this character is sleeping
	 * @return int
	 */
	
	public void setCharacter(String character){
		this.characterName = character;
	}
	
	public String getCharacterName(){
		return this.characterName;
	}
	
	public int sleeping(){
		return sleeping;
	}
	
	public String getName(){
		return name;
	}
	
	public int stealPieces(){
		int mapPiecesTemp = this.mapPieces;
		this.mapPieces = 0;
		return mapPiecesTemp;
	}
	
	public void givePieces(int pieces){
		this.mapPieces += pieces;
	}
	
	// Används ej! endast för system out print-testning!!!
	public int getPieces(){
		return this.mapPieces;
	}
	
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

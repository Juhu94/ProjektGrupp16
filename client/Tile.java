package client;

/**
 * 
 * @author Julian Hultgren, Lukas Persson, Erik Johansson, Simon Börjesson
 * Version 2.0
 */

public class Tile {
	private boolean accessible;
	private boolean seeThrough;
	private boolean treasure = false;
	private String name;
	private boolean raft = false;
	private boolean canon = false;
	private boolean boat = false;
	private Character character = null;
	private Character sleepingCharacter = null;

	
	/**
	 * Constructor gives the basic information to the tile
	 * 
	 * @param 	String 	name
	 * @param 	boolean	accessible
	 * @param 	boolean	seethrough
	 * @param 	boolean	treasure
	 */
	
	public Tile(String name, boolean accessible, boolean seethrough, boolean treasure){
		this.name = name; 
		this.accessible = accessible;
		this.seeThrough = seethrough;
		this.treasure = treasure;
	}
	
	/**
	 * Returns true if their is a sleeping/wounded character on this tile
	 * @return	boolean
	 */
	
	public boolean containsSleepingCharacter(){
		if(sleepingCharacter != null){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Returns the character that occupies this tile
	 * @return	Character
	 */
	
	public Character getSleepingCharacter(){
		return this.sleepingCharacter;
	}
	
	/**
	 * removes the sleeping/wounded character on this tile
	 */
	
	public void removeSleepingCharacter(){
		character = sleepingCharacter;
		sleepingCharacter = null;
	}
	
	/**
	 * Changes the character on this tile to the sleeping position
	 */
	
	public void moveCharacterToSleeping(){
		sleepingCharacter = character; 
		character = null;
	}
	
	/**
	 * Checks if there is a character occupying this tile
	 * @return boolean
	 */
	
	public boolean containsCharacter(){
		if(character != null){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Returns a Character
	 * @return Character
	 */
	
	public Character getCharacter(){
		return character;
	}
	
	/**
	 * Places a Character on this tile 
	 * @param Character
	 */
	
	public void setCharacter(Character character){
		this.character = character;
	}
	
	/**
	 * Removes the Character from this tile
	 */
	
	public void removeCharacter(){
		this.character = null;
	}
	
	/**
	 * Plases a treasure on this tile
	 */
	
	public void treasureOn(){
		treasure = true;
	}
	
	/**
	 * Takes away a treasure from this tile
	 */
	
	public void treasureOff(){
		treasure = false;
	}
	
	/**
	 * Returns true if there is a treasure on this tile
	 * @return	boolean
	 */
	
	public boolean getTreasure(){
		return treasure;
	}
	
	
	/**
	 * Returns the name of the tile
	 * @return String
	 */
	
	public String getName(){
		return this.name;
	}
	
	/**
	 * Returns true if this tile is see through
	 * @return boolan
	 */
	
	public boolean getSeeThrough(){
		return this.seeThrough;
	}
	
	/**
	 * Returns true if this tile is accessible
	 * @return boolan
	 */
	
	public boolean getAccessible(){
		return this.accessible;
	}
	
	/**
	 * Place a raft on this tile
	 */
	
	public void raftOn(){
		raft = true;
	}
	
	/**
	 * Takes away a raft from this tile
	 */
	
	public void raftOff(){
		raft = false;
	}
	
	/**
	 * Returns true if there is a raft on this tile
	 * @return boolean
	 */
	
	public boolean getRaft(){
		return raft;
	}
	
	/**
	 * Place a canon on this tile
	 */
	
	public void canonOn(){
		canon = true;
	}
	
	/**
	 * Takes away a canon on this tile
	 */
	
	public void canonOff(){
		canon = false;
	}
	
	/**
	 * Returns true if there is a canon on this tile
	 * @return boolan
	 */
	
	public boolean getCanon(){
		return canon;
	}
	
	/**
	 * Place a boat on this tile
	 */
	
	public void boatOn(){
		boat = true;
	}
	
	/**
	 * Takes away a boat on this tile
	 */
	
	public void boatOff(){
		boat = false;
	}
	
	/**
	 * Returns true if there is a boat on this tile
	 * @return boolan
	 */
	
	public boolean getBoat(){
		return boat;
	}
	
	/**
	 * This method is a placeholder for waterTile
	 * @param 	int		row
	 * @param 	int		col
	 */
	
	public void setNext(int row, int col){	
	}
	
	/**
	 * This method is a placeholder for waterTile
	 * 
	 * @return int
	 */
	
	public int nextRow(){
		return 0;
	}
	
	/**
	 * This method is a placeholder for waterTile
	 * @return int
	 */
	
	public int nextCol(){
		return 0;
	}
	
	/**
	 * Returns the row the character will get to if its a good roll
	 * @return int
	 */
	
	public int successRow(){
		return 0;
	}
	
	/**
	 * Returns the column the character will get to if its a good roll
	 * @return int
	 */
	
	public int successCol(){
		return 0;
	}
	
	/**
	 * Sets the tile the character will get to if success() get called
	 * @param Tile
	 */
	
	public void setSuccess(int row, int col){
	}
	
	/**
	 * Returns the row the character will get to if its a bad roll
	 * @return int
	 */
	
	public int failRow(){
		return 0;
	}
	
	/**
	 * Returns the column the character will get to if its a bad roll
	 * @return int
	 */
	
	public int failCol(){
		return 0;
	}
	
	/**
	 * Sets the tile the character will get to if fail() get called
	 * @param Tile
	 */
	
	public void setFail(int row, int col){
	}
	
}

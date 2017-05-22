package client;
/**
 * 
 * @author 
 * Version 1.9
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
	 *Constructor
	 */
	
	public Tile(String name, boolean accessible, boolean seethrough, boolean treasure){
		this.name = name; 
		this.accessible = accessible;
		this.seeThrough = seethrough;
		this.treasure = treasure;
	}
	
	public boolean containsSleepingCharacter(){
		if(sleepingCharacter != null){
			return true;
		}else{
			return false;
		}
	}
	
	public Character getSleepingCharacter(){
		return this.sleepingCharacter;
	}
	
//	public Character getSleepingCharacter(){
//		return sleepingCharacter;
//	}
	
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
	 * Plases a raft on this tile
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
	 * Plases a canon on this tile
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
	 * Plases a boat on this tile
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
	
	public void setNext(int row, int col){	
	}
	
	public int nextRow(){
		return 0;
	}
	
	public int nextCol(){
		return 0;
	}
	
	/**
	 * Returns the tile the character will get to if its a good roll
	 * @return Tile
	 */
	
	public int successRow(){
		return 0;
	}
	
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
	 * Returns the tile the character will get to if its a bad roll
	 * @return Tile
	 */
	
	public int failRow(){
		return 0;
	}
	
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

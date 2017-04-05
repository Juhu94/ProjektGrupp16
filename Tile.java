package sjörövarön;

public class Tile {
	private boolean accessible;
	private boolean seeThrough;
	private boolean treasure = false;
	private boolean raft = false;
	private boolean canon = false;
	private boolean boat = false; 
	private String name;
	
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
	
}

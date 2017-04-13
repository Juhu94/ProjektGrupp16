package sjörövarön;

public class Tile {
	private boolean accessible;
	private boolean seeThrough;
	private boolean treasure = false;
	private String name;
	
	/**
	 *Constructor
	 */
	
	public Tile(String name, boolean accessible, boolean seethrough, boolean treasure){
		this.name = name; 
		this.accessible = accessible;
		this.seeThrough = seethrough;
		this.treasure = treasure;
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
	
}

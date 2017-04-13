package sjörövarön;

public class GroundTile extends Tile {
	private boolean raft = false;
	private boolean canon = false;
	private boolean boat = false; 

	/**
	 *Constructor
	 */
	public GroundTile(boolean raft, boolean canon, boolean boat){
		super("Ground", true, true, false);
		this.raft = raft;
		this.canon = canon;
		this.boat = boat;
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
}

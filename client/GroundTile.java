package client;

/**
 * 
 * @author Julian Hultgren, Lukas Persson, Erik Johansson, Simon Börjesson
 * Version 2.0
 *
 */

public class GroundTile extends Tile {

	/**
	 *Constructor
	 */
	public GroundTile(boolean raft, boolean canon, boolean boat){
		super("Ground", true, true, false);
		if (raft){
			super.raftOn();
		}
		if (canon){
			super.canonOn();
		}
		if (boat){
			super.boatOn();
		}
	}
	
	
}

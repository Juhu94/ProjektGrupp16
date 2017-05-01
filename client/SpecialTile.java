package client;
/**
 * 
 * @author 
 * Version 1.0
 */
public class SpecialTile extends Tile{
	private Tile success = null;
	private Tile fail = null;
	
	public SpecialTile(){
		super("Special", true, true, false);
	}
	
	/**
	 * Returns the tile the character will get to if its a good roll
	 * @return Tile
	 */
	
	public Tile success(){
		return success;
	}
	
	/**
	 * Sets the tile the character will get to if success() get called
	 * @param Tile
	 */
	
	public void setSuccess(Tile tile){
		success = tile;
	}
	
	/**
	 * Returns the tile the character will get to if its a bad roll
	 * @return Tile
	 */
	
	public Tile fail(){
		return fail;
	}
	
	/**
	 * Sets the tile the character will get to if fail() get called
	 * @param Tile
	 */
	
	public void setFail(Tile tile){
		fail = tile;
	}
}

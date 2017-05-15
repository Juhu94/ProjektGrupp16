package client;
/**
 * 
 * @author 
 * Version 1.0
 */
public class SpecialTile extends Tile{
	private int successRow;
	private int successCol;
	private int failRow;
	private int failCol;
	
	public SpecialTile(){
		super("Special", true, true, false);
	}
	
	/**
	 * Returns the tile the character will get to if its a good roll
	 * @return Tile
	 */
	
	public int successRow(){
		return successRow;
	}
	
	public int successCol(){
		return successCol;
	}
	
	/**
	 * Sets the tile the character will get to if success() get called
	 * @param Tile
	 */
	
	public void setSuccess(int row, int col){
		successRow = row;
		successCol = col;
	}
	
	/**
	 * Returns the tile the character will get to if its a bad roll
	 * @return Tile
	 */
	
	public int failRow(){
		return failRow;
	}
	
	public int failCol(){
		return failCol;
	}
	
	/**
	 * Sets the tile the character will get to if fail() get called
	 * @param Tile
	 */
	
	public void setFail(int row, int col){
		failRow = row;
		failCol = col;
	}
}

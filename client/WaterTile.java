package client;
/**
 * 
 * @author 
 * Version 1.0
 */
public class WaterTile extends Tile {
	
	int nextRow;
	int nextCol;
	
	/**
	 *Constructor
	 */
	
	public WaterTile(){
		super("Water", false, true, false);
	}
	
	public void setNext(int row, int col){
		nextRow = row;
		nextCol = col;
	}
	
	public int nextRow(){
		return nextRow;
	}
	
	public int nextCol(){
		return nextCol;
	}
}

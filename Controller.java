package sjörövarön;

import java.lang.reflect.Array;
import java.util.Random;

public class Controller {
	private Tile[][] map = new Tile[47][41];

	/**
	 * Tells viewer to move a piece
	 * @param pice	?
	 * @param x		int
	 * @param y		int
	 */
	public void movePiece(int pice, int x, int y){
		
	}
	
	/**
	 * Rolls a dice
	 * @return int (1 - 6)
	 */
	public int rollDice(){
		Random rad = new Random();
		return (rad.nextInt(6) + 1);
	}
	
	
}

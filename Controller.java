package sjörövarön;

import java.lang.reflect.Array;
import java.util.Random;

public class Controller {
	// Not finished...
	private Tile[][] map = {new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile();
							new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new GroundTile(false, false, false), new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new GroundTile(false, false, false), new GroundTile(false, false, false),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(), new JungleTile(),new JungleTile(), new JungleTile(), new JungleTile(), new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile();
							new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new GroundTile(false, false, false), new GroundTile(false, false, false), new SpecialTile(),new WaterTile(),new SpecialTile(),new WaterTile(),new SpecialTile(),new GroundTile(false, false, false), new GroundTile(false, false, false), new GroundTile(false, false, false), new GroundTile(false, false, false), new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new JungleTile(), new GroundTile(false, false, false), new GroundTile(false, false, false), new GroundTile(false, false, false), new GroundTile(false, false, false), new JungleTile(), new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile();
							new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new JungleTile(),new GroundTile(false, false, false), new GroundTile(false, false, false),new JungleTile(), new WaterTile(),new JungleTile(), new WaterTile(),new JungleTile(), new JungleTile(), new GroundTile(false, false, false), new JungleTile(), new GroundTile(false, false, false), new GroundTile(false, false, false), new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new GroundTile(false, false, false), new GroundTile(false, false, false), new GroundTile(false, false, false), new GroundTile(false, false, false), new JungleTile(), new JungleTile(), new GroundTile(false, false, false), new GroundTile(false, false, false), new JungleTile(), new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile();
							new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new GroundTile(false, false, false),new GroundTile(false, false, false),new JungleTile(),new JungleTile(),new WaterTile(),new JungleTile()new WaterTile(),new JungleTile(),new JungleTile(),new JungleTile(), new JungleTile(), new GroundTile(false, false, false),new GroundTile(false, false, false),new JungleTile()new WaterTile(),new WaterTile(),new WaterTile(),new JungleTile(),new SpecialTile(), new GroundTile(false, false, false),new WaterTile(),new JungleTile(),new JungleTile(),new JungleTile(),new GroundTile(false, false, false),new GroundTile(false, false, false), new JungleTile(),new JungleTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile();
							new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new GroundTile(false, false, false),new GroundTile(false, false, false),new JungleTile(),new JungleTile(),new JungleTile(),new JungleTile(),new JungleTile(),new JungleTile(),new JungleTile(),new JungleTile(),new JungleTile(),new GroundTile(false, false, false),new GroundTile(false, false, false),new JungleTile(),new JungleTile(), new WaterTile(),new WaterTile(),new JungleTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new JungleTile(),new GroundTile(false, false, false), new GroundTile(false, false, false),new JungleTile(),new JungleTile(),new JungleTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile();
							new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new JungleTile(),new GroundTile(false, false, false), new JungleTile(),new JungleTile(),new JungleTile(),new GroundTile(false, false, false),new JungleTile(),new GroundTile(false, false, false),new JungleTile(),new JungleTile(),new JungleTile(),new JungleTile(),new JungleTile(),new GroundTile(false, false, false),new JungleTile(),new GroundTile(false, false, false), new GroundTile(false, false, true),new JungleTile(),new JungleTile(),new SpecialTile(),new JungleTile(),new SpecialTile(),new WaterTile(),new SpecialTile(),new GroundTile(false, false, false),new JungleTile(),new JungleTile(),new JungleTile(),new JungleTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile(),new WaterTile();
							};

	/**
	 * Tells viewer to move a piece
	 * 
	 * @param pice
	 *            ?
	 * @param x
	 *            int
	 * @param y
	 *            int
	 */
	public void movePiece(int pice, int x, int y) {
		//placeholder
	}

	/**
	 * Rolls a dice
	 * 
	 * @return int (1 - 6)
	 */
	public int rollDice() {
		Random rad = new Random();
		return (rad.nextInt(6) + 1);
	}

}

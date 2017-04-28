package Client;
/**
 * 
 * @author 
 * Version 1.0
 */
public class SpecialTile extends Tile{
	private Tile success;
	private Tile fail;

	public SpecialTile(){
		super("Special", true, true, false);
	}
	
	public Tile success(){
		return success;
	}
	
	public Tile fail(){
		return fail;
	}
}

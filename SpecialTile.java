package sjörövarön;

public class SpecialTile extends Tile{
	private Tile sucsess;
	private Tile fail;

	public SpecialTile(){
		super("Special", true, true, false);
	}
	
	public Tile sucsess(){
		return sucsess;
	}
	
	public Tile fail(){
		return fail;
	}
}

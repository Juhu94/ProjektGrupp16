package projekt;

public class Character {
	private int iD;
	private int row;
	private int col;
	private boolean sleeping;
	private int mapPieces;
	private boolean hasTreasure;
	
	public Character(int id, int row, int col){
		this.iD = id;
		this.row = row;
		this.col = col;
		sleeping = false;
		mapPieces = 1;
		hasTreasure = false;
	}
	
	public void setPos(int row, int col){
		this.row = row;
		this.col = col;
		
	}
}

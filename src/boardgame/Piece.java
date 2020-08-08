package boardgame;

public class Piece {

	//association
	protected Position position;
	private Board board;
	
	//constructors
	public Piece() {
	}
	
	public Piece (Board board) {
		this.board = board;
		position = null;
	}
	
	protected Board getBoard() {
		return board;
	}
}

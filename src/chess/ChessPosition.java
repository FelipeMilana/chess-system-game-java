package chess;

import boardgame.Position;

public class ChessPosition {

	//attributes
	private char column;
	private int row;
	
	//constructors
	public ChessPosition(char column, int row) {
		if(column < 'a' || column > 'h' || row < 1 || row > 8) {  //teste de verificação se existe
			throw new ChessException("Error instantiating ChessPosition. Valid values are from a1 to h8");
		}
		this.column = column;
		this.row = row;
	}

	//methods
	public char getColumn() {
		return column;
	}

	public int getRow() {
		return row;
	}
	
	protected Position toPosition() { //de ChessPosition para Position
		return new Position(8 - row, column - 'a');  
	}
	
	protected static ChessPosition fromPosition(Position position) {   //nao precisa de instanciação
		return new ChessPosition((char) ('a' + position.getColumn()), (8 - position.getRow())); //de Position para ChessPosition.
	}
	
	@Override
	public String toString() {
		return ""+ column + row;
	}
	
	
}

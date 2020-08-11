package chess;

import boardgame.Board;
import boardgame.Piece;

public abstract class ChessPiece extends Piece {  //ainda � generica, entao � tambem abstrata

	//association
	private Color color;
	
	//constructor
	public ChessPiece(Board board, Color color) { 
		super(board); //o position n ta no construtor, � nulo
		this.color = color;
	}
	
	//methods
	public Color getColor() {   //apenas mostrara a cor, e nao altera-la
		return color;
	}
}
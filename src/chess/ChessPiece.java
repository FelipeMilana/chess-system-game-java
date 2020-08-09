package chess;

import boardgame.Board;
import boardgame.Piece;

public class ChessPiece extends Piece {

	//association
	private Color color;
	
	//constructor
	public ChessPiece(Board board, Color color) { 
		super(board); //o position n ta no construtor, é nulo
		this.color = color;
	}
	
	//methods
	public Color getColor() {   //apenas mostrara a cor, e nao altera-la
		return color;
	}
}

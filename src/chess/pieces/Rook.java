package chess.pieces;

import boardgame.Board;
import chess.ChessPiece;
import chess.Color;

public class Rook extends ChessPiece{

	//contructor
	public Rook(Board board, Color color) {
		super(board, color);
	}

	//methods
	public String toString() {
		return "R";
	}
}

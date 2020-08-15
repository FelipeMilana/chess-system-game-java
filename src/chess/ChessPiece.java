package chess;

import boardgame.Board;

import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece {  //ainda é generica, entao é tambem abstrata

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
	
	public ChessPosition getChessPosition() {   //transforma position em chessposition 
		return ChessPosition.fromPosition(position);
	}
	
	protected boolean isThereOpponentPiece(Position position) {  // protected para ser usada na classe Chesspiece e suas subclasses
		ChessPiece p = (ChessPiece) getBoard().piece(position);   //retorna a peça que esta naquela posição, faz o dowcasting de piece para ChessPiece
		return p != null && p.getColor() != color;  //retorn verdadeiro se a peça p for diferente de nulo, e se  cor da peça for diferente da minha peça
	}
}
package chess;

import boardgame.Board;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {

	//association
	private Board board;
	
	//constructors
	public ChessMatch() { //construtor padrao, tudo nulo
		board = new Board(8, 8);     //   intancia o objeto board e cria um tabuleiro 8 x 8 nulo, e cria peças do tipo piece
		initialSetup();
	}
	
	//methods
	public ChessPiece[][] getPieces() {  //o que interessa é a peça do tipo de xadrez, nao uma peça qualquer
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];  //instnaciamos uma matriz vazia do tipo chesspiece
		for(int i = 0; i < board.getRows(); i++) {
			for(int j = 0; j < board.getColumns(); j++) {
				mat[i][j] = (ChessPiece) board.piece(i, j);  //era do tipo piece e faz o downcasting, pois chesspiece é subclasse de piece
			}
		}
		return mat;
	}
	
	private void initialSetup() {   //inicia a partida e coloca as peças no tabuleiro
		placeNewPiece('b', 6, new Rook(board, Color.WHITE));  //instancia uma peça, com o tabuleiro e cor, e sua posição
		placeNewPiece('e', 8, new King(board, Color.BLACK));
		placeNewPiece('e', 1, new King(board, Color.WHITE));		
	}
	
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
	}
}

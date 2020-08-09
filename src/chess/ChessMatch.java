package chess;

import boardgame.Board;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {

	//association
	private Board board;
	
	//constructors
	public ChessMatch() { //construtor padrao, tudo nulo
		board = new Board(8, 8);     //   intancia o objeto board e cria um tabuleiro 8 x 8 nulo, e cria pe�as do tipo piece
		initialSetup();
	}
	
	//methods
	public ChessPiece[][] getPieces() {  //o que interessa � a pe�a do tipo de xadrez, nao uma pe�a qualquer
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];  //instnaciamos uma matriz vazia do tipo chesspiece
		for(int i = 0; i < board.getRows(); i++) {
			for(int j = 0; j < board.getColumns(); j++) {
				mat[i][j] = (ChessPiece) board.piece(i, j);  //era do tipo piece e faz o downcasting, pois chesspiece � subclasse de piece
			}
		}
		return mat;
	}
	
	private void initialSetup() {   //inicia a partida e coloca as pe�as no tabuleiro
		board.placePiece(new Rook(board, Color.WHITE), new Position(2, 1));  //instancia uma pe�a, com o tabuleiro e cor, e sua posi��o
		board.placePiece(new King(board, Color.BLACK), new Position(0, 4));
		board.placePiece(new King(board, Color.WHITE), new Position(7, 4));		
	}
}

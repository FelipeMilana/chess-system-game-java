package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {

	//association
	private Board board;
	
	//constructors
	public ChessMatch() { //construtor padrao, sem parametros
		board = new Board(8, 8);     //intancia o objeto board e cria um tabuleiro 8 x 8, e cria peças do tipo piece
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
	
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition(); //faz a conversao para position
		Position target = targetPosition.toPosition();	//faz a conversao para position
		validateSourcePosition(source);
		Piece capturedPiece = makeMove(source, target);
		return (ChessPiece) capturedPiece;
	}
	
	private Piece makeMove(Position source, Position target) {
		Piece p = board.removePiece(source);
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(p, target);
		return capturedPiece;
	}
	
	private void validateSourcePosition(Position position) {
		if(!board.thereIsAPiece(position)) {
			throw new ChessException("There is no piece on source position");
		}
		if(!board.piece(position).isThereAnyPossibleMove()) {  //se existe movimentos possiveis daquela peça no tabuleiro
			throw new ChessException("There is no possible moves for the chosen piece");
		}
	}
	
	private void initialSetup() {   //inicia a partida e coloca as peças no tabuleiro
		placeNewPiece('c', 1, new Rook(board, Color.WHITE));  //instancia uma peça, com o tabuleiro e cor, e sua posição
		placeNewPiece('c', 2, new Rook(board, Color.WHITE));
		placeNewPiece('d', 2, new Rook(board, Color.WHITE));	
		placeNewPiece('e', 2, new Rook(board, Color.WHITE));
		placeNewPiece('e', 1, new Rook(board, Color.WHITE));
		placeNewPiece('d', 1, new King(board, Color.WHITE));
		
		placeNewPiece('c', 7, new Rook(board, Color.BLACK));
		placeNewPiece('c', 8, new Rook(board, Color.BLACK));
		placeNewPiece('d', 7, new Rook(board, Color.BLACK));
		placeNewPiece('e', 7, new Rook(board, Color.BLACK));
		placeNewPiece('e', 8, new Rook(board, Color.BLACK));
		placeNewPiece('d', 8, new King(board, Color.BLACK));
	
	}
	
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
	}
}

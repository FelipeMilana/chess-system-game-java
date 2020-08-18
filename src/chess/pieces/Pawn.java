package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {

	//association
	private ChessMatch chessMatch;
	
	//constructors
	public Pawn(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
	}
	
	//methods
	@Override
	public String toString() {
		return "P";
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];  //criou-se uma matriz de booleanos da mesma dimensao do tabuliro, cheia de falso inicialmente
		
		Position p = new Position(0, 0); //seta um objeto p do tipo Position, na linha 0, coluna 0.
		
		if(getColor() == Color.WHITE) {  //se o peao for branco
			
			p.setValues(position.getRow() - 1, position.getColumn());  //sobe uma posição
			if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { //se a posição existir e nao houver peça
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			p.setValues(position.getRow() - 2, position.getColumn());  //sobe duas posições
			Position p2 = new Position(position.getRow() - 1, position.getColumn());  //sobe uma posição
			if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			p.setValues(position.getRow() - 1, position.getColumn() - 1);  //noroeste
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)) { //se a posição existir e houver uma peça inimiga
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			p.setValues(position.getRow() - 1, position.getColumn() + 1);  //nordeste
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)) { //se a posição existir e houver uma peça inimiga
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			//special move enPassant
			if(position.getRow() == 3) {
				Position left = new Position(position.getRow(), position.getColumn() - 1);
				if(getBoard().positionExists(left) && isThereOpponentPiece(left) && getBoard().piece(left) == chessMatch.getEnPassantVulnerable()) {
					mat[left.getRow() - 1][left.getColumn()] = true;
				}
				
				Position right = new Position(position.getRow(), position.getColumn() + 1);
				if(getBoard().positionExists(right) && isThereOpponentPiece(right) && getBoard().piece(right) == chessMatch.getEnPassantVulnerable()) {
					mat[right.getRow() - 1][right.getColumn()] = true;
				}
			}
		}
		else {  //se o peão for preto
			
			p.setValues(position.getRow() + 1, position.getColumn());  //sobe uma posição
			if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { //se a posição existir e nao houver peça
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			p.setValues(position.getRow() + 2, position.getColumn());  //sobe duas posições
			Position p2 = new Position(position.getRow() + 1, position.getColumn()); //sobe uma posição
			if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			p.setValues(position.getRow() + 1, position.getColumn() - 1);  //sudoeste
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)) { //se a posição existir e houver uma peça inimiga
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			p.setValues(position.getRow() + 1, position.getColumn() + 1);  //sudeste
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)) { //se a posição existir e houver uma peça inimiga
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			//special move enPassant
			if(position.getRow() == 4) {
				Position left = new Position(position.getRow(), position.getColumn() - 1);
				if(getBoard().positionExists(left) && isThereOpponentPiece(left) && getBoard().piece(left) == chessMatch.getEnPassantVulnerable()) {
					mat[left.getRow() + 1][left.getColumn()] = true;
				}
				
				Position right = new Position(position.getRow(), position.getColumn() + 1);
				if(getBoard().positionExists(right) && isThereOpponentPiece(right) && getBoard().piece(right) == chessMatch.getEnPassantVulnerable()) {
					mat[right.getRow() + 1][right.getColumn()] = true;
				}
			}
		}
		return mat;
	}
}

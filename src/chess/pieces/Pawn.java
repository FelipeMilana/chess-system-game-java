package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {

	//constructors
	public Pawn(Board board, Color color) {
		super(board, color);
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
		}
		return mat;
	}
}

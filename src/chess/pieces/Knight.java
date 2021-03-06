package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Knight extends ChessPiece {

	//constructors
	public Knight(Board board, Color color) {
		super(board, color);
	}

	//methods
	
	@Override
	public String toString() {
		return "N";
	}
	
	private boolean canMove(Position position) {  //se o cavalo pode mover para uma determinada posi��o
		ChessPiece p = (ChessPiece) getBoard().piece(position); //  pega o p nessa posi��o, e faz o downcasting de Piece para ChessPiece
		return p == null || p.getColor() != getColor();  //retorna verdadeiro se tiver uma pe�a inimiga ou tiver vazia
	}
	
	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];  //criou-se uma matriz de booleanos da mesma dimensao do tabuliro, cheia de falso inicialmente
		
		Position p = new Position(0, 0);  //posi��o auxiliar
		
		
		p.setValues(position.getRow() - 1, position.getColumn() - 2);  //pega a linha e a coluna da pe�a rei
		if(getBoard().positionExists(p) && canMove(p)) {  //pode mover para essa posi��o
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		p.setValues(position.getRow() - 1, position.getColumn() +2);  //pega a linha e a coluna da pe�a rei
		if(getBoard().positionExists(p) && canMove(p)) {  //pode mover para essa posi��o
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		p.setValues(position.getRow() - 2, position.getColumn() - 1);  //pega a linha e a coluna da pe�a rei
		if(getBoard().positionExists(p) && canMove(p)) {  //pode mover para essa posi��o
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		p.setValues(position.getRow() - 2, position.getColumn() + 1);  //pega a linha e a coluna da pe�a rei
		if(getBoard().positionExists(p) && canMove(p)) {  //pode mover para essa posi��o
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		p.setValues(position.getRow() + 1, position.getColumn() + 2);  //pega a linha e a coluna da pe�a rei
		if(getBoard().positionExists(p) && canMove(p)) {  //pode mover para essa posi��o
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		p.setValues(position.getRow() + 1, position.getColumn() - 2);  //pega a linha e a coluna da pe�a rei
		if(getBoard().positionExists(p) && canMove(p)) {  //pode mover para essa posi��o
			mat[p.getRow()][p.getColumn()] = true;
		}
	
		p.setValues(position.getRow() + 2, position.getColumn() - 1);  //pega a linha e a coluna da pe�a rei
		if(getBoard().positionExists(p) && canMove(p)) {  //pode mover para essa posi��o
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		p.setValues(position.getRow() + 2, position.getColumn() + 1);  //pega a linha e a coluna da pe�a rei
		if(getBoard().positionExists(p) && canMove(p)) {  //pode mover para essa posi��o
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		return mat;
	}

}

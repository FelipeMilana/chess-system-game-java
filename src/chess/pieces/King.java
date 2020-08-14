package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {

	//constructors
	public King(Board board, Color color) {  
		super(board, color);
	}
	
	//methods
	@Override
	public String toString() {
		return "K";
	}
	
	private boolean canMove(Position position) {  //se o rei pode mover para uma determinada posição
		ChessPiece p = (ChessPiece) getBoard().piece(position); //  pega o p nessa posição, e faz o downcasting de Piece para ChessPiece
		return p == null || p.getColor() != getColor();  //retorna verdadeiro se tiver uma peça inimiga ou tiver vazia
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];  //criou-se uma matriz de booleanos da mesma dimensao do tabuliro, cheia de falso inicialmente
		
		Position p = new Position(0, 0);  //posição auxiliar
		
		//above
		p.setValues(position.getRow() - 1, position.getColumn());  //pega a linha e a coluna da peça rei
		if(getBoard().positionExists(p) && canMove(p)) {  //pode mover para essa posição
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//below
		p.setValues(position.getRow() + 1, position.getColumn());  //pega a linha e a coluna da peça rei
		if(getBoard().positionExists(p) && canMove(p)) {  //pode mover para essa posição
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//left
		p.setValues(position.getRow(), position.getColumn() - 1);  //pega a linha e a coluna da peça rei
		if(getBoard().positionExists(p) && canMove(p)) {  //pode mover para essa posição
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//right
		p.setValues(position.getRow(), position.getColumn() + 1);  //pega a linha e a coluna da peça rei
		if(getBoard().positionExists(p) && canMove(p)) {  //pode mover para essa posição
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//nw(noroeste)
		p.setValues(position.getRow() - 1, position.getColumn() - 1);  //pega a linha e a coluna da peça rei
		if(getBoard().positionExists(p) && canMove(p)) {  //pode mover para essa posição
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//ne(nordeste)
		p.setValues(position.getRow() - 1, position.getColumn() + 1);  //pega a linha e a coluna da peça rei
		if(getBoard().positionExists(p) && canMove(p)) {  //pode mover para essa posição
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//sw(sudoeste)
		p.setValues(position.getRow() + 1, position.getColumn() - 1);  //pega a linha e a coluna da peça rei
		if(getBoard().positionExists(p) && canMove(p)) {  //pode mover para essa posição
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//se(sudeste)
		p.setValues(position.getRow() + 1, position.getRow() + 1);  //pega a linha e a coluna da peça rei
		if(getBoard().positionExists(p) && canMove(p)) {  //pode mover para essa posição
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		return mat;
	}
}

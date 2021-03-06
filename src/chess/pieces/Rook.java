package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Rook extends ChessPiece{

	//contructor
	public Rook(Board board, Color color) {
		super(board, color);
	}

	//methods
	@Override
	public String toString() {
		return "R";
	}
	
	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];  //criou-se uma matriz de booleanos da mesma dimensao do tabuliro, cheia de falso inicialmente
		
		Position p = new Position(0, 0); //seta um objeto p do tipo Position, na linha 0, coluna 0.
		
		//above
		p.setValues(position.getRow() - 1, position.getColumn());  //pega a posi��o da pe�a, objeto position vem da classe piece
		
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {  //enquanto existir a posi��o e nao tiver pe�a la
			mat[p.getRow()][p.getColumn()] = true;
			p.setRow(p.getRow() - 1);
		}
		if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {  //se tiver pe�a na posi��o p, e a pe�a for diferente de nulo, e com cor diferente da sua
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//left
		p.setValues(position.getRow(), position.getColumn() - 1);  //pega a posi��o da pe�a, objeto position vem da classe piece
		
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {  //enquanto existir a posi��o e nao tiver pe�a la
			mat[p.getRow()][p.getColumn()] = true;
			p.setColumn(p.getColumn() - 1);
		}
		if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {  //se tiver pe�a na posi��o p, e a pe�a for diferente de nulo, e com cor diferente da sua
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//right
		p.setValues(position.getRow(), position.getColumn() + 1);  //pega a posi��o da pe�a, objeto position vem da classe piece
		
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {  //enquanto existir a posi��o e nao tiver pe�a la
			mat[p.getRow()][p.getColumn()] = true;
			p.setColumn(p.getColumn() + 1);
		}
		if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {  //se tiver pe�a na posi��o p, e a pe�a for diferente de nulo, e com cor diferente da sua
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//below
		p.setValues(position.getRow() + 1, position.getColumn());  //pega a posi��o da pe�a, objeto position vem da classe piece
		
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {  //enquanto existir a posi��o e nao tiver pe�a la
			mat[p.getRow()][p.getColumn()] = true;
			p.setRow(p.getRow() + 1);
		}
		if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {  //se tiver pe�a na posi��o p, e a pe�a for diferente de nulo, e com cor diferente da sua
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		return mat;  
	}
}

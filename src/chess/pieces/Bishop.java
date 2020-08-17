package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Bishop extends ChessPiece{

	//constructors
	public Bishop(Board board, Color color) {
		super(board, color);
	}

	//methods
	
	@Override
	public String toString() {
		return "B";
	}
	
	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];  //criou-se uma matriz de booleanos da mesma dimensao do tabuliro, cheia de falso inicialmente
		
		Position p = new Position(0, 0); //seta um objeto p do tipo Position, na linha 0, coluna 0.
		
		//nw (noroeste)
		p.setValues(position.getRow() - 1, position.getColumn() - 1);  //pega a posição da peça, objeto position vem da classe piece
		
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {  //enquanto existir a posição e nao tiver peça la
			mat[p.getRow()][p.getColumn()] = true;
			p.setValues(p.getRow() - 1, p.getColumn() - 1);  //para ir subindo os valores de linha e coluna
		}
		if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {  //se tiver peça na posição p, e a peça for diferente de nulo, e com cor diferente da sua
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//ne (nordeste)
		p.setValues(position.getRow() - 1, position.getColumn() + 1);  //pega a posição da peça, objeto position vem da classe piece
		
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {  //enquanto existir a posição e nao tiver peça la
			mat[p.getRow()][p.getColumn()] = true;
			p.setValues(p.getRow() - 1, p.getColumn() + 1);
		}
		if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {  //se tiver peça na posição p, e a peça for diferente de nulo, e com cor diferente da sua
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//se (sudeste)
		p.setValues(position.getRow() + 1, position.getColumn() + 1);  //pega a posição da peça, objeto position vem da classe piece
		
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {  //enquanto existir a posição e nao tiver peça la
			mat[p.getRow()][p.getColumn()] = true;
			p.setValues(p.getRow() + 1, p.getColumn() + 1);  //ir subindo os valores
		}
		if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {  //se tiver peça na posição p, e a peça for diferente de nulo, e com cor diferente da sua
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//sw (sudoeste)
		p.setValues(position.getRow() + 1, position.getColumn() - 1);  //pega a posição da peça, objeto position vem da classe piece
		
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {  //enquanto existir a posição e nao tiver peça la
			mat[p.getRow()][p.getColumn()] = true;
			p.setValues(p.getRow() + 1, p.getColumn() - 1);
		}
		if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {  //se tiver peça na posição p, e a peça for diferente de nulo, e com cor diferente da sua
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		return mat;  
	}

}

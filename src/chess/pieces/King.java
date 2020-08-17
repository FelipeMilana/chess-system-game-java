package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {
	
	//association
	private ChessMatch chessMatch;

	//constructors
	public King(Board board, Color color, ChessMatch chessMatch) {  
		super(board, color);
		this.chessMatch = chessMatch;
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
	
	private boolean testRookCastling(Position position) {
		ChessPiece p = (ChessPiece)getBoard().piece(position); //retorna a peça dessa posição.
		return p != null && p instanceof Rook && p.getColor() == getColor() && p.getMoveCount() == 0;  //se a peça n for nula, se for uma torre, se a cor for igual ao do rei, e os movimentos da peça forem 0
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
		p.setValues(position.getRow() + 1, position.getColumn() + 1);  //pega a linha e a coluna da peça rei
		if(getBoard().positionExists(p) && canMove(p)) {  //pode mover para essa posição
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//special move castling
		if(getMoveCount() == 0 && !chessMatch.getCheck()) {  //se o rei nao tiver sido mexido, e nao estiver em check
			//roque pequeno
			Position posT1 = new Position(position.getRow(), position.getColumn() + 3);  //define a posição da torre
			if(testRookCastling(posT1)) {   //confirma as condições da torre
				Position p1 = new Position(position.getRow(), position.getColumn() + 1); //pega a primeira casa da direita do rei
				Position p2 = new Position(position.getRow(), position.getColumn() + 2);  //pega a segunda casa da direita do rei
				
				if(getBoard().piece(p1) == null && getBoard().piece(p2) == null) {  //se ambas as posições estiverem nula
					mat[position.getRow()][position.getColumn() + 2] = true;  //a segunda casa da direita do rei, se torna um movimento possivel
				}
			}
			
			//roque grande
			Position posT2 = new Position(position.getRow(), position.getColumn() - 4);  //define a posição da torre
			if(testRookCastling(posT2)) {   //confirma as condições da torre
				Position p1 = new Position(position.getRow(), position.getColumn() - 1); //pega a primeira casa da esquerda do rei
				Position p2 = new Position(position.getRow(), position.getColumn() - 2);  //pega a segunda casa da esquerda do rei
				Position p3 = new Position(position.getRow(), position.getColumn() - 3);   //pega a terceira casa da esquerda do rei
				
				if(getBoard().piece(p1) == null && getBoard().piece(p2) == null && getBoard().piece(p3) == null) {  //se ambas as posições estiverem nula
					mat[position.getRow()][position.getColumn() - 2] = true;  //a segunda casa da esquerda do rei, se torna um movimento possivel
				}
			}
		}		
		return mat;
	}
}

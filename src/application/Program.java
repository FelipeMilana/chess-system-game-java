package application;

import chess.ChessMatch;

public class Program {

	public static void main(String[] args) {
		
		ChessMatch chessMatch = new ChessMatch();
		UI.printBoard(chessMatch.getPieces());  //metodo estatico, e ira imprimir as pe�as de uma partida de xadrez
	}
}

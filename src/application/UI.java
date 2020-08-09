package application;

import chess.ChessPiece;

public class UI {

	public static void printBoard(ChessPiece[][] pieces) {   //metodo para imprimir a matriz de peça
		for(int i = 0; i < pieces.length; i++) {  //percorre a linha da matriz
			System.out.print((8 - i) + " ");
			for(int j = 0; j < pieces[i].length; j++) { //percorre as colunas de cada linha da matriz
				printPiece(pieces[i][j]); //imprime uma peça
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}
	
	
	public static void printPiece(ChessPiece piece) {
		if(piece == null) {
			System.out.print("-");
		}
		else {
			System.out.print(piece.toString());
		}
		System.out.print(" ");
	}
}

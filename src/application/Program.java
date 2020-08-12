package application;

import java.util.InputMismatchException;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		ChessMatch chessMatch = new ChessMatch();
		
		while(true) {
			try {
				UI.clearScreen();
				UI.printBoard(chessMatch.getPieces());  //metodo estatico, e ira imprimir as peças de uma partida de xadrez
				System.out.println();
				System.out.print("Source ");
				ChessPosition source = UI.readChessPosition(sc);
				
				System.out.println();
				System.out.print("Target: ");
				ChessPosition target = UI.readChessPosition(sc);
				
				ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
			}
			catch(ChessException e) {    //para o programa não terminar, tratando as exceções, ira tratar boardException, mas n tem mensagem
				System.out.println(e.getMessage());
				sc.nextLine();  //para aguardar um enter e começar dnv
			}
			
			catch(InputMismatchException e) {
				System.out.println(e.getMessage());
				System.out.println();   
			}
		}
	}
}

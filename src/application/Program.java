package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		ChessMatch chessMatch = new ChessMatch();
		List<ChessPiece> captured = new ArrayList<ChessPiece>(); //lista vazia de peças capturada
		
		while(!chessMatch.getCheckMate()) {  //enquanto nao tem o checkMate
			try {
				UI.clearScreen();
				UI.printMatch(chessMatch, captured);  //metodo estatico, e ira imprimir as peças de uma partida de xadrez
				System.out.println();
				System.out.print("Source: ");
				ChessPosition source = UI.readChessPosition(sc);
				
				boolean[][] possibleMoves = chessMatch.possibleMoves(source);  //aloca a matriz contendo os movimentos possiveis daquela posição na matriz possibleMoves
				UI.clearScreen();
				UI.printBoard(chessMatch.getPieces(), possibleMoves);  //sobrecarga de metodo, e dessa vez servira para mostrar os movimentos possiveis daquela peça na posição escolhida
				
				System.out.println();
				System.out.print("Target: ");
				ChessPosition target = UI.readChessPosition(sc);
				
				ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
				
				if(capturedPiece != null) {
					captured.add(capturedPiece);  //adiciona a peça capturada na lista se ela nao for nula
				}
				
				if(chessMatch.getPromoted() != null) {
					System.out.print("Enter piece for promotion(B/N/R/Q): ");
					String type = sc.nextLine();
					chessMatch.replacePromotedPiece(type);  //adiciona a peça no lugar
				}
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
		
		UI.clearScreen();
		UI.printMatch(chessMatch, captured);  //mostra a partida finalizada
	}
}

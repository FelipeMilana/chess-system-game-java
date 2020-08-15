package application;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;

public class UI {

	//codigo de cores
	public static final String ANSI_RESET = "\u001B[0m";  // https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
	
	//limpa a tela do terminal
	public static void clearScreen() {      // https://stackoverflow.com/questions/2979383/java-clear-the-console
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}	
		
	//leitura da peça inserida pela usuario
	public static ChessPosition readChessPosition(Scanner sc) {   //le a poisção com o scanner
		try {
			String s = sc.nextLine();
			char column = s.charAt(0);
			int row = Integer.parseInt(s.substring(1)); //recorta a string s na posição 1 e transforma para inteiro
			return new ChessPosition(column, row);
		}
		catch(RuntimeException e) {
			throw new InputMismatchException("Error reading ChessPosition. Valid values are from a1 to h8");
		}
	}
	
	//imprimir a partida
	public static void printMatch(ChessMatch chessMatch, List<ChessPiece> captured) {
		printBoard(chessMatch.getPieces()); //imprime o tabuleiro
		printCapturedPieces(captured); //imprime a lista, inicialmento com a lista vazia
		System.out.println();
		System.out.println("Turn: " + chessMatch.getTurn());
		
		if(!chessMatch.getCheckMate()) {  //se nao tiver o checkMate, a partida continua
			System.out.println("Waiting player: " + chessMatch.getCurrentPlayer());
			
			if(chessMatch.getCheck()) {  //se retornar verdadeiro
				System.out.println("CHECK!");
			}
		}
		else {  //senao mostra o campeao
			System.out.println("CHECKMATE!");
			System.out.println("WINNER: " + chessMatch.getCurrentPlayer());
		}
	}
	
	//impressao do tabuleiro
	public static void printBoard(ChessPiece[][] pieces) {   //metodo para imprimir a matriz de peça
		for(int i = 0; i < pieces.length; i++) {  //percorre a linha da matriz
			System.out.print(ANSI_RED +(8 - i) + " " + ANSI_RESET);  //COR DOS NUMEROS
			for(int j = 0; j < pieces[i].length; j++) { //percorre as colunas de cada linha da matriz
				printPiece(pieces[i][j], false); //imprime uma peça sem cor de fundo
			}
			System.out.println();
		}
		System.out.println(ANSI_RED + "  a b c d e f g h"+ ANSI_RESET);  //cor das letras	
	}
	
	//impressao do tabuleiro com coloração para movimentos possiveis
	public static void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves) {
		for(int i = 0; i < pieces.length; i++) {  //percorre a linha da matriz
			System.out.print(ANSI_RED +(8 - i) + " " + ANSI_RESET);  //COR DOS NUMEROS
			for(int j = 0; j < pieces[i].length; j++) { //percorre as colunas de cada linha da matriz
				printPiece(pieces[i][j], possibleMoves[i][j]); //imprime uma peça e aonde for true, colore o fundo indicando um movimento possivel
			}
			System.out.println();
		}
		System.out.println(ANSI_RED + "  a b c d e f g h"+ ANSI_RESET);  //cor das letras	
	}
	
	//impressao de uma peça
	private static void printPiece(ChessPiece piece, boolean background) {  //variavel para ver se precisa colorir ou nao o fundo da peça
		if(background) {
			System.out.print(ANSI_BLUE_BACKGROUND);
		}
		if (piece == null) {
            System.out.print(ANSI_PURPLE
            		+"-" + ANSI_RESET);
        }
        else if (piece.getColor() == Color.WHITE) {
            System.out.print(ANSI_WHITE + piece.toString() + ANSI_RESET);
        }
        else {
        	System.out.print(ANSI_YELLOW + piece.toString() + ANSI_RESET);
        }
        System.out.print(" ");
	}
	
	//impressao da peça capturada
	private static void printCapturedPieces(List<ChessPiece> captured) {
		List<ChessPiece> white = captured.stream().filter(x -> x.getColor() == Color.WHITE).collect(Collectors.toList());
		List<ChessPiece> black = captured.stream().filter(x -> x.getColor() == Color.BLACK).collect(Collectors.toList());
		
		System.out.println("Captured pieces: ");
		System.out.print("White: ");
		System.out.print(ANSI_WHITE); //cor branca
		System.out.println(Arrays.toString(white.toArray()));  //imprimir a lista
		/*or  for (ChessPiece p: white) {
				System.out.print(p.toString() + " ");
		} */
		System.out.print(ANSI_RESET); //reseta a cor
		System.out.print("Black: ");
		System.out.print(ANSI_YELLOW);
		System.out.println(Arrays.toString(black.toArray()));  //imprimir a lista
		System.out.print(ANSI_RESET);
	}
}

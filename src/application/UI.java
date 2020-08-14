package application;

import java.util.InputMismatchException;

import java.util.Scanner;

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
		
	//leitura da pe�a inserida pela usuario
	public static ChessPosition readChessPosition(Scanner sc) {   //le a pois��o com o scanner
		try {
			String s = sc.nextLine();
			char column = s.charAt(0);
			int row = Integer.parseInt(s.substring(1)); //recorta a string s na posi��o 1 e transforma para inteiro
			return new ChessPosition(column, row);
		}
		catch(RuntimeException e) {
			throw new InputMismatchException("Error reading ChessPosition. Valid values are from a1 to h8");
		}
	}
	
	//impressao do tabuleiro
	public static void printBoard(ChessPiece[][] pieces) {   //metodo para imprimir a matriz de pe�a
		for(int i = 0; i < pieces.length; i++) {  //percorre a linha da matriz
			System.out.print(ANSI_RED +(8 - i) + " " + ANSI_RESET);  //COR DOS NUMEROS
			for(int j = 0; j < pieces[i].length; j++) { //percorre as colunas de cada linha da matriz
				printPiece(pieces[i][j], false); //imprime uma pe�a sem cor de fundo
			}
			System.out.println();
		}
		System.out.println(ANSI_RED + "  a b c d e f g h"+ ANSI_RESET);  //cor das letras	
	}
	
	//impressao do tabuleiro com colora��o para movimentos possiveis
	public static void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves) {
		for(int i = 0; i < pieces.length; i++) {  //percorre a linha da matriz
			System.out.print(ANSI_RED +(8 - i) + " " + ANSI_RESET);  //COR DOS NUMEROS
			for(int j = 0; j < pieces[i].length; j++) { //percorre as colunas de cada linha da matriz
				printPiece(pieces[i][j], possibleMoves[i][j]); //imprime uma pe�a e aonde for true, colore o fundo indicando um movimento possivel
			}
			System.out.println();
		}
		System.out.println(ANSI_RED + "  a b c d e f g h"+ ANSI_RESET);  //cor das letras	
	}
	
	
	private static void printPiece(ChessPiece piece, boolean background) {  //variavel para ver se precisa colorir ou nao o fundo da pe�a
		if(background) {
			System.out.print(ANSI_BLUE_BACKGROUND);
		}
		if (piece == null) {
            System.out.print("-" + ANSI_RESET);
        }
        else if (piece.getColor() == Color.WHITE) {
            System.out.print(ANSI_WHITE + piece.toString() + ANSI_RESET);
        }
        else {
        	System.out.print(ANSI_YELLOW + piece.toString() + ANSI_RESET);
        }
        System.out.print(" ");
	}
}

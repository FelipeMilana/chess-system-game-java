package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;

public class ChessMatch {

	//attributes
	private int turn;
	private boolean check; //por padrão ja começa com falso
	private boolean checkMate;  //inicialmente falso
	private List<Piece> piecesOnTheBoard = new ArrayList<Piece>();  //duas listas vazias, pode ser iniciada no construtor
	private List<Piece> capturedPieces = new ArrayList<Piece>();
	
	//association
	private Board board;
	private Color currentPlayer;  //ja esta no pacote
	
	//constructors
	public ChessMatch() { //construtor padrao, sem parametros
		board = new Board(8, 8);     //intancia o objeto board e cria um tabuleiro 8 x 8, e cria peças do tipo piece
		turn = 1;
		currentPlayer = Color.WHITE;
		initialSetup();
	}
	
	//methods
	public int getTurn() {
		return turn;
	}
	
	public Color getCurrentPlayer() {
		return currentPlayer;
	}
	
	public boolean getCheck() {
		return check;
	}
	
	public boolean getCheckMate() {
		return checkMate;
	}
	
	public ChessPiece[][] getPieces() {  //o que interessa é a peça do tipo de xadrez, nao uma peça qualquer
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];  //instnaciamos uma matriz vazia do tipo chesspiece
		for(int i = 0; i < board.getRows(); i++) {
			for(int j = 0; j < board.getColumns(); j++) {
				mat[i][j] = (ChessPiece) board.piece(i, j);  //era do tipo piece e faz o downcasting, pois chesspiece é subclasse de piece
			}
		}
		return mat;
	}
	
	public boolean[][] possibleMoves(ChessPosition sourcePosition) {
		Position position = sourcePosition.toPosition();  //conversao de ChessPosition para Position
		validateSourcePosition(position);  //valida a posição
		return board.piece(position).possibleMoves();  //acessa o tabuleiro, e retorna a peça que esta sala na matriz pieces, e acessa o metodo possibleMoves().
	}
	
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition(); //faz a conversao para position
		Position target = targetPosition.toPosition();	//faz a conversao para position
		validateSourcePosition(source);
		validateTargetPosition(source, target);
		Piece capturedPiece = makeMove(source, target); //faz o movimento e captura a peça
		
		if(testCheck(currentPlayer)) {  //verifica se o jogador se auto colocou em check
			undoMove(source, target, capturedPiece);
			throw new ChessException("You can't put yourself in check");
		}
		
		check = (testCheck(opponent(currentPlayer))) ? true : false;  //verifica se o oponente esta em check
		
		if(testCheckMate(opponent(currentPlayer))) {  //se o oponente receber o checkMate, o jogo acaba
			checkMate = true;
		}
		else {
			nextTurn();  //do contrario o turno passa
		}
		return (ChessPiece) capturedPiece;
	}
	
	private Piece makeMove(Position source, Position target) {
		ChessPiece p = (ChessPiece) board.removePiece(source); //tiro do tabuleiro a peça q eu quero mexer
		p.increaseMoveCount(); //adicioan um movimento
		Piece capturedPiece = board.removePiece(target); //tiro do tabuleiro uma possivel peça na posição de destino 
		board.placePiece(p, target); //adiciona a peça que eu queria mexer para a posição de destino
		
		if(capturedPiece != null) {  //se houver captura de peça
			piecesOnTheBoard.remove(capturedPiece);  //retira da lista de peças dispostas no tabuleiro
			capturedPieces.add(capturedPiece); //adiciona essa peça retirada do tabuleiro, na lista de peças capturadas
		}
		return capturedPiece;
	}
	
	private void undoMove(Position source, Position target, Piece capturedPiece) {
		ChessPiece p = (ChessPiece) board.removePiece(target);  //retira a peça do target
		p.decreaseMoveCount(); //subtrai um movimento
		board.placePiece(p, source);  //e coloca no source, ou seja, volta ao normal
		
		if(capturedPiece != null) { //se foi pega uma peça
			board.placePiece(capturedPiece, target);  //volta aquela peça na posição onde estava
			capturedPieces.remove(capturedPiece);  //retira a peça da lista de peças capturadas
			piecesOnTheBoard.add(capturedPiece); //poe a peça de novo na lista de peças dispostas no tabuleiro
		}
	}
	
	private void validateSourcePosition(Position position) {
		if(!board.thereIsAPiece(position)) {
			throw new ChessException("There is no piece on source position");
		}
		if(currentPlayer != ((ChessPiece)board.piece(position)).getColor()) { //precisa do downcasting pra usar o getColor, pois é da classe ChessPiece
			throw new ChessException("The chosen piece is not yours");
		}
		if(!board.piece(position).isThereAnyPossibleMove()) {  //se existe movimentos possiveis daquela peça no tabuleiro
			throw new ChessException("There is no possible moves for the chosen piece");
		}
	}
	
	private void validateTargetPosition(Position source, Position target) {
		if(!board.piece(source).possibleMove(target)) {//se na peça de origem, a posição de destino nao é um movimento possivel
			throw new ChessException("The chosen piece can't move to target position");
		}
	}
	
	private void nextTurn() {
		turn++;
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;  //expressao ternaria condicional
	}
	
	private Color opponent(Color color) {  //vai devolver o oponente de uma cor
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	private ChessPiece king(Color color) {  
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());  // filtra a lista de peças dispostas no tabuleiro, e adiciona na lista list, as peças que tenham a cor passada como argumento
		for(Piece p: list) {
			if(p instanceof King) {  //se a peça p for instanciada como rei, retorne essa peça p
				return (ChessPiece)p;
			}
		}
		throw new IllegalStateException("There is no" + color + "king on the board"); // essa exceção é para não ocorrer, dessa forma, não será tratada.
	}
	
	private boolean testCheck(Color color) {
		Position kingPosition = king(color).getChessPosition().toPosition(); //pega a posição do rei no formato de matriz
		List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList());  //cria uma lista com as peças no tabuleiro com a cor oponente do rei selecionado anteriormente
		for(Piece p: opponentPieces) {
			boolean[][] mat = p.possibleMoves();  //adiciona na matriz mat, a matriz de movimentos possiveis daquela peça
			if(mat[kingPosition.getRow()][kingPosition.getColumn()]) {  //se na posição do rei, tiver alguma peça com movimento possivel, entra no if
				return true; // retorna que o teste de check deu verdadeiro.
			}
		}
		return false;
	}
	
	private boolean testCheckMate(Color color) {
		if(!testCheck(color)) {
			return false;
		}
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList()); //adiciona na lista list, todas as peças da mesma cor da passada como argumento
		for(Piece p: list) {  //percorre a lista
			boolean[][] mat = p.possibleMoves();  //adiciona na matriz mat a matriz de movimentos possiveis de uma determinada peça
			
			for(int i = 0; i < board.getRows(); i++) {   //percorrendo a matriz
				for(int j = 0; j < board.getColumns(); j++) {
					if(mat[i][j]) {  //se for true
						Position source = ((ChessPiece)p).getChessPosition().toPosition();  //pega a posição da peça p na matriz
						Position target = new Position(i, j);  //pega a posição de teste
						Piece capturedPiece = makeMove(source, target);  //faz o movimento da peça p para a posição de teste
						
						boolean testCheck = testCheck(color);  //ve se o rei esta em check
						
						undoMove(source, target, capturedPiece); //desfaz o movimento
						
						if(!testCheck) {  //se o teste de check retornar falso, quer dizer que aquele movimento tira o check;
							return false;
						}
						
					}
				}
			} 
			
		}
		return true;
	}
	
	private void initialSetup() {   //inicia a partida e coloca as peças no tabuleiro
		placeNewPiece('a', 1, new Rook(board, Color.WHITE));  //instancia uma peça, com o tabuleiro e cor, e sua posição
		placeNewPiece('b', 1, new Knight(board, Color.WHITE)); 
		placeNewPiece('c', 1, new Bishop(board, Color.WHITE)); 
		placeNewPiece('d', 1, new Queen(board, Color.WHITE)); 
        placeNewPiece('e', 1, new King(board, Color.WHITE));
    	placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
    	placeNewPiece('g', 1, new Knight(board, Color.WHITE)); 
        placeNewPiece('h', 1, new Rook(board, Color.WHITE));
        placeNewPiece('a', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('b', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('c', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('d', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('e', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('f', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('g', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('h', 2, new Pawn(board, Color.WHITE));

        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
        placeNewPiece('b', 8, new Knight(board, Color.BLACK)); 
        placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('d', 8, new Queen(board, Color.BLACK)); 
        placeNewPiece('e', 8, new King(board, Color.BLACK));
        placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('g', 8, new Knight(board, Color.BLACK)); 
        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
        placeNewPiece('a', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('b', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('c', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('d', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('e', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('f', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('g', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('h', 7, new Pawn(board, Color.BLACK));
	}
	
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		piecesOnTheBoard.add(piece); //coloca a peça na lista de peças dispostas no tabuleiro
		
	}
}

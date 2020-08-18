package chess;

import java.security.InvalidParameterException;
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
	private int turn;  //inicia 0
	private boolean check; //por padr�o ja come�a com falso
	private boolean checkMate;  //inicialmente falso
	private List<Piece> piecesOnTheBoard = new ArrayList<Piece>();  //duas listas vazias, pode ser iniciada no construtor
	private List<Piece> capturedPieces = new ArrayList<Piece>();
	
	//association
	private Board board;  //inicialmente nulo 
	private Color currentPlayer;  //ja esta no pacote
	private ChessPiece enPassantVulnerable;   //inicialmente ja esta nulo 
	private ChessPiece promoted; //inicialmente nulo 
	
	//constructors
	public ChessMatch() { //construtor padrao, sem parametros
		board = new Board(8, 8);     //intancia o objeto board e cria um tabuleiro 8 x 8, e cria pe�as do tipo piece
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
	
	public ChessPiece getEnPassantVulnerable() {
		return enPassantVulnerable;
	}
	
	public ChessPiece getPromoted() {
		return promoted;
	}
	
	public ChessPiece[][] getPieces() {  //o que interessa � a pe�a do tipo de xadrez, nao uma pe�a qualquer
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];  //instnaciamos uma matriz vazia do tipo chesspiece
		for(int i = 0; i < board.getRows(); i++) {
			for(int j = 0; j < board.getColumns(); j++) {
				mat[i][j] = (ChessPiece) board.piece(i, j);  //era do tipo piece e faz o downcasting, pois chesspiece � subclasse de piece
			}
		}
		return mat;
	}
	
	public boolean[][] possibleMoves(ChessPosition sourcePosition) {
		Position position = sourcePosition.toPosition();  //conversao de ChessPosition para Position
		validateSourcePosition(position);  //valida a posi��o
		return board.piece(position).possibleMoves();  //acessa o tabuleiro, e retorna a pe�a que esta sala na matriz pieces, e acessa o metodo possibleMoves().
	}
	
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition(); //faz a conversao para position
		Position target = targetPosition.toPosition();	//faz a conversao para position
		validateSourcePosition(source);
		validateTargetPosition(source, target);
		Piece capturedPiece = makeMove(source, target); //faz o movimento e captura a pe�a
		
		if(testCheck(currentPlayer)) {  //verifica se o jogador se auto colocou em check
			undoMove(source, target, capturedPiece);
			throw new ChessException("You can't put yourself in check");
		}
		
		ChessPiece movedPiece = (ChessPiece)board.piece(target);  //recebe a pe�a que esta na posi��o target, que � a pe�a que estava na posi��o source
		
		//special move promotion
		promoted = null; //assegurar que � um novo teste
		if(movedPiece instanceof Pawn) {
			if((movedPiece.getColor() == Color.WHITE && target.getRow() == 0) || (movedPiece.getColor() == Color.BLACK && target.getRow() == 7)) {  //se a pe�a for branca e chegar no final, ou se a pe�a for preta e chegar no final
				promoted = (ChessPiece)board.piece(target); //o peao � a pe�a promovida
				promoted = replacePromotedPiece("Q");  //por padrao come�a trocando pela rainha
			}
		}
		check = (testCheck(opponent(currentPlayer))) ? true : false;  //verifica se o oponente esta em check
		
		if(testCheckMate(opponent(currentPlayer))) {  //se o oponente receber o checkMate, o jogo acaba
			checkMate = true;
		}
		else {
			nextTurn();  //do contrario o turno passa
		}
		
		//special move enPassant
		if(movedPiece instanceof Pawn && (target.getRow() == source.getRow() + 2 || target.getRow() == source.getRow() - 2) ) {
			enPassantVulnerable = movedPiece;
		}
		else {
			enPassantVulnerable = null;
		}
		
		return (ChessPiece) capturedPiece;
	}
	
	public ChessPiece replacePromotedPiece(String type) {
		if(promoted == null) {  //se for nulo 
			throw new IllegalStateException("There is no piece to be promoted");
		}
		if(!type.equals("B") && !type.equals("N") && !type.equals("R") && !type.equals("Q")) {  //usa o equals pq o String � do tipo classe, ou seja, compara objetos
			throw new InvalidParameterException("Invalid type for promotion");
		}
		
		Position pos = promoted.getChessPosition().toPosition();  //retorna a posi��o da pe�a promovida
		Piece p = board.removePiece(pos);  //removeu a pe�a dessa posi��o e armazenou em p
		piecesOnTheBoard.remove(p);  //remove a pe�a da lista de pe�as dispostas no tabuleiro
		
		ChessPiece newPiece = newPiece(type, promoted.getColor());  //instancia uma pe�a de acordo com a string informada e a cor
		board.placePiece(newPiece, pos); //adiciona a pe�a no tabuleiro 
		piecesOnTheBoard.add(newPiece); //adiciona a pe�a na lista de pe�as dispostas no tabuleiro
		
		return newPiece;
	}
	
	private ChessPiece newPiece(String type, Color color) {
		if(type.equals("B")) {
			return new Bishop(board, color);
		}
		if(type.equals("N")) {
			return new Knight(board, color);
		}
		if(type.equals("R")) {
			return new Rook(board, color);
		}
		return new Queen(board, color);
	}
	
	private Piece makeMove(Position source, Position target) {
		ChessPiece p = (ChessPiece) board.removePiece(source); //tiro do tabuleiro a pe�a q eu quero mexer
		p.increaseMoveCount(); //adicioan um movimento
		Piece capturedPiece = board.removePiece(target); //tiro do tabuleiro uma possivel pe�a na posi��o de destino 
		board.placePiece(p, target); //adiciona a pe�a que eu queria mexer para a posi��o de destino
		
		if(capturedPiece != null) {  //se houver captura de pe�a
			piecesOnTheBoard.remove(capturedPiece);  //retira da lista de pe�as dispostas no tabuleiro
			capturedPieces.add(capturedPiece); //adiciona essa pe�a retirada do tabuleiro, na lista de pe�as capturadas
		}
		
		//special move castling king side rook
		if(p instanceof King & target.getColumn() == source.getColumn() + 2) {  //roque pequeno
			Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
			Position targetT = new Position(source.getRow(), source.getColumn() + 1);
			ChessPiece rook = (ChessPiece)board.removePiece(sourceT);
			board.placePiece(rook, targetT);  //move a torre manualmente
			rook.increaseMoveCount();
		}
		
		if(p instanceof King & target.getColumn() == source.getColumn() - 2) {  //roque pequeno
			Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
			Position targetT = new Position(source.getRow(), source.getColumn() - 1);
			ChessPiece rook = (ChessPiece)board.removePiece(sourceT);
			board.placePiece(rook, targetT);  //move a torre manualmente
			rook.increaseMoveCount();
		}
		
		//special move enPassant
		if(p instanceof Pawn) {  //testa se a pe�a selecionada foi um pe�o
			if(source.getColumn() != target.getColumn() && capturedPiece == null) {  //se o pe�o mexeu na diagonal e nao capturou pe�a
				Position pawnPosition;
				if(p.getColor() == Color.WHITE) {
					pawnPosition = new Position(target.getRow() + 1, target.getColumn());  //o pe�o que deve ser retirado esta em baixo da pe�a
				}
				else {
					pawnPosition = new Position(target.getRow() - 1, target.getColumn());  //o pe�o que deve ser retirado esta em cima da pe�a 
				}
				capturedPiece = board.removePiece(pawnPosition);  //retira o pe�o do tabuleiro
				capturedPieces.add(capturedPiece);  //adiciona a lista de pe�as capturadas
				piecesOnTheBoard.remove(capturedPiece);  //retira da lista de pe�as dispostas no tabuleiro
			}
		}
		return capturedPiece;
	}
	
	private void undoMove(Position source, Position target, Piece capturedPiece) {
		ChessPiece p = (ChessPiece) board.removePiece(target);  //retira a pe�a do target
		p.decreaseMoveCount(); //subtrai um movimento
		board.placePiece(p, source);  //e coloca no source, ou seja, volta ao normal
		
		if(capturedPiece != null) { //se foi pega uma pe�a
			board.placePiece(capturedPiece, target);  //volta aquela pe�a na posi��o onde estava
			capturedPieces.remove(capturedPiece);  //retira a pe�a da lista de pe�as capturadas
			piecesOnTheBoard.add(capturedPiece); //poe a pe�a de novo na lista de pe�as dispostas no tabuleiro
		}
		
		//special move castling king side rook
		if(p instanceof King & target.getColumn() == source.getColumn() + 2) {  //roque pequeno
			Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
			Position targetT = new Position(source.getRow(), source.getColumn() + 1);
			ChessPiece rook = (ChessPiece)board.removePiece(targetT);  //desfaz o movimento
			board.placePiece(rook, sourceT);  //move a torre manualmente
			rook.decreaseMoveCount();
		}
		
		if(p instanceof King & target.getColumn() == source.getColumn() - 2) {  //roque pequeno
			Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
			Position targetT = new Position(source.getRow(), source.getColumn() - 1);
			ChessPiece rook = (ChessPiece)board.removePiece(targetT);  //desfaz o movimento
			board.placePiece(rook, sourceT);  //move a torre manualmente
			rook.decreaseMoveCount();
		}
		
		//special move enPassant
		if(p instanceof Pawn) {  //testa se a pe�a selecionada foi um pe�o
			if(source.getColumn() != target.getColumn() && capturedPiece == enPassantVulnerable) {  //se o pe�o mexeu na diagonal e a pe�a capturada foi um pe�o vulneravel ao en Passant
				ChessPiece pawn = (ChessPiece)board.removePiece(target);  //remove o pe�o da posi��o errada
				Position pawnPosition;
				if(p.getColor() == Color.WHITE) {
					pawnPosition = new Position(3, target.getColumn());  //a posi��o certa
				}
				else {
					pawnPosition = new Position(4, target.getColumn());  //a posi��o certa 
				}
				
				board.placePiece(pawn, pawnPosition); //coloca o pe�o retirado da posi��o errada na posi��o certa.
			}
		}
	}
	
	private void validateSourcePosition(Position position) {
		if(!board.thereIsAPiece(position)) {
			throw new ChessException("There is no piece on source position");
		}
		if(currentPlayer != ((ChessPiece)board.piece(position)).getColor()) { //precisa do downcasting pra usar o getColor, pois � da classe ChessPiece
			throw new ChessException("The chosen piece is not yours");
		}
		if(!board.piece(position).isThereAnyPossibleMove()) {  //se existe movimentos possiveis daquela pe�a no tabuleiro
			throw new ChessException("There is no possible moves for the chosen piece");
		}
	}
	
	private void validateTargetPosition(Position source, Position target) {
		if(!board.piece(source).possibleMove(target)) {//se na pe�a de origem, a posi��o de destino nao � um movimento possivel
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
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());  // filtra a lista de pe�as dispostas no tabuleiro, e adiciona na lista list, as pe�as que tenham a cor passada como argumento
		for(Piece p: list) {
			if(p instanceof King) {  //se a pe�a p for instanciada como rei, retorne essa pe�a p
				return (ChessPiece)p;
			}
		}
		throw new IllegalStateException("There is no" + color + "king on the board"); // essa exce��o � para n�o ocorrer, dessa forma, n�o ser� tratada.
	}
	
	private boolean testCheck(Color color) {
		Position kingPosition = king(color).getChessPosition().toPosition(); //pega a posi��o do rei no formato de matriz
		List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList());  //cria uma lista com as pe�as no tabuleiro com a cor oponente do rei selecionado anteriormente
		for(Piece p: opponentPieces) {
			boolean[][] mat = p.possibleMoves();  //adiciona na matriz mat, a matriz de movimentos possiveis daquela pe�a
			if(mat[kingPosition.getRow()][kingPosition.getColumn()]) {  //se na posi��o do rei, tiver alguma pe�a com movimento possivel, entra no if
				return true; // retorna que o teste de check deu verdadeiro.
			}
		}
		return false;
	}
	
	private boolean testCheckMate(Color color) {
		if(!testCheck(color)) {
			return false;
		}
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList()); //adiciona na lista list, todas as pe�as da mesma cor da passada como argumento
		for(Piece p: list) {  //percorre a lista
			boolean[][] mat = p.possibleMoves();  //adiciona na matriz mat a matriz de movimentos possiveis de uma determinada pe�a
			
			for(int i = 0; i < board.getRows(); i++) {   //percorrendo a matriz
				for(int j = 0; j < board.getColumns(); j++) {
					if(mat[i][j]) {  //se for true
						Position source = ((ChessPiece)p).getChessPosition().toPosition();  //pega a posi��o da pe�a p na matriz
						Position target = new Position(i, j);  //pega a posi��o de teste
						Piece capturedPiece = makeMove(source, target);  //faz o movimento da pe�a p para a posi��o de teste
						
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
	
	private void initialSetup() {   //inicia a partida e coloca as pe�as no tabuleiro
		placeNewPiece('a', 1, new Rook(board, Color.WHITE));  //instancia uma pe�a, com o tabuleiro e cor, e sua posi��o
		placeNewPiece('b', 1, new Knight(board, Color.WHITE)); 
		placeNewPiece('c', 1, new Bishop(board, Color.WHITE)); 
		placeNewPiece('d', 1, new Queen(board, Color.WHITE)); 
        placeNewPiece('e', 1, new King(board, Color.WHITE, this));  //referencia que a partida � essa
    	placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
    	placeNewPiece('g', 1, new Knight(board, Color.WHITE)); 
        placeNewPiece('h', 1, new Rook(board, Color.WHITE));
        placeNewPiece('a', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('b', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('c', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('d', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('e', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('f', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('g', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('h', 2, new Pawn(board, Color.WHITE, this));

        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
        placeNewPiece('b', 8, new Knight(board, Color.BLACK)); 
        placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('d', 8, new Queen(board, Color.BLACK)); 
        placeNewPiece('e', 8, new King(board, Color.BLACK, this));
        placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('g', 8, new Knight(board, Color.BLACK)); 
        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
        placeNewPiece('a', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('b', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('c', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('d', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('e', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('f', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('g', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('h', 7, new Pawn(board, Color.BLACK, this));
	}
	
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		piecesOnTheBoard.add(piece); //coloca a pe�a na lista de pe�as dispostas no tabuleiro
		
	}
}

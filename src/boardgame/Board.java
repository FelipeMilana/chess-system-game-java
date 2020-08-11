package boardgame;

public class Board {

	//attribution
	private int rows;        
	private int columns;
	
	//association
	private Piece[][] pieces; //criamos uma variavel que vai ser uma matriz de pe�as
	 
	//constructors
	public Board(int rows, int columns) { //cria um tabuleiro, com uma matriz de pe�as vazias
		if(rows < 1 || columns < 1) {
			throw new BoardException("Error creating board: thre must be at least 1 row and 1 column ");
		}
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];  //instanciamos uma matriz nula, que tera o numero de linhas e colunas falados, nao � o construtor da classe piece
	}

	//methods
	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}
	
	public Piece piece(int row, int column) {     //retorna uma pe�a do tipo Piece informando uma linha e coluna da matriz
		if(!positionExists(row, column)) {   //verica��o se a posi��o na matriz existe
			throw new BoardException("Position not on the board");
		}
		return pieces[row][column];
	}
	
	public Piece piece(Position position) {   //retorna uma pe�a do tipo Piece informando a posicao da matriz
		if(!positionExists(position)) {   //verifica��o se a posi��o na matriz existe
			throw new BoardException("Position not on the board");
		}
		return pieces[position.getRow()][position.getColumn()];
	}
	
	public void placePiece(Piece piece, Position position) {   //adicionar pe�a no tabuleiro, dando a pe�a e a posi��o na matriz
		if(thereIsAPiece(position)) {  //verifica se em pe�a adicionada naquela posi��o ja 
			throw new BoardException("There is already a piece on position " + position);
		}
		pieces[position.getRow()][position.getColumn()] = piece;  //coloca a pe�a na posi��o da matriz
		piece.position = position; //a posi��o daquela pe�a deixa de ser nula
	}
	
	public Piece removePiece(Position position) {   //remove uma pe�a do tabuleiro dando a posi��o na matriz
		if(!positionExists(position)) {  //verifica se existe aquela posi��o
			throw new BoardException("Position not on board");
		}
		if(piece(position) == null) {   //se na matriz naquela posi��o tiver nulo , so retorna nulo  
			return null;
		}
		Piece aux = piece(position);   // senao, aux recebe a pe�a naquela posi��o
		aux.position = null;      //a posi��o dessa pe�a se torna nula
		pieces[position.getRow()][position.getColumn()] = null;  //a posi��o na matriz fica nula tbm 
		return aux;  //retorna a pe�a
	}
	
	public boolean positionExists(Position position) {  //retorna verdadeiro se a posi��o existir no tabuleiro
		return positionExists(position.getRow(), position.getColumn());
	}
	
	public boolean thereIsAPiece(Position position) {  //retorna verdadeiro se existir uma pe�a naquela posi��o
		if(!positionExists(position)) {
			throw new BoardException("Position not on the board");
		}
		return piece(position) != null;
	}
	
	private boolean positionExists(int row, int column) {   //retorna verdadeiro se a linha e coluna existir no tabuleiro
		return row >= 0 && row < rows && column >=0 && column < columns;
	}
}
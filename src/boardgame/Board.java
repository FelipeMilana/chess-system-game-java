package boardgame;

public class Board {

	//attribution
	private int rows;        
	private int columns;
	
	//association
	private Piece[][] pieces; //criamos uma variavel que vai ser uma matriz de peças
	 
	//constructors
	public Board(int rows, int columns) { //cria um tabuleiro, com uma matriz de peças vazias
		if(rows < 1 || columns < 1) {
			throw new BoardException("Error creating board: thre must be at least 1 row and 1 column ");
		}
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];  //instanciamos uma matriz nula, que tera o numero de linhas e colunas falados, nao é o construtor da classe piece
	}

	//methods
	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}
	
	public Piece piece(int row, int column) {     //retorna uma peça do tipo Piece informando uma linha e coluna da matriz
		if(!positionExists(row, column)) {   //vericação se a posição na matriz existe
			throw new BoardException("Position not on the board");
		}
		return pieces[row][column];
	}
	
	public Piece piece(Position position) {   //retorna uma peça do tipo Piece informando a posicao da matriz
		if(!positionExists(position)) {   //verificação se a posição na matriz existe
			throw new BoardException("Position not on the board");
		}
		return pieces[position.getRow()][position.getColumn()];
	}
	
	public void placePiece(Piece piece, Position position) {   //adicionar peça no tabuleiro, dando a peça e a posição na matriz
		if(thereIsAPiece(position)) {  //verifica se em peça adicionada naquela posição ja 
			throw new BoardException("There is already a piece on position " + position);
		}
		pieces[position.getRow()][position.getColumn()] = piece;  //coloca a peça na posição da matriz
		piece.position = position; //a posição daquela peça deixa de ser nula
	}
	
	public Piece removePiece(Position position) {   //remove uma peça do tabuleiro dando a posição na matriz
		if(!positionExists(position)) {  //verifica se existe aquela posição
			throw new BoardException("Position not on board");
		}
		if(piece(position) == null) {   //se na matriz naquela posição tiver nulo , so retorna nulo  
			return null;
		}
		Piece aux = piece(position);   // senao, aux recebe a peça naquela posição
		aux.position = null;      //a posição dessa peça se torna nula
		pieces[position.getRow()][position.getColumn()] = null;  //a posição na matriz fica nula tbm 
		return aux;  //retorna a peça
	}
	
	public boolean positionExists(Position position) {  //retorna verdadeiro se a posição existir no tabuleiro
		return positionExists(position.getRow(), position.getColumn());
	}
	
	public boolean thereIsAPiece(Position position) {  //retorna verdadeiro se existir uma peça naquela posição
		if(!positionExists(position)) {
			throw new BoardException("Position not on the board");
		}
		return piece(position) != null;
	}
	
	private boolean positionExists(int row, int column) {   //retorna verdadeiro se a linha e coluna existir no tabuleiro
		return row >= 0 && row < rows && column >=0 && column < columns;
	}
}
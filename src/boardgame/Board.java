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
		pieces = new Piece[rows][columns];  //instanciamos o objeto, que tera o numero de linhas e colunas falados, e sera cheia de nulos, nao � o construtor da classe piece
	}

	//methods
	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}
	
	public Piece piece(int row, int column) {     //retorna uma pe�a do tipo Piece informando uma linha e coluna
		if(!positionExists(row, column)) {
			throw new BoardException("Position not on the board");
		}
		return pieces[row][column];
	}
	
	public Piece piece(Position position) {   //retorna uma pe�a do tipo Piece informando a posicao
		if(!positionExists(position)) {
			throw new BoardException("Position not on the board");
		}
		return pieces[position.getRow()][position.getColumn()];
	}
	
	public void placePiece(Piece piece, Position position) {   //adicionar pe�a
		if(thereIsAPiece(position)) {
			throw new BoardException("There is already a piece on position " + position);
		}
		pieces[position.getRow()][position.getColumn()] = piece;  //adiciona a pe�a
		piece.position = position; //adiciona a posi��o da pe�a
	}
	
	private boolean positionExists(int row, int column) {   //retorna verdadeiro se a linha e coluna existir no tabuleiro
		return row >= 0 && row < rows && column >=0 && column < columns;
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
}

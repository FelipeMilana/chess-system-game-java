package boardgame;

public class Board {

	//attribution
	private int rows;        
	private int columns;
	
	//association
	private Piece[][] pieces; //criamos uma variavel que vai ser uma matriz de peças
	 
	//constructors
	public Board(int rows, int columns) { //cria um tabuleiro, com uma matriz de peças vazias
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];  //instanciamos o objeto, que tera o numero de linhas e colunas falados, e sera cheia de nulos, nao é o construtor da classe piece
	}

	//methods
	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}
	
	public Piece piece(int row, int column) {     //retorna uma peça do tipo Piece informando uma linha e coluna
		return pieces[row][column];
	}
	
	public Piece piece(Position position) {   //retorna uma peça do tipo Piece informando a posicao
		return pieces[position.getRow()][position.getColumn()];
	}
}

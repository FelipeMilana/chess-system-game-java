package boardgame;

public class Board {

	//attribution
	private int rows;
	private int columns;
	private Piece[][] pieces; //criamos uma variavel que vai ser uma matriz de peça
	
	//constructors
	public Board() {
	}
	
	public Board(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];  //instanciamos o objeto, que tera o numero de linhas e colunas falados, e sera cheia de nulos
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
}

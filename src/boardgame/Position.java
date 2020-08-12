package boardgame;

public class Position {

	//attributes
	private int row;
	private int column;
	
	//constructors
	public Position(int row, int column) {   //cria uma posição com coluna e linha
		this.row = row;
		this.column = column;
	}
	
	//methods
	public int getRow() {
		return row;
	}
	
	public void setRow(int row) {
		this.row = row;
	}
	
	public int getColumn() {
		return column;
	}
	
	public void setColumn(int column) {
		this.column = column;
	}
	
	public void setValues(int row, int column) {  //metodo para atualizar os valores de linha e coluna
		this.row = row;
		this.column = column;
	}
	@Override
	public String toString() {  //apenas imprime
		return row+", "+column;
	}
}

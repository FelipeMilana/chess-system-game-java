package boardgame;

public class Piece {

	//association
	protected Position position; //nao é a posição do xadrez, é uma simples de matriz 
	private Board board;  //a peça conhece o tabuleiro onde ele está
	
	//constructors
	public Piece (Board board) {  //para a posição inicial ser nula, nao poe no argumento do construtor e nem precisava da atribuição
		this.board = board;
		position = null;        //posição inicial de uma peça recem criada sera nula, dizendo que nao foi colocada no tabuleiro ainda
	}
	
	//methods
	protected Board getBoard() {     //nao permite que o tabuleiro seja alterado, e sera protected, e nao vai ser aessivel da camada de xadrez
		return board;
	}
}

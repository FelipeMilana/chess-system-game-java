package boardgame;

public class Piece {

	//association
	protected Position position; //nao � a posi��o do xadrez, � uma simples de matriz 
	private Board board;  //a pe�a conhece o tabuleiro onde ele est�
	
	//constructors
	public Piece (Board board) {  //para a posi��o inicial ser nula, nao poe no argumento do construtor e nem precisava da atribui��o
		this.board = board;
		position = null;        //posi��o inicial de uma pe�a recem criada sera nula, dizendo que nao foi colocada no tabuleiro ainda
	}
	
	//methods
	protected Board getBoard() {     //nao permite que o tabuleiro seja alterado, e sera protected, e nao vai ser aessivel da camada de xadrez
		return board;
	}
}

package boardgame;

public abstract class Piece {  //por causa do metodo

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
	
	public abstract boolean[][] possibleMoves();  //� generico, cada pe�a tem o seu, por isso abstract
	
	public boolean possibleMove(Position position) {  //metodo concreto utiliza um metodo abstrato
		return possibleMoves()[position.getRow()][position.getColumn()]; //metodo que faz gancho com a subclasse, templateMethod
	}
	
	public boolean isThereAnyPossibleMove() {   //metodo concreto que depende de um metodo abstrato
		boolean[][] mat = possibleMoves();  //cheia de false ou true
		for(int i = 0; i < mat.length; i++) {
			for(int j = 0; j < mat[i].length; j++) {
				if(mat[i][j]) {  //se true
					return true;
				}
			}
		}
		return false;  //se tudo for false, retorne false
	}
} 

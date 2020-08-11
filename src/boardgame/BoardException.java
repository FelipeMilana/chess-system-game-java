package boardgame;

public class BoardException extends RuntimeException {  //herança de RuntimeException, e nao precisa ser tratado
	private static final long serialVersionUID = 1L;   //numero padrao de versao
	
	//constructor
	public BoardException(String msg) {  //recebe uma msg e passa para a classe super, usa-se o .getMessage
		super(msg);
	}

}

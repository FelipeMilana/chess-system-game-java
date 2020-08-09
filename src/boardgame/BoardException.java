package boardgame;

public class BoardException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	//constructor
	public BoardException(String msg) {
		super(msg);
	}

}

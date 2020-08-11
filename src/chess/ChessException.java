package chess;

import boardgame.BoardException;

public class ChessException extends BoardException{  //eran�a de BoardException, logo tamb�m � heran�a da runtimeException
	private static final long serialVersionUID = 1L;

	//constructors
	public ChessException(String msg) {   //a msg passa pro construtor da boardException, e consequetemente para a runtimeException
		super(msg);
	}
}

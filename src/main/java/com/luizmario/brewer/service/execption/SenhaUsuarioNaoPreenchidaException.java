package com.luizmario.brewer.service.execption;

public class SenhaUsuarioNaoPreenchidaException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public SenhaUsuarioNaoPreenchidaException(String message) {
		super(message);
	}

}

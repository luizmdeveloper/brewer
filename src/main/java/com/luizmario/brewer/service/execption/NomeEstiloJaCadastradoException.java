package com.luizmario.brewer.service.execption;

public class NomeEstiloJaCadastradoException extends RuntimeException {
	
	private static final long serialVersionUID = 3809378721293878933L;
	
	public NomeEstiloJaCadastradoException(String message){
		super(message);
	}

}

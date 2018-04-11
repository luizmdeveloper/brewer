package com.luizmario.brewer.service.execption;

public class CnpjCpfJaCadastradoException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public CnpjCpfJaCadastradoException(String mensagem){
		super(mensagem);
	}

}

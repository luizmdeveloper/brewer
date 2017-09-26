package com.luizmario.brewer.model;

public enum Origem {
	
	INTERNACIONAL("Internacional"),
	NACIONAL("Nacional");
	
	private String descricao;
	
	Origem(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao(){
		return descricao;
	}
}

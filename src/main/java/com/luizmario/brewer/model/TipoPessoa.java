package com.luizmario.brewer.model;

public enum TipoPessoa {

	Fisica("Física", "CPF", "00.000.000-00"),
	Juridica("Jurídica", "CNPJ", "00.000.000/0000-00");
	
	private String field;
	private String descricao;
	private String mascara;
	
	private TipoPessoa(String descricao, String field, String mascara) {
		this.descricao = descricao;
		this.field = field;
		this.mascara = mascara;
	}

	public String getField() {
		return field;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getMascara() {
		return mascara;
	}	
}

package com.luizmario.brewer.model;

import com.luizmario.brewer.model.validation.groups.CnpjGroups;
import com.luizmario.brewer.model.validation.groups.CpfGroups;

public enum TipoPessoa {

	Fisica("Fisica", "CPF", "00.000.000-00", CpfGroups.class),
	Juridica("Jurídica", "CNPJ", "00.000.000/0000-00", CnpjGroups.class);
	
	private String field;
	private String descricao;
	private String mascara;
	private Class<?> grupo;
	
	private TipoPessoa(String descricao, String field, String mascara, Class<?> grupo) {
		this.descricao = descricao;
		this.field = field;
		this.mascara = mascara;
		this.grupo = grupo;
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
	
	public Class<?> getGrupo(){
		return grupo;
	}
}

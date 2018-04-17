package com.luizmario.brewer.model;

import com.luizmario.brewer.model.validation.groups.CnpjGroups;
import com.luizmario.brewer.model.validation.groups.CpfGroups;

public enum TipoPessoa {

	Fisica("Fisica", "CPF", "000.000.000-00", CpfGroups.class) {
		@Override
		public String formatar(String cpfOuCnpj) {
			return cpfOuCnpj.replaceAll("(\\d{3})(\\d{3})(\\d{3})", "$1.$2.$3-");
		}
	},
	Juridica("Jurídica", "CNPJ", "00.000.000/0000-00", CnpjGroups.class) {
		@Override
		public String formatar(String cpfOuCnpj) {
			return cpfOuCnpj.replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d{4})", "$1.$2.$3/$4-");
		}
	};
	
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
	
	public abstract String formatar(String cpfOuCnpj);

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

	public static String removerFormatacao(String cpfOuCnpj) {
		return cpfOuCnpj.replaceAll("\\.|-|/", "");
	}
}

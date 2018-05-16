package com.luizmario.brewer.respository.filter;

import java.util.List;

import com.luizmario.brewer.model.Grupo;

public class UsuarioFilter {

	private String nome;
	private String email;
	private Boolean status;
	private List<Grupo> grupos;
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Boolean getStatus() {
		return status;
	}
	
	public void setStatus(Boolean status) {
		this.status = status;
	}

	public List<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}	
}

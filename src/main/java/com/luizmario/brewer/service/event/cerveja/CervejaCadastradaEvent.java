package com.luizmario.brewer.service.event.cerveja;

import org.springframework.util.StringUtils;

import com.luizmario.brewer.model.Cerveja;

public class CervejaCadastradaEvent {
	
	private Cerveja cerveja;
	
	public CervejaCadastradaEvent(Cerveja cerveja){
		this.cerveja = cerveja;
	}
	
	public Cerveja getCerveja(){
		return cerveja;
	}
	
	public boolean isFoto(){
		return !StringUtils.isEmpty(cerveja.getFoto());
	}
	
	public boolean isNovaFoto() {
		return cerveja.isNovaFoto();
	}

}

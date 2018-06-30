package com.luizmario.brewer.service.event.cerveja;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.luizmario.brewer.storage.FotoStorage;

@Component
public class CervejaCadastradaListner {
	
	@Autowired
	private FotoStorage fotoStorage;
	
	@EventListener(condition="#evento.isFoto() and #evento.isNovaFoto()")
	public void salvar(CervejaCadastradaEvent evento){
		fotoStorage.salvar(evento.getCerveja().getFoto());
	}

}

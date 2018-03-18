package com.luizmario.brewer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luizmario.brewer.model.Cerveja;
import com.luizmario.brewer.respository.CervejasRepository;
import com.luizmario.brewer.service.event.cerveja.CervejaCadastradaEvent;

@Service
public class CervejaService {

	@Autowired
	private CervejasRepository cervejasRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Transactional
	public void salvar(Cerveja cerveja){
		cervejasRepository.save(cerveja);
		
		publisher.publishEvent(new CervejaCadastradaEvent(cerveja));
	}
}

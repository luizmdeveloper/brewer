package com.luizmario.brewer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luizmario.brewer.model.Estilo;
import com.luizmario.brewer.respository.EstilosRepository;

@Service
public class EstiloService {
	
	@Autowired
	private EstilosRepository estiloRepository;
	
	@Transactional
	public void salvar(Estilo estilo){
		estiloRepository.save(estilo);
	}

}

package com.luizmario.brewer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luizmario.brewer.model.Cerveja;
import com.luizmario.brewer.respository.CervejasRepository;

@Service
public class CervejaService {

	@Autowired
	private CervejasRepository cervejasRepository;
	
	@Transactional
	public void salvar(Cerveja cerveja){
		cervejasRepository.save(cerveja);
	}
}

package com.luizmario.brewer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luizmario.brewer.model.Estilo;
import com.luizmario.brewer.respository.EstilosRepository;
import com.luizmario.brewer.service.execption.NomeEstiloJaCadastradoException;

@Service
public class EstiloService {
	
	@Autowired
	private EstilosRepository estiloRepository;
	
	@Transactional
	public Estilo salvar(Estilo estilo){
		
		Optional<Estilo> optionalEstilo = estiloRepository.findByNomeIgnoreCase(estilo.getNome());		
		if (optionalEstilo.isPresent()){
			throw new NomeEstiloJaCadastradoException("Nome do estilo já cadastrado");
		}
		
		return estiloRepository.saveAndFlush(estilo);
	}

}

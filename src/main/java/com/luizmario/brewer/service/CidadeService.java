package com.luizmario.brewer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luizmario.brewer.model.Cidade;
import com.luizmario.brewer.respository.CidadeRepository;

@Service
public class CidadeService {
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Transactional
	public void salvar(Cidade cidade){
		cidadeRepository.save(cidade);
	}

}

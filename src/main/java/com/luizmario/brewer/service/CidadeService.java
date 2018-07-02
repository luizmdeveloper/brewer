package com.luizmario.brewer.service;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luizmario.brewer.model.Cidade;
import com.luizmario.brewer.respository.CidadeRepository;
import com.luizmario.brewer.service.execption.CidadeComClienteCadastradaException;

@Service
public class CidadeService {
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Transactional
	public void salvar(Cidade cidade){
		cidadeRepository.save(cidade);
	}

	@Transactional
	public void apagar(long codigo) {
		try {
			cidadeRepository.delete(codigo);
			cidadeRepository.flush();
		} catch (PersistenceException e) {
			throw new CidadeComClienteCadastradaException("Impossível excluir cidade, devido ter cliente cadastrado!");
		}
	}

}

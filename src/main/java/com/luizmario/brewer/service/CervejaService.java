package com.luizmario.brewer.service;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.luizmario.brewer.model.Cerveja;
import com.luizmario.brewer.respository.CervejasRepository;
import com.luizmario.brewer.service.event.cerveja.CervejaCadastradaEvent;
import com.luizmario.brewer.service.execption.CervejaComVendaCadastradaException;
import com.luizmario.brewer.storage.FotoStorage;

@Service
public class CervejaService {

	@Autowired
	private CervejasRepository cervejasRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private FotoStorage fotoStorage;
	
	@Transactional
	public void salvar(Cerveja cerveja){
		cervejasRepository.save(cerveja);
		
		publisher.publishEvent(new CervejaCadastradaEvent(cerveja));
	}

	@Transactional
	public void apagar(Cerveja cerveja) {
		try {
			String foto = cerveja.getFoto();
			cervejasRepository.delete(cerveja);
			cervejasRepository.flush();
			if (!StringUtils.isEmpty(foto)) {
				fotoStorage.apagar(foto);
			}
		} catch (PersistenceException e) {
			throw new CervejaComVendaCadastradaException("Impossível excluir essa cerveja, devido ter venda cadastrada");
		}
	}
}

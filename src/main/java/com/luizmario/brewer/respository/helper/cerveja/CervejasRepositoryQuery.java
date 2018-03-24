package com.luizmario.brewer.respository.helper.cerveja;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.luizmario.brewer.model.Cerveja;
import com.luizmario.brewer.respository.filter.CervejaFilter;

public interface CervejasRepositoryQuery {
	
	public Page<Cerveja> filtar(CervejaFilter filtro, Pageable page);
}
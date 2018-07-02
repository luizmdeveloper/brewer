package com.luizmario.brewer.respository.helper.cidade;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.luizmario.brewer.model.Cidade;
import com.luizmario.brewer.respository.filter.CidadeFilter;

public interface CidadeRepositoryQuery {

	public Page<Cidade> filtrar(CidadeFilter filtro, Pageable pageable);
	
	public Cidade buscarComEstado(Long codigo);
}

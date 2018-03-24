package com.luizmario.brewer.respository.helper.estilo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.luizmario.brewer.model.Estilo;
import com.luizmario.brewer.respository.filter.EstiloFilter;

public interface EstilosRepositoryQuery {
	
	public Page<Estilo> filtrar(EstiloFilter filtro, Pageable page);

}

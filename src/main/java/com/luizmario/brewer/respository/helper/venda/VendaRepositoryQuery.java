package com.luizmario.brewer.respository.helper.venda;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.luizmario.brewer.model.Venda;
import com.luizmario.brewer.respository.filter.VendaFilter;

public interface VendaRepositoryQuery {
	
	public Page<Venda> filtrar(VendaFilter filtro, Pageable pageable);
	
	public Venda buscarComItens(Long codigo);
	
}

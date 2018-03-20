package com.luizmario.brewer.respository.helper.cerveja;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.luizmario.brewer.model.Cerveja;
import com.luizmario.brewer.respository.filter.CervejaFilter;

public interface CervejasRepositoryQuery {
	
	public List<Cerveja> filtar(CervejaFilter filtro, Pageable page);
}

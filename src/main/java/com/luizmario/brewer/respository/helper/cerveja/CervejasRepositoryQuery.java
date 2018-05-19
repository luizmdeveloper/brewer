package com.luizmario.brewer.respository.helper.cerveja;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.luizmario.brewer.dto.CervejaDTO;
import com.luizmario.brewer.model.Cerveja;
import com.luizmario.brewer.respository.filter.CervejaFilter;

public interface CervejasRepositoryQuery {
	
	public Page<Cerveja> filtar(CervejaFilter filtro, Pageable pageable);
	
	public List<CervejaDTO> buscarPorSkuOuNome(String skuOuNome);
}

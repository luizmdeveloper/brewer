package com.luizmario.brewer.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luizmario.brewer.model.Venda;
import com.luizmario.brewer.respository.helper.venda.VendaRepositoryQuery;

public interface VendaRepository extends JpaRepository<Venda, Long>, VendaRepositoryQuery {

	


}

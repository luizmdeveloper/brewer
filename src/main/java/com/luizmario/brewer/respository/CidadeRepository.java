package com.luizmario.brewer.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luizmario.brewer.model.Cidade;
import com.luizmario.brewer.respository.helper.cidade.CidadeRepositoryQuery;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long>, CidadeRepositoryQuery {
	
	public List<Cidade> findByEstadoCodigo(Long codigoEstado);

}

package com.luizmario.brewer.respository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luizmario.brewer.model.Estilo;
import com.luizmario.brewer.respository.helper.estilo.EstilosRepositoryQuery;

@Repository
public interface EstilosRepository extends JpaRepository<Estilo, Long>, EstilosRepositoryQuery {

	public Optional<Estilo> findByNomeIgnoreCase(String nome);
}

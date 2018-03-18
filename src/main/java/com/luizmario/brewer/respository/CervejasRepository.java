package com.luizmario.brewer.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luizmario.brewer.model.Cerveja;
import com.luizmario.brewer.respository.helper.cerveja.CervejasRepositoryQuery;

@Repository
public interface CervejasRepository extends JpaRepository<Cerveja, Long>, CervejasRepositoryQuery {

}

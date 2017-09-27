package com.luizmario.brewer.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luizmario.brewer.model.Estilo;

@Repository
public interface EstilosRepository extends JpaRepository<Estilo, Long> {

}

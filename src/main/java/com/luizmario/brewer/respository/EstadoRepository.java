package com.luizmario.brewer.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luizmario.brewer.model.Estado;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long> {

}

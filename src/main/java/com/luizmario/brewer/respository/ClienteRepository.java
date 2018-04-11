package com.luizmario.brewer.respository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luizmario.brewer.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	public Optional<Cliente> findByCpfOuCnpj(String cpfOuCnpj);

}

package com.luizmario.brewer.respository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luizmario.brewer.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	public Optional<Usuario> findByEmailIgnoreCase(String email); 

}

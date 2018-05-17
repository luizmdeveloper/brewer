package com.luizmario.brewer.respository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luizmario.brewer.model.Usuario;
import com.luizmario.brewer.respository.helper.usuario.UsuarioRepositoryQuery;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>, UsuarioRepositoryQuery {

	public Optional<Usuario> findByEmailIgnoreCase(String email);

	public List<Usuario> findByCodigoIn(Long[] codigos); 

}

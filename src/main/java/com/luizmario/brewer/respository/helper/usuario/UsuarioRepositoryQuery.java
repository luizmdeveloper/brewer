package com.luizmario.brewer.respository.helper.usuario;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.luizmario.brewer.model.Usuario;
import com.luizmario.brewer.respository.filter.UsuarioFilter;

public interface UsuarioRepositoryQuery {
	
	public Optional<Usuario> findByEmailUsuarioAtivo(String email);
	
	public List<String> findPermissoesByUsuario(Usuario usuario);
	
	public Page<Usuario> filtar(UsuarioFilter usuarioFilter, Pageable page);
	
	public Usuario buscarPor(Long codigo);

}

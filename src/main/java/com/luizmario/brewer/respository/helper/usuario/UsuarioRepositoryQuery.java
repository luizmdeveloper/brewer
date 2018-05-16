package com.luizmario.brewer.respository.helper.usuario;

import java.util.List;
import java.util.Optional;

import com.luizmario.brewer.model.Usuario;
import com.luizmario.brewer.respository.filter.UsuarioFilter;

public interface UsuarioRepositoryQuery {
	
	public Optional<Usuario> findByEmailUsuarioAtivo(String email);
	
	public List<String> findPermissoesByUsuario(Usuario usuario);
	
	public List<Usuario> findAll(UsuarioFilter usuarioFilter);

}

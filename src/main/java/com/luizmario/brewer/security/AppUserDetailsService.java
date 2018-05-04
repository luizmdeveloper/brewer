package com.luizmario.brewer.security;

import java.util.HashSet;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.luizmario.brewer.model.Usuario;
import com.luizmario.brewer.respository.UsuarioRepository;

@Service
public class AppUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<Usuario> usuarioOptional = usuarioRepository.findByEmailUsuarioAtivo(email);
		Usuario usuario = usuarioOptional.orElseThrow(() -> new UsernameNotFoundException("Usuário e/ou senha não conferem"));
		return new User(usuario.getEmail(), usuario.getSenha(), new HashSet<>());
	}

}

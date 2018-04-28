package com.luizmario.brewer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luizmario.brewer.model.Usuario;
import com.luizmario.brewer.respository.UsuarioRepository;
import com.luizmario.brewer.service.execption.EmailUsuarioJaCadastradoException;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Transactional
	public void salvar(Usuario usuario) {
		
		Optional<Usuario> optionalUsuario = usuarioRepository.findByEmailIgnoreCase(usuario.getEmail());
		
		if (optionalUsuario.isPresent()) {
			throw new EmailUsuarioJaCadastradoException("Usuario já cadastrado");
		}
		
		usuarioRepository.save(usuario);
	}

}

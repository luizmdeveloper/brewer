package com.luizmario.brewer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.luizmario.brewer.model.Usuario;
import com.luizmario.brewer.respository.UsuarioRepository;
import com.luizmario.brewer.service.execption.EmailUsuarioJaCadastradoException;
import com.luizmario.brewer.service.execption.SenhaUsuarioNaoPreenchidaException;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Transactional
	public void salvar(Usuario usuario) {
		
		Optional<Usuario> optionalUsuario = usuarioRepository.findByEmailIgnoreCase(usuario.getEmail());
		
		if (optionalUsuario.isPresent()) {
			throw new EmailUsuarioJaCadastradoException("Usuario já cadastrado");
		}
		
		if (usuario.isNovo() && StringUtils.isEmpty(usuario.getSenha())) {
			throw new SenhaUsuarioNaoPreenchidaException("Senha deve ser preenchida para novos usuários");
		}
		
		if (usuario.isNovo()) {
			usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
			usuario.setConfirmacaoSenha(usuario.getSenha());
		}
		
		usuarioRepository.save(usuario);
	}

}

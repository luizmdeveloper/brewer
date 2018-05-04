package com.luizmario.brewer.respository.helper.usuario;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.luizmario.brewer.model.Usuario;

public class UsuarioRepositoryImpl implements UsuarioRepositoryQuery {
	
	@PersistenceContext
	private EntityManager manager;

	@Override
	public Optional<Usuario> findByEmailUsuarioAtivo(String email) {
		return manager.createQuery("from Usuario where lower(email)=lower(:email) and ativo = 1", Usuario.class)
					.setParameter("email", email).getResultList().stream().findFirst();
	}

}

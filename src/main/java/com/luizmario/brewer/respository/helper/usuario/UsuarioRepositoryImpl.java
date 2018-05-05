package com.luizmario.brewer.respository.helper.usuario;

import java.util.List;
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

	@Override
	public List<String> findPermissoesByUsuario(Usuario usuario) {
		return manager.createQuery("select distinct p.nome from Usuario u inner join u.grupos g inner join g.permissoes p where u=:usuario", String.class)
				.setParameter("usuario", usuario).getResultList();
	}

}

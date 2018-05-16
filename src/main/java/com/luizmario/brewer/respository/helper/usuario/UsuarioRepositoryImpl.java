package com.luizmario.brewer.respository.helper.usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.sql.JoinType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.luizmario.brewer.model.Grupo;
import com.luizmario.brewer.model.Usuario;
import com.luizmario.brewer.model.UsuarioGrupo;
import com.luizmario.brewer.respository.filter.UsuarioFilter;

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

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public List<Usuario> findAll(UsuarioFilter usuarioFilter) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Usuario.class);
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		adicionarFiltro(usuarioFilter, criteria);
		
		return criteria.list();
	}

	private void adicionarFiltro(UsuarioFilter usuarioFilter, Criteria criteria) {
		if (usuarioFilter != null) {
			if (!StringUtils.isEmpty(usuarioFilter.getNome())) {
				criteria.add(Restrictions.ilike("nome", usuarioFilter.getNome(), MatchMode.ANYWHERE));
			}
			
			if (!StringUtils.isEmpty(usuarioFilter.getEmail())) {
				criteria.add(Restrictions.ilike("email", usuarioFilter.getEmail(), MatchMode.START));
			}
			
			criteria.createAlias("grupos", "g", JoinType.LEFT_OUTER_JOIN);
			if (usuarioFilter.getGrupos() != null && !usuarioFilter.getGrupos().isEmpty()) {
				List<Criterion> subqueries = new ArrayList<>();
				for(Long codigoGrupo : usuarioFilter.getGrupos().stream().mapToLong(Grupo::getCodigo).toArray()) {
					DetachedCriteria detachedCriteria = DetachedCriteria.forClass(UsuarioGrupo.class);
					detachedCriteria.add(Restrictions.eq("id.grupo.codigo", codigoGrupo));
					detachedCriteria.setProjection(Projections.property("id.usuario"));
					
					subqueries.add(Subqueries.propertyIn("codigo", detachedCriteria));
				}
				
				Criterion[] criterions = new Criterion[subqueries.size()];
				criteria.add(Restrictions.and(subqueries.toArray(criterions)));
			}
		}
	}
}

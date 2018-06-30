package com.luizmario.brewer.respository.helper.usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.luizmario.brewer.model.Grupo;
import com.luizmario.brewer.model.Usuario;
import com.luizmario.brewer.model.UsuarioGrupo;
import com.luizmario.brewer.respository.filter.UsuarioFilter;
import com.luizmario.brewer.respository.paginacao.PaginacaoUtil;

public class UsuarioRepositoryImpl implements UsuarioRepositoryQuery {
	
	@PersistenceContext
	private EntityManager manager;

	@Autowired
	private PaginacaoUtil paginacaoUtil;

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
	public Page<Usuario> filtar(UsuarioFilter usuarioFilter, Pageable page) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Usuario.class);
		
		paginacaoUtil.preparar(criteria, page);
		adicionarFiltro(usuarioFilter, criteria);
		
		List<Usuario> usuarioFiltrdos = criteria.list();
		usuarioFiltrdos.forEach(u -> Hibernate.initialize(u.getGrupos()));		
		
		return new PageImpl<>(criteria.list(), page, total(usuarioFilter));
	}

	@Transactional(readOnly = true)
	@Override
	public Usuario buscarPor(Long codigo) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Usuario.class);
		criteria.createAlias("grupos", "g", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("codigo", codigo));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (Usuario) criteria.uniqueResult();
	}
	
	
	private Long total(UsuarioFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Usuario.class);
		adicionarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}
	

	private void adicionarFiltro(UsuarioFilter usuarioFilter, Criteria criteria) {
		if (usuarioFilter != null) {
			if (!StringUtils.isEmpty(usuarioFilter.getNome())) {
				criteria.add(Restrictions.ilike("nome", usuarioFilter.getNome(), MatchMode.ANYWHERE));
			}
			
			if (!StringUtils.isEmpty(usuarioFilter.getEmail())) {
				criteria.add(Restrictions.ilike("email", usuarioFilter.getEmail(), MatchMode.START));
			}
			
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

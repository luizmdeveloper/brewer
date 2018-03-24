package com.luizmario.brewer.respository.helper.estilo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.luizmario.brewer.model.Cerveja;
import com.luizmario.brewer.model.Estilo;
import com.luizmario.brewer.respository.filter.EstiloFilter;

public class EstilosRepositoryImpl implements EstilosRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = false)
	public Page<Estilo> filtrar(EstiloFilter filtro, Pageable page) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Estilo.class);
		
		int paginaAtual = page.getPageNumber();
		int quantidadeRegistroPorPagina = page.getPageSize();
		int primeiroRegistro = paginaAtual * quantidadeRegistroPorPagina;
		
		Sort sort = page.getSort();		
		if (sort != null){
			Sort.Order order = sort.iterator().next();
			String property = order.getProperty();
			criteria.addOrder(order.isAscending() ? Order.asc(property) : Order.desc(property));
		}
		
		criteria.setFirstResult(primeiroRegistro);
		criteria.setMaxResults(quantidadeRegistroPorPagina);
		
		adicionarFiltro(filtro, criteria);
				
		return new PageImpl<>(criteria.list(), page, total(filtro));
	}

	private Long total(EstiloFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cerveja.class);
		adicionarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());		
		return (Long) criteria.uniqueResult();
	}

	private void adicionarFiltro(EstiloFilter filtro, Criteria criteria) {
		if (filtro != null){
			if (!StringUtils.isEmpty(filtro.getNome())){
				criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
			}
		}
	}
}

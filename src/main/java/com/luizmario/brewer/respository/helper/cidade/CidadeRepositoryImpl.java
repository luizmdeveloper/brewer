package com.luizmario.brewer.respository.helper.cidade;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.luizmario.brewer.model.Cidade;
import com.luizmario.brewer.respository.filter.CidadeFilter;
import com.luizmario.brewer.respository.paginacao.PaginacaoUtil;

public class CidadeRepositoryImpl implements CidadeRepositoryQuery {
	
	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private PaginacaoUtil paginacaoUtil;

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public Page<Cidade> filtrar(CidadeFilter filtro, Pageable pageable) {		
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cidade.class);
		
		paginacaoUtil.preparar(criteria, pageable);
		adicionarFiltro(criteria, filtro);
		
		criteria.createAlias("estado", "e", JoinType.LEFT_OUTER_JOIN);
		
		return new PageImpl<>(criteria.list(), pageable, total(filtro));
	}

	private Long total(CidadeFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cidade.class);
		adicionarFiltro(criteria, filtro);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	private void adicionarFiltro(Criteria criteria, CidadeFilter filtro) {
		if (filtro != null){
			if (!StringUtils.isEmpty(filtro.getNome())){
				criteria.add(Restrictions.ilike("nome", filtro.getNome()));
			}
			
			if (filtro.getEstado() != null){
				criteria.add(Restrictions.eq("estado", filtro.getEstado()));
			}
		}		
	}

}

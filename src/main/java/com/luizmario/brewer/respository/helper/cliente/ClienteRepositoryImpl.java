package com.luizmario.brewer.respository.helper.cliente;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.luizmario.brewer.model.Cliente;
import com.luizmario.brewer.respository.filter.ClienteFilter;
import com.luizmario.brewer.respository.paginacao.PaginacaoUtil;

public class ClienteRepositoryImpl implements ClienteRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private PaginacaoUtil paginacaoUtil;
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public Page<Cliente> filtrar(ClienteFilter filter, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cliente.class);
		
		paginacaoUtil.preparar(criteria, pageable);
		adicionarFiltro(filter, criteria);		
		
		criteria.createAlias("endereco.cidade", "c", JoinType.LEFT_OUTER_JOIN);
		criteria.createAlias("c.estado", "e", JoinType.LEFT_OUTER_JOIN);
		
		return new PageImpl<>(criteria.list(), pageable, total(filter));
	}	

	private long total(ClienteFilter filter) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cliente.class);		
		adicionarFiltro(filter, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}


	private void adicionarFiltro(ClienteFilter filter, Criteria criteria) {
		if (filter != null){
			
			if (!StringUtils.isEmpty(filter.getNome())){
				criteria.add(Restrictions.ilike("nome", filter.getNome(), MatchMode.ANYWHERE));				
			}
			
			if (!StringUtils.isEmpty(filter.getCpfOuCnpj())){
				criteria.add(Restrictions.ilike("cpf_cnpj", filter.getCpfOuCnpj(), MatchMode.ANYWHERE));				
			}
		}		
	}	
}

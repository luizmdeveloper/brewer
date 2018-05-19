package com.luizmario.brewer.respository.helper.cerveja;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.luizmario.brewer.dto.CervejaDTO;
import com.luizmario.brewer.model.Cerveja;
import com.luizmario.brewer.respository.filter.CervejaFilter;
import com.luizmario.brewer.respository.paginacao.PaginacaoUtil;

public class CervejasRepositoryImpl implements CervejasRepositoryQuery {
	
	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private PaginacaoUtil paginacaoUtil;

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public Page<Cerveja> filtar(CervejaFilter filtro, Pageable page) {		
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cerveja.class);
		
		paginacaoUtil.preparar(criteria, page);
		adicionarFiltro(filtro, criteria);
		
		return new PageImpl<>(criteria.list(), page, total(filtro));
	}

	private Long total(CervejaFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cerveja.class);
		adicionarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}
	
	private void adicionarFiltro(CervejaFilter filtro, Criteria criteria) {
		if (filtro != null) {
			if (!StringUtils.isEmpty(filtro.getSku())) {
				criteria.add(Restrictions.eq("sku", filtro.getSku()));
			}
			
			if (!StringUtils.isEmpty(filtro.getNome())) {
				criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));	
			}
			
			if (estiloPreenchido(filtro)) {
				criteria.add(Restrictions.eq("estilo", filtro.getEstilo()));
			}
			
			if (filtro.getSabor() != null) {
				criteria.add(Restrictions.eq("sabor", filtro.getSabor()));
			}

			if (filtro.getOrigem() != null) {
				criteria.add(Restrictions.eq("origem", filtro.getOrigem()));
			}

			if (filtro.getValorDe() != null) {
				criteria.add(Restrictions.ge("valor", filtro.getValorDe()));
			}

			if (filtro.getValorAte() != null) {
				criteria.add(Restrictions.le("valor", filtro.getValorAte()));
			}
		}
	}


	private boolean estiloPreenchido(CervejaFilter filtro) {
		return filtro.getEstilo() != null && filtro.getEstilo().getCodigo() != null;
	}

	@Override
	public List<CervejaDTO> buscarPorSkuOuNome(String skuOuNome) {
		String jpql = " SELECT new com.luizmario.brewer.dto.CervejaDTO(codigo, sku, nome, origem, valor, foto) FROM Cerveja " +
					  " WHERE lower(sku) like lower(:skuOuNome) OR lower(nome) like lower(:skuOuNome) " ;
		List<CervejaDTO> cervejasFiltrada = manager.createQuery(jpql, CervejaDTO.class)
				.setParameter("skuOuNome", skuOuNome + "%")
				.getResultList();
		return cervejasFiltrada;
	}
}

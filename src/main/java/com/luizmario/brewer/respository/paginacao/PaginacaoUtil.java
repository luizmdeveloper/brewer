package com.luizmario.brewer.respository.paginacao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class PaginacaoUtil {
	
	public void preparar(Criteria criteria, Pageable pageable){
		int paginaAtual = pageable.getPageNumber();
		int quantidadeRegistroPorPagina = pageable.getPageSize();
		int primeiroRegistro = paginaAtual * quantidadeRegistroPorPagina;
		
		Sort sort = pageable.getSort();		
		if (sort != null){
			Sort.Order order = sort.iterator().next();
			String property = order.getProperty();
			criteria.addOrder(order.isAscending() ? Order.asc(property) : Order.desc(property));
		}
		
		criteria.setFirstResult(primeiroRegistro);
		criteria.setMaxResults(quantidadeRegistroPorPagina);
		
	}

}

package com.luizmario.brewer.respository.helper.cliente;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.luizmario.brewer.model.Cliente;
import com.luizmario.brewer.respository.filter.ClienteFilter;

public interface ClienteRepositoryQuery {
	
	public Page<Cliente> filtrar(ClienteFilter filter, Pageable pageable);
}

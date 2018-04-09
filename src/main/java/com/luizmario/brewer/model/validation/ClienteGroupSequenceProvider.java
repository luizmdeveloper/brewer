package com.luizmario.brewer.model.validation;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

import com.luizmario.brewer.model.Cliente;

public class ClienteGroupSequenceProvider implements DefaultGroupSequenceProvider<Cliente> {

	@Override
	public List<Class<?>> getValidationGroups(Cliente cliente) {
		List<Class<?>> grupos = new ArrayList<>();		
		grupos.add(Cliente.class);
		
		if (isPessoaSelecionada(cliente)){
			System.out.println("Tipo Pessoa -> " + cliente.getTipoPessoa().getGrupo());
			grupos.add(cliente.getTipoPessoa().getGrupo());
		}
		
		return grupos;
	}
	
	private boolean isPessoaSelecionada(Cliente cliente){
		return cliente != null && cliente.getTipoPessoa() != null;
	}


}

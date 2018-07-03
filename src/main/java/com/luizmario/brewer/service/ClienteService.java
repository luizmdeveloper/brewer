package com.luizmario.brewer.service;

import java.util.Optional;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luizmario.brewer.model.Cliente;
import com.luizmario.brewer.respository.ClienteRepository;
import com.luizmario.brewer.service.execption.ClienteComVendaCadastradaException;
import com.luizmario.brewer.service.execption.CnpjCpfJaCadastradoException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Transactional
	public void salvar(Cliente cliente){
		Optional<Cliente> clienteExistente = clienteRepository.findByCpfOuCnpj(cliente.getCpfOuCnpjSemFormatacao());
		
		if (clienteExistente.isPresent() && !clienteExistente.get().equals(cliente)){
			throw new CnpjCpfJaCadastradoException("CPF/CNPJ já cadastrado");
		}
		
		clienteRepository.save(cliente);
	}

	@Transactional
	public void apagar(Cliente cliente) {
		try {
			clienteRepository.delete(cliente);
			clienteRepository.flush();
		} catch (PersistenceException e) {
			throw new ClienteComVendaCadastradaException("Impossível excluir cliente, devido ter venda cadastrada!");
		}		
	}

}

package com.luizmario.brewer.service.event.venda;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.luizmario.brewer.model.Cerveja;
import com.luizmario.brewer.model.ItemVenda;
import com.luizmario.brewer.respository.CervejasRepository;

@Component
public class VendaListner {
	
	@Autowired
	private CervejasRepository cervejaRepository;
	
	@EventListener
	public void vendaEmitida(VendaEvent vendaEvent) {
		for(ItemVenda itemVenda : vendaEvent.getVenda().getItens()) {
			Cerveja cerveja = cervejaRepository.findOne(itemVenda.getCerveja().getCodigo());
			cerveja.setQuantidadeEstoque(cerveja.getQuantidadeEstoque() - itemVenda.getQuantidade());
			cervejaRepository.save(cerveja);
		}
	}

}

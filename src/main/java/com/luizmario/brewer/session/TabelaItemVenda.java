package com.luizmario.brewer.session;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import com.luizmario.brewer.model.Cerveja;
import com.luizmario.brewer.model.ItemVenda;

@SessionScope
@Component
public class TabelaItemVenda {

	private List<ItemVenda> itens = new ArrayList<ItemVenda>();
	
	public BigDecimal getValorTotal() {
		return itens.stream()
				.map(ItemVenda::getValorTotal)
				.reduce(BigDecimal::add)
				.orElse(BigDecimal.ZERO);
	}
	
	public void adicionarItem(Cerveja cerveja, Integer quantidade) {
		Optional<ItemVenda> itemVendaOptional = buscarItemPorCerveja(cerveja);
	
		ItemVenda itemVenda = null;
		if (itemVendaOptional.isPresent()) {
			itemVenda = itemVendaOptional.get();
			itemVenda.setQuantidade(itemVenda.getQuantidade() + quantidade);
		}else {			
			itemVenda = new ItemVenda();
			itemVenda.setCerveja(cerveja);
			itemVenda.setQuantidade(quantidade);
			itemVenda.setValorUnitario(cerveja.getValor());
			
			itens.add(0, itemVenda);			
		}
		
	}
	
	public void atualizaQuantidadeItem(Cerveja cerveja, Integer quantidade) {
		ItemVenda itemVenda = buscarItemPorCerveja(cerveja).get();
		itemVenda.setQuantidade(quantidade);
	}
	
	public void removerItem(Cerveja cerveja) {
		int indice = IntStream.range(0, getTotaisItens())
					.filter(i -> itens.get(i).getCerveja().equals(cerveja))
					.findAny().getAsInt();
		itens.remove(indice);
	}
	
	public int getTotaisItens() {
		return itens.size();
	}

	public List<ItemVenda> getItem() {
		return itens;
	}
	
	private Optional<ItemVenda> buscarItemPorCerveja(Cerveja cerveja) {
		return itens.stream().filter(i -> i.getCerveja().equals(cerveja)).findAny();
	}
}

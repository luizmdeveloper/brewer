package com.luizmario.brewer.session;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
		ItemVenda item = new ItemVenda();
		item.setCerveja(cerveja);
		item.setQuantidade(quantidade);
		item.setValorUnitario(cerveja.getValor());
		
		itens.add(item);
	}
	
	public int totalItens() {
		return itens.size();
	}

	public List<ItemVenda> getItem() {
		return itens;
	}
	
}

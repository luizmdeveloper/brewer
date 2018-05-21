package com.luizmario.brewer.venda;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.luizmario.brewer.model.Cerveja;
import com.luizmario.brewer.model.ItemVenda;

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
	
}

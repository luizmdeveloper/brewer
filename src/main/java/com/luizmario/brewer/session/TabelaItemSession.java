package com.luizmario.brewer.session;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import com.luizmario.brewer.model.Cerveja;
import com.luizmario.brewer.model.ItemVenda;

@SessionScope
@Component
public class TabelaItemSession {
	
	private Set<TabelaItemVenda> tabelas = new HashSet<>();

	public void adicionarItem(String uuid, Cerveja cerveja, int quantidade) {
		TabelaItemVenda itemVenda = buscarTabelaPorUuid(uuid);
		itemVenda.adicionarItem(cerveja, quantidade);
		tabelas.add(itemVenda);
	}

	public void atualizaQuantidadeItem(String uuid, Cerveja cerveja, Integer quantidade) {
		TabelaItemVenda itemVenda = buscarTabelaPorUuid(uuid);
		itemVenda.atualizaQuantidadeItem(cerveja, quantidade);
	}

	public void removerItem(String uuid, Cerveja cerveja) {
		TabelaItemVenda itemVenda = buscarTabelaPorUuid(uuid);
		itemVenda.removerItem(cerveja);		
	}

	public List<ItemVenda> getItem(String uuid) {
		return buscarTabelaPorUuid(uuid).getItem();
	}

	public BigDecimal getValorTotalItens(String uuid) {
		return buscarTabelaPorUuid(uuid).getValorTotal();
	}

	private TabelaItemVenda buscarTabelaPorUuid(String uuid) {
		return tabelas.stream()
				.filter(i -> i.getUuid().equals(uuid))
				.findAny()
				.orElse(new TabelaItemVenda(uuid));
	}
}

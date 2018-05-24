package com.luizmario.brewer.session;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import com.luizmario.brewer.model.Cerveja;
import com.luizmario.brewer.model.ItemVenda;

class TabelaItemVenda {

	private String uuid;
	private List<ItemVenda> itens = new ArrayList<ItemVenda>();
	
	public TabelaItemVenda(String uuid) {
		this.uuid = uuid;
	}
		
	public String getUuid() {
		return uuid;
	}
	
	public List<ItemVenda> getItem() {
		return itens;
	}
		
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TabelaItemVenda other = (TabelaItemVenda) obj;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}

	private Optional<ItemVenda> buscarItemPorCerveja(Cerveja cerveja) {
		return itens.stream().filter(i -> i.getCerveja().equals(cerveja)).findAny();
	}
}
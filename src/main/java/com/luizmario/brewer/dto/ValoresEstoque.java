package com.luizmario.brewer.dto;

import java.math.BigDecimal;

public class ValoresEstoque {
	
	private BigDecimal valorTotal;
	private Long quantidadeTotal;
	
	public ValoresEstoque() {
	}
	
	public ValoresEstoque(BigDecimal valorTotal, Long quantidadeTotal) {
		this.valorTotal = valorTotal;
		this.quantidadeTotal = quantidadeTotal;
	}
	
	public BigDecimal getValorTotal() {
		return valorTotal;
	}
	
	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}
	
	public Long getQuantidadeTotal() {
		return quantidadeTotal;
	}
	
	public void setQuantidadeTotal(Long quantidadeTotal) {
		this.quantidadeTotal = quantidadeTotal;
	}
}

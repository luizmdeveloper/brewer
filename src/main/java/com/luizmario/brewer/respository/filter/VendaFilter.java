package com.luizmario.brewer.respository.filter;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.luizmario.brewer.model.StatusVenda;

public class VendaFilter {

	private Long codigo;
	private String nomeCliente;
	private String cpfOuCnpj;
	
	private LocalDate de;
	private LocalDate ate;

	private StatusVenda status;
	
	private BigDecimal valorMinimo;
	private BigDecimal valorMaximo;
	
	public Long getCodigo() {
		return codigo;
	}
	
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	
	public String getNomeCliente() {
		return nomeCliente;
	}
	
	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}
	
	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}
	
	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
	}
	
	public LocalDate getDe() {
		return de;
	}
	
	public void setDe(LocalDate de) {
		this.de = de;
	}
	
	public LocalDate getAte() {
		return ate;
	}
	
	public void setAte(LocalDate ate) {
		this.ate = ate;
	}
	
	public StatusVenda getStatus() {
		return status;
	}
	
	public void setStatus(StatusVenda status) {
		this.status = status;
	}
	
	public BigDecimal getValorMinimo() {
		return valorMinimo;
	}
	
	public void setValorMinimo(BigDecimal valorMinimo) {
		this.valorMinimo = valorMinimo;
	}
	
	public BigDecimal getValorMaximo() {
		return valorMaximo;
	}
	
	public void setValorMaximo(BigDecimal valorMaximo) {
		this.valorMaximo = valorMaximo;
	}	
}

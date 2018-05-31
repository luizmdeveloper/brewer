package com.luizmario.brewer.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luizmario.brewer.model.ItemVenda;
import com.luizmario.brewer.model.Venda;
import com.luizmario.brewer.respository.VendaRepository;

@Service
public class VendaService {

	@Autowired
	private VendaRepository vendaRepository;
	
	@Transactional
	public void salvar(Venda venda) {
		if (venda.isNova()) {
			venda.setDataCriacao(LocalDateTime.now());
		}
		
		BigDecimal valorTotalItens = venda.getItens().stream()
									.map(ItemVenda::getValorTotal)
									.reduce(BigDecimal::add)
									.get();
		
		BigDecimal valorTotal = calcularValorTotalVenda(valorTotalItens, venda.getValorFrete(), venda.getValorDesconto());		
		venda.setValorTotal(valorTotal);
		
		if (venda.getDataEntrega() != null) {
			venda.setDataHoraEntrega(LocalDateTime.of(venda.getDataEntrega(), venda.getHorarioEntrega()));
		}
		
		vendaRepository.save(venda);
	}

	private BigDecimal calcularValorTotalVenda(BigDecimal valorTotalItens, BigDecimal valorFrete, BigDecimal valorDesconto) {
		return valorTotalItens
				.add(Optional.ofNullable(valorFrete).orElse(BigDecimal.ZERO))
				.subtract(Optional.ofNullable(valorDesconto).orElse(BigDecimal.ZERO));
	}
}
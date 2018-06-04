package com.luizmario.brewer.controller.validator;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.luizmario.brewer.model.Venda;

@Component
public class VendaValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Venda.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors erros) {		
		Venda venda = (Venda) target;
		
		ValidationUtils.rejectIfEmpty(erros, "cliente.codigo", "", "Informe o cliente, para pequisar clique na consulta rápida");
		validarSeInformouItensNaVenda(erros, venda);
		validarDataHoraDaEntrega(erros, venda);
		validarValorTotalVendaNegativo(erros, venda);
	}

	private void validarValorTotalVendaNegativo(Errors erros, Venda venda) {
		if (venda.getValorTotal().compareTo(BigDecimal.ZERO) < 0) {
			erros.reject("", "Valor total da venda não pode ser negativa");
		}
	}

	private void validarDataHoraDaEntrega(Errors erros, Venda venda) {
		if (venda.getHorarioEntrega() != null && venda.getDataEntrega() == null) {
			erros.rejectValue("dataEntrega", "", "Se informou o horário de entrega, deve informar a data de entrega");
		}
	}

	private void validarSeInformouItensNaVenda(Errors erros, Venda venda) {
		if (venda.getItens().isEmpty()) {
			erros.reject("", "Adicione pelo menos uma cerveja a venda");
		}
	}
}
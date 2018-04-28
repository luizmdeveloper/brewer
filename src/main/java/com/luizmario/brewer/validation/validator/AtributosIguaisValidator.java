package com.luizmario.brewer.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder;

import org.apache.commons.beanutils.BeanUtils;

import com.luizmario.brewer.validation.AtributosIguais;

public class AtributosIguaisValidator implements ConstraintValidator<AtributosIguais, Object> {

	private String atributo;
	private String atributoConfirmacao;
	
	@Override
	public void initialize(AtributosIguais atributosIguais) {
		this.atributo = atributosIguais.atributo();
		this.atributoConfirmacao = atributosIguais.atributoConfirmacao();
	}

	@Override
	public boolean isValid(Object object, ConstraintValidatorContext context) {
		boolean valido = false;
		
		try {
			Object valorAtributo = BeanUtils.getProperty(object, this.atributo);
			Object valorAtributoConfirmacao = BeanUtils.getProperty(object, this.atributoConfirmacao);
			
			valido = atributosSaoNulos(valorAtributo, valorAtributoConfirmacao) || atributosIguais(valorAtributo, valorAtributoConfirmacao); 
		} catch (Exception e) {
			throw new RuntimeException("Erro ao recuperar dados dos atributos iguais", e);
		}
		
		if (!valido) {
			context.disableDefaultConstraintViolation();
			String mensagem = context.getDefaultConstraintMessageTemplate();
			
			ConstraintViolationBuilder violationBuilder = context.buildConstraintViolationWithTemplate(mensagem);
			violationBuilder.addPropertyNode(atributoConfirmacao).addConstraintViolation();			
		}
		
		return valido;
	}

	private boolean atributosIguais(Object valorAtributo, Object valorAtributoConfirmacao) {
		return valorAtributo != null && valorAtributo.equals(valorAtributoConfirmacao);
	}

	private boolean atributosSaoNulos(Object valorAtributo, Object valorAtributoConfirmacao) {
		return valorAtributo == null && valorAtributoConfirmacao == null;
	}

}

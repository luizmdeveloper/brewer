package com.luizmario.brewer.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.luizmario.brewer.model.Cerveja;

@Controller
public class CerverjasController {
	
	private static final Logger logger = LoggerFactory.getLogger(CerverjasController.class);
	
	@RequestMapping("/cerveja/novo")
	public String novo(Cerveja cerveja){
		return "cerveja/cadastro-cervejas";
	}
	
	@RequestMapping(value = "cerveja/novo", method = RequestMethod.POST)
	public String cadastrar(@Valid Cerveja cerveja, BindingResult result, Model model, RedirectAttributes attributes){
		
		if (result.hasErrors()){			
			return novo(cerveja);
		}
		
		attributes.addFlashAttribute("mensagem", "Cerveja cadastrada com sucesso!");
		System.out.println(" >>>> sku: " + cerveja.getSku());
		System.out.println(" >>>> nome: " + cerveja.getNome());
		System.out.println(" >>>> descrição: " + cerveja.getDescricao());
		return "redirect:/cerveja/novo";
	}

}

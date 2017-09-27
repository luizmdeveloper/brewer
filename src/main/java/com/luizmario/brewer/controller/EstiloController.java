package com.luizmario.brewer.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.luizmario.brewer.model.Estilo;
import com.luizmario.brewer.service.EstiloService;

@Controller
public class EstiloController {
	
	@Autowired
	private EstiloService estiloService;

	@RequestMapping("estilo/novo")
	public ModelAndView novo(Estilo estilo){
		ModelAndView mv = new ModelAndView("estilo/cadastro-estilos");
		return mv;
	}
	
	@RequestMapping(value = "estilo/novo", method = RequestMethod.POST)
	public ModelAndView cadastrar(@Valid Estilo estilo, BindingResult result, RedirectAttributes attributes){
		if (result.hasErrors()){			
			return novo(estilo);
		}
		
		estiloService.salvar(estilo);
		attributes.addFlashAttribute("mensagem", "Estilo cadastrado com sucesso!");
		
		return new ModelAndView("redirect:/estilo/novo");
	}
}

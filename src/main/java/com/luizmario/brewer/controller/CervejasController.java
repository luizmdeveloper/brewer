package com.luizmario.brewer.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.luizmario.brewer.model.Cerveja;
import com.luizmario.brewer.model.Origem;
import com.luizmario.brewer.model.Sabor;
import com.luizmario.brewer.respository.EstilosRepository;
import com.luizmario.brewer.service.CervejaService;

@Controller
@RequestMapping("/cerveja")
public class CervejasController {
		
	@Autowired
	private EstilosRepository estilosRepository;
	
	@Autowired
	private CervejaService cervejaService;
	
	private static final Logger logger = LoggerFactory.getLogger(CervejasController.class);
	
	@RequestMapping("/novo")
	public ModelAndView novo(Cerveja cerveja){
		ModelAndView mv = new ModelAndView("cerveja/cadastro-cervejas");
		mv.addObject("sabores", Sabor.values());
		mv.addObject("estilos", estilosRepository.findAll());
		mv.addObject("origens", Origem.values());
		return mv;
	}
	
	@RequestMapping(value = "/novo", method = RequestMethod.POST)
	public ModelAndView cadastrar(@Valid Cerveja cerveja, BindingResult result, Model model, RedirectAttributes attributes){
		
		if (result.hasErrors()){			
			return novo(cerveja);
		}
		
		cervejaService.salvar(cerveja);		
		attributes.addFlashAttribute("mensagem", "Cerveja cadastrada com sucesso!");
		
		return new ModelAndView("redirect:/cerveja/novo");
	}

}

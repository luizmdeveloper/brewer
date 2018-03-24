package com.luizmario.brewer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.luizmario.brewer.model.TipoPessoa;

@Controller
@RequestMapping("/cliente")
public class ClientesController {

	@RequestMapping("/novo")
	public ModelAndView novo(){
		ModelAndView mv = new ModelAndView("cliente/cadastro-clientes");
		mv.addObject("tipoPessoa", TipoPessoa.values());
		
		return mv;
	}
}

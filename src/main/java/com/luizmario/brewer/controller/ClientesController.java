package com.luizmario.brewer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.luizmario.brewer.model.TipoPessoa;
import com.luizmario.brewer.respository.EstadoRepository;

@Controller
@RequestMapping("/cliente")
public class ClientesController {
	
	@Autowired
	private EstadoRepository estado;

	@RequestMapping("/novo")
	public ModelAndView novo(){
		ModelAndView mv = new ModelAndView("cliente/cadastro-clientes");
		mv.addObject("tipoPessoa", TipoPessoa.values());
		mv.addObject("estados", estado.findAll());
		
		return mv;
	}
}

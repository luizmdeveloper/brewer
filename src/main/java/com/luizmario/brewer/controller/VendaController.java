package com.luizmario.brewer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/venda")
public class VendaController {
	
	@GetMapping("/nova")
	public ModelAndView nova() {
		ModelAndView mv = new ModelAndView("venda/cadastro-venda");
			
		return mv;
	}

}

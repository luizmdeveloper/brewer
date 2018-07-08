package com.luizmario.brewer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.luizmario.brewer.respository.VendaRepository;

@Controller
public class Dashboard {
	
	@Autowired
	private VendaRepository vendaRepository;
	
	@GetMapping("/")
	public ModelAndView dashboard() {
		ModelAndView mv = new ModelAndView("dashboard");
		
		mv.addObject("valorTotalNoAno", vendaRepository.valorTotalNoAno());		
		mv.addObject("valorTotalNoMes", vendaRepository.valorTotalNoMes());
		mv.addObject("valorTicketMedio", vendaRepository.valorTicketMedio());
		
		return mv;
	}
}


package com.luizmario.brewer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.luizmario.brewer.dto.ValoresEstoque;
import com.luizmario.brewer.respository.CervejasRepository;
import com.luizmario.brewer.respository.ClienteRepository;
import com.luizmario.brewer.respository.VendaRepository;

@Controller
public class Dashboard {
	
	@Autowired
	private VendaRepository vendaRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private CervejasRepository cervejaRepository;
	
	@GetMapping("/")
	public ModelAndView dashboard() {
		ModelAndView mv = new ModelAndView("dashboard");
		ValoresEstoque valoresEstoque = cervejaRepository.buscarDadosEstoque();

		mv.addObject("valorTotalNoAno", vendaRepository.valorTotalNoAno());		
		mv.addObject("valorTotalNoMes", vendaRepository.valorTotalNoMes());
		mv.addObject("valorTicketMedio", vendaRepository.valorTicketMedio());
		mv.addObject("quantidadeCliente", clienteRepository.count());
		mv.addObject("valorTotalEstoque", valoresEstoque.getValorTotal());
		mv.addObject("quantidadeTotalEstoque", valoresEstoque.getQuantidadeTotal());
		
		return mv;
	}
}


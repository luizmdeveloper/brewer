package com.luizmario.brewer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.luizmario.brewer.model.Cerveja;
import com.luizmario.brewer.respository.CervejasRepository;
import com.luizmario.brewer.session.TabelaItemVenda;

@Controller
@RequestMapping("/venda")
public class VendaController {
	
	@Autowired
	private CervejasRepository cervejasRepository;
	
	@Autowired
	private TabelaItemVenda tabelaItemVenda;
	
	@GetMapping("/nova")
	public ModelAndView nova() {
		ModelAndView mv = new ModelAndView("venda/cadastro-venda");
			
		return mv;
	}
	
	@PostMapping("/item")
	public ModelAndView adicionarItem(Long codigoCerveja) {		
		Cerveja cerveja = cervejasRepository.findOne(codigoCerveja);		
		tabelaItemVenda.adicionarItem(cerveja, 1);
		ModelAndView mv = new ModelAndView("venda/tabela-item-venda");
		mv.addObject("itens", tabelaItemVenda.getItem());
		return mv;
	} 

}

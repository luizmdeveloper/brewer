package com.luizmario.brewer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
		return mvTabelaItemVenda();
	} 
	
	@PutMapping("/item/{codigoCerveja}")
	public ModelAndView atualizarQuantidadeItem(@PathVariable("codigoCerveja") Cerveja cerveja, Integer quantidade) {
		tabelaItemVenda.atualizaQuantidadeItem(cerveja, quantidade);
		return mvTabelaItemVenda();
	}
	
	@DeleteMapping("/item/{codigoCerveja}")
	public ModelAndView removerCerveja(@PathVariable("codigoCerveja") Cerveja cerveja) {
		tabelaItemVenda.removerItem(cerveja);
		return mvTabelaItemVenda();
	}

	private ModelAndView mvTabelaItemVenda() {
		ModelAndView mv = new ModelAndView("venda/tabela-item-venda");
		mv.addObject("itens", tabelaItemVenda.getItem());
		return mv;
	}

}

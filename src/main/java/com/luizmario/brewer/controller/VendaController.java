package com.luizmario.brewer.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.luizmario.brewer.model.Cerveja;
import com.luizmario.brewer.model.Venda;
import com.luizmario.brewer.respository.CervejasRepository;
import com.luizmario.brewer.security.UsuarioSistema;
import com.luizmario.brewer.service.VendaService;
import com.luizmario.brewer.session.TabelaItemSession;

@Controller
@RequestMapping("/venda")
public class VendaController {
	
	@Autowired
	private CervejasRepository cervejasRepository;
	
	@Autowired
	private TabelaItemSession tabelaItem;
	
	@Autowired
	private VendaService vendaService;
	
	@GetMapping("/nova")
	public ModelAndView nova(Venda venda) {
		ModelAndView mv = new ModelAndView("venda/cadastro-venda");
		venda.setUuid(UUID.randomUUID().toString());
		
		return mv;
	}
	
	@PostMapping("/nova")
	public ModelAndView salvar(Venda venda, RedirectAttributes attributes, @AuthenticationPrincipal UsuarioSistema usuarioSistema) {
		venda.setUsuario(usuarioSistema.getUsuario());
		venda.adicionarItens(tabelaItem.getItens(venda.getUuid()));
		
		vendaService.salvar(venda);
		
		attributes.addFlashAttribute("messagem", "venda salva com sucesso!");
		return new ModelAndView("redirect:/venda/nova");
	}
	
	@PostMapping("/item")
	public ModelAndView adicionarItem(Long codigoCerveja, String uuid) {		
		Cerveja cerveja = cervejasRepository.findOne(codigoCerveja);		
		tabelaItem.adicionarItem(uuid, cerveja, 1);
		return mvTabelaItemVenda(uuid);
	} 
	
	@PutMapping("/item/{codigoCerveja}")
	public ModelAndView atualizarQuantidadeItem(@PathVariable("codigoCerveja") Cerveja cerveja, Integer quantidade, String uuid) {
		tabelaItem.atualizaQuantidadeItem(uuid, cerveja, quantidade);
		return mvTabelaItemVenda(uuid);
	}
	
	@DeleteMapping("/item/{uuid}/{codigoCerveja}")
	public ModelAndView removerCerveja(@PathVariable String uuid, @PathVariable("codigoCerveja") Cerveja cerveja) {
		tabelaItem.removerItem(uuid, cerveja);
		return mvTabelaItemVenda(uuid);
	}

	private ModelAndView mvTabelaItemVenda(String uuid) {
		ModelAndView mv = new ModelAndView("venda/tabela-item-venda");
		mv.addObject("itens", tabelaItem.getItens(uuid));
		mv.addObject("valorTotal", tabelaItem.getValorTotalItens(uuid));
		
		return mv;
	}
}

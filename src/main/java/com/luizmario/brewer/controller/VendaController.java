package com.luizmario.brewer.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.luizmario.brewer.controller.page.PageWrapper;
import com.luizmario.brewer.controller.validator.VendaValidator;
import com.luizmario.brewer.mailer.Mailer;
import com.luizmario.brewer.model.Cerveja;
import com.luizmario.brewer.model.ItemVenda;
import com.luizmario.brewer.model.StatusVenda;
import com.luizmario.brewer.model.Venda;
import com.luizmario.brewer.respository.CervejasRepository;
import com.luizmario.brewer.respository.VendaRepository;
import com.luizmario.brewer.respository.filter.VendaFilter;
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
	
	@Autowired
	private VendaValidator vendaValidator;
	
	@Autowired
	private VendaRepository vendaRepository;
	
	@Autowired
	private Mailer mailer;
	
	@InitBinder("venda")
	public void iniciarValidadores(WebDataBinder binder) {
		binder.setValidator(vendaValidator);
	}

	@GetMapping
	public ModelAndView buscar(VendaFilter vendaFilter,@PageableDefault(size = 5) Pageable page, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("venda/pesquisar-venda");
		PageWrapper<Venda> pagina = new PageWrapper<>(vendaRepository.filtrar(vendaFilter, page), httpServletRequest);		
		mv.addObject("pagina", pagina);
		mv.addObject("todosStatus", StatusVenda.values());
				
		return mv;
	}
	
	@GetMapping("/nova")
	public ModelAndView nova(Venda venda) {
		ModelAndView mv = new ModelAndView("venda/cadastro-venda");
		
		setUuid(venda);
		
		mv.addObject("itens", venda.getItens());
		mv.addObject("valorFrete", venda.getValorFrete());
		mv.addObject("valorDesconto", venda.getValorDesconto());
		mv.addObject("valorTotalItens", tabelaItem.getValorTotalItens(venda.getUuid()));
		
		return mv;
	}
	
	@PostMapping(value="/nova", params="salvar")
	public ModelAndView salvar(Venda venda, BindingResult result, RedirectAttributes attributes, @AuthenticationPrincipal UsuarioSistema usuarioSistema) {
		venda.setUsuario(usuarioSistema.getUsuario());
		validarVenda(venda, result);

		if (result.hasErrors()) {
			return nova(venda);
		}
		
		venda = vendaService.salvar(venda);
		
		attributes.addFlashAttribute("mensagem", "Venda salva com sucesso!");
		return new ModelAndView("redirect:/venda/nova");
	}
	
	@PostMapping(value="/nova", params="emitir")
	public ModelAndView emitir(Venda venda, BindingResult result, RedirectAttributes attributes, @AuthenticationPrincipal UsuarioSistema usuarioSistema) {
		venda.setUsuario(usuarioSistema.getUsuario());
		validarVenda(venda, result);

		if (result.hasErrors()) {
			return nova(venda);
		}
		
		vendaService.emitir(venda);
		
		attributes.addFlashAttribute("mensagem", "Venda emitida com sucesso!");
		return new ModelAndView("redirect:/venda/nova");
	}


	@PostMapping(value="/nova", params="enviarEmail")
	public ModelAndView enviarEmail(Venda venda, BindingResult result, RedirectAttributes attributes, @AuthenticationPrincipal UsuarioSistema usuarioSistema) {
		venda.setUsuario(usuarioSistema.getUsuario());
		validarVenda(venda, result);

		if (result.hasErrors()) {
			return nova(venda);
		}
		
		venda = vendaService.salvar(venda);
		mailer.enviar(venda);
				
		attributes.addFlashAttribute("mensagem", String.format("Venda salva nº %d com sucesso e enviada para email", venda.getCodigo()));
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
	
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable Long codigo) {
		Venda venda = vendaRepository.buscarComItens(codigo);		

		setUuid(venda);
		for (ItemVenda item : venda.getItens()) {
			tabelaItem.adicionarItem(venda.getUuid(), item.getCerveja(), item.getQuantidade());
		}		
		
		ModelAndView mv = nova(venda);
		mv.addObject(venda);
		return mv;
	}
	
	@PostMapping(value="/nova", params = "cancelar")
	public ModelAndView cancelar(Venda venda, BindingResult result, RedirectAttributes attributes, @AuthenticationPrincipal UsuarioSistema usuarioSistema) {
		try {
			vendaService.cancelar(venda);
		} catch (AccessDeniedException e) {
			return new ModelAndView("/acesso-negado");	
		}
		
		attributes.addFlashAttribute("mensagem", "Venda cancelada com sucesso!");
		return new ModelAndView("redirect:/venda/"+ venda.getCodigo());
	}
	
	
	private void setUuid(Venda venda) {
		if (StringUtils.isEmpty(venda.getUuid())) {
			venda.setUuid(UUID.randomUUID().toString());
		}
	}
	
	private void validarVenda(Venda venda, BindingResult result) {
		venda.adicionarItens(tabelaItem.getItens(venda.getUuid()));
		venda.calcularValoTotal();
		
		vendaValidator.validate(venda, result);
	}
	
	private ModelAndView mvTabelaItemVenda(String uuid) {
		ModelAndView mv = new ModelAndView("venda/tabela-item-venda");
		mv.addObject("itens", tabelaItem.getItens(uuid));
		mv.addObject("valorTotal", tabelaItem.getValorTotalItens(uuid));
		
		return mv;
	}
}

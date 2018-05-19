package com.luizmario.brewer.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.luizmario.brewer.controller.page.PageWrapper;
import com.luizmario.brewer.model.Cliente;
import com.luizmario.brewer.model.TipoPessoa;
import com.luizmario.brewer.respository.ClienteRepository;
import com.luizmario.brewer.respository.EstadoRepository;
import com.luizmario.brewer.respository.filter.ClienteFilter;
import com.luizmario.brewer.service.ClienteService;
import com.luizmario.brewer.service.execption.CnpjCpfJaCadastradoException;

@Controller
@RequestMapping("/cliente")
public class ClientesController {
	
	@Autowired
	private EstadoRepository estado;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@GetMapping
	public ModelAndView buscar(ClienteFilter clienteFilter,BindingResult result, @PageableDefault(size = 5) Pageable page, HttpServletRequest httpServletRequest){
		ModelAndView mv = new ModelAndView("cliente/pesquisa-clientes");
		
		PageWrapper<Cliente> pagina = new PageWrapper<>(clienteRepository.filtrar(clienteFilter, page), httpServletRequest);		
		mv.addObject("pagina", pagina);
			
		return mv;
	}

	@RequestMapping("/novo")
	public ModelAndView novo(Cliente cliente){
		ModelAndView mv = new ModelAndView("cliente/cadastro-clientes");
		mv.addObject("tipoPessoa", TipoPessoa.values());
		mv.addObject("estados", estado.findAll());
		
		return mv;
	}
	
	@PostMapping("/novo")
	public ModelAndView salvar (@Valid Cliente cliente, BindingResult result, Model model, RedirectAttributes attributes){
		if (result.hasErrors()){
			return novo(cliente);
		}
		
		try {			
			clienteService.salvar(cliente);
		} catch (CnpjCpfJaCadastradoException e) {
			result.rejectValue("cpfOuCnpj", e.getMessage(), e.getMessage());
			return novo(cliente);
		}
		
		attributes.addFlashAttribute("mensagem", "Cliente cadastrada com sucesso!");		
		return new ModelAndView("redirect:/cliente/novo");
	}
	
	@RequestMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody List<Cliente> pesquisar(String nome){
		validarTamanhoNome(nome);
		return clienteRepository.findByNomeStartingWithIgnoreCase(nome);
		                         
	}

	private void validarTamanhoNome(String nome) {
		if (StringUtils.isEmpty(nome) || nome.length() < 3) {
			throw new IllegalArgumentException();
		}		
	}
	
	@ExceptionHandler({IllegalArgumentException.class})
	private ResponseEntity<Void> tratarIllegalArgumentException(IllegalArgumentException e){
		return ResponseEntity.badRequest().build();
	}
}

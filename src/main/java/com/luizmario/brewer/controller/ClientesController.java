package com.luizmario.brewer.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.luizmario.brewer.model.Cliente;
import com.luizmario.brewer.model.TipoPessoa;
import com.luizmario.brewer.respository.EstadoRepository;
import com.luizmario.brewer.service.ClienteService;
import com.luizmario.brewer.service.execption.CnpjCpfJaCadastradoException;

@Controller
@RequestMapping("/cliente")
public class ClientesController {
	
	@Autowired
	private EstadoRepository estado;
	
	@Autowired
	private ClienteService clienteService;

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
}

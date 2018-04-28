package com.luizmario.brewer.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.luizmario.brewer.model.Usuario;
import com.luizmario.brewer.respository.GrupoRepository;
import com.luizmario.brewer.service.UsuarioService;
import com.luizmario.brewer.service.execption.EmailUsuarioJaCadastradoException;
import com.luizmario.brewer.service.execption.SenhaUsuarioNaoPreenchidaException;

@Controller
@RequestMapping("/usuario")
public class UsuariosController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private GrupoRepository grupoRepository;
	
	@RequestMapping("/novo")
	public ModelAndView novo(Usuario usuario){
		ModelAndView mv = new ModelAndView("usuario/cadastro-usuarios");		
		mv.addObject("grupos", grupoRepository.findAll());
		
		return mv;
	}
	
	@PostMapping("/novo")
	public ModelAndView salvar(@Valid Usuario usuario, BindingResult result, RedirectAttributes attributes) {			
		if (result.hasErrors()) {
			return novo(usuario);
		}
		
		try {
			usuarioService.salvar(usuario);
		}catch (EmailUsuarioJaCadastradoException e) {
			result.rejectValue("email", e.getMessage(), e.getMessage());
			return novo(usuario);
		}catch(SenhaUsuarioNaoPreenchidaException e) {
			result.rejectValue("senha", e.getMessage(), e.getMessage());
			return novo(usuario);
		}
		
		attributes.addFlashAttribute("mensagem", "Usuário cadastrado com sucesso!");		
		return new ModelAndView("redirect:/usuario/novo");		
	}

}

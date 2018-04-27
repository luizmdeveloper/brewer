package com.luizmario.brewer.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.luizmario.brewer.model.Usuario;

@Controller
@RequestMapping("/usuario")
public class UsuariosController {
	
	@RequestMapping("/novo")
	public ModelAndView novo(Usuario usuario){
		ModelAndView mv = new ModelAndView("usuario/cadastro-usuarios");
		
		return mv;
	}
	
	@PostMapping("/novo")
	public ModelAndView salvar(@Valid Usuario usuario, BindingResult result, RedirectAttributes attributes) {		
		if (result.hasErrors()) {
			return novo(usuario);
		}
		
		attributes.addFlashAttribute("mensagem", "Usuário cadastrado com sucesso!");		
		return new ModelAndView("redirect:/usurario/novo");		
	}

}

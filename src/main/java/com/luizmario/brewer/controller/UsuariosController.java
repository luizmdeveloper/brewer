package com.luizmario.brewer.controller;

import java.awt.print.Pageable;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.luizmario.brewer.model.Usuario;
import com.luizmario.brewer.respository.GrupoRepository;
import com.luizmario.brewer.respository.UsuarioRepository;
import com.luizmario.brewer.respository.filter.UsuarioFilter;
import com.luizmario.brewer.service.StatusUsuario;
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
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
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
	
	@GetMapping
	public ModelAndView buscar(UsuarioFilter usuarioFilter, @PageableDefault(size = 5) Pageable page, HttpServletResponse httpServletRequest) {
		ModelAndView mv = new ModelAndView("usuario/pesquisar-usuarios");
		mv.addObject("usuarios", usuarioRepository.findAll(usuarioFilter));
		mv.addObject("grupos", grupoRepository.findAll());
		
		return mv;
	}
	
	@PutMapping("/status")
	@ResponseStatus(HttpStatus.OK)
	public void altetarStatus(@RequestParam("codigos[]") Long codigos[], @RequestParam("status") StatusUsuario status) {
		usuarioService.alterarStauts(codigos, status);
	}

}

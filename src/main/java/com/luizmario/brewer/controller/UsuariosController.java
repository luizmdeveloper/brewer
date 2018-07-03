package com.luizmario.brewer.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.luizmario.brewer.controller.page.PageWrapper;
import com.luizmario.brewer.model.Usuario;
import com.luizmario.brewer.respository.GrupoRepository;
import com.luizmario.brewer.respository.UsuarioRepository;
import com.luizmario.brewer.respository.filter.UsuarioFilter;
import com.luizmario.brewer.service.StatusUsuario;
import com.luizmario.brewer.service.UsuarioService;
import com.luizmario.brewer.service.execption.EmailUsuarioJaCadastradoException;
import com.luizmario.brewer.service.execption.SenhaUsuarioNaoPreenchidaException;
import com.luizmario.brewer.service.execption.UsuarioComVendaCadastradaException;

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
		ModelAndView mv = new ModelAndView("usuario/cadastro-usuario");		
		mv.addObject("grupos", grupoRepository.findAll());
		
		return mv;
	}
	
	@PostMapping(value = {"/novo", "{\\d+}"})
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
		
		attributes.addFlashAttribute("mensagem", "Usuário salvo com sucesso!");
		return new ModelAndView("redirect:/usuario/novo");		
	}
	
	@GetMapping
	public ModelAndView buscar(UsuarioFilter usuarioFilter, @PageableDefault(size = 5) Pageable page, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("usuario/pesquisar-usuario");
		mv.addObject("grupos", grupoRepository.findAll());

		PageWrapper<Usuario> pagina = new PageWrapper<>(usuarioRepository.filtar(usuarioFilter, page), httpServletRequest);		
		mv.addObject("pagina", pagina);
		
		return mv;
	}
	
	@DeleteMapping("{codigo}")
	public @ResponseBody ResponseEntity<?> apagar(@PathVariable("codigo") Usuario usuario){
		try {
			usuarioService.apagar(usuario);
		} catch (UsuarioComVendaCadastradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/status")
	@ResponseStatus(HttpStatus.OK)
	public void altetarStatus(@RequestParam("codigos[]") Long codigos[], @RequestParam("status") StatusUsuario status) {
		usuarioService.alterarStauts(codigos, status);
	}
	
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable Long codigo) {
		Usuario usuario = usuarioRepository.buscarPor(codigo);
		ModelAndView mv = novo(usuario);
		mv.addObject(usuario);
		
		return mv;
	}

}

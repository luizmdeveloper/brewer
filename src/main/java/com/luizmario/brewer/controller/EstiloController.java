package com.luizmario.brewer.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.luizmario.brewer.controller.page.PageWrapper;
import com.luizmario.brewer.model.Estilo;
import com.luizmario.brewer.respository.EstilosRepository;
import com.luizmario.brewer.respository.filter.EstiloFilter;
import com.luizmario.brewer.service.EstiloService;
import com.luizmario.brewer.service.execption.EstiloComCervejaCadastradaException;
import com.luizmario.brewer.service.execption.NomeEstiloJaCadastradoException;

@Controller
@RequestMapping("/estilo")
public class EstiloController {
	
	@Autowired
	private EstiloService estiloService;
	
	@Autowired
	private EstilosRepository estiloRepository;

	@GetMapping
	public ModelAndView buscar(EstiloFilter estiloFilter, @PageableDefault(size = 5) Pageable page, HttpServletRequest httpServletRequest){
		ModelAndView mv = new ModelAndView("estilo/pesquisa-estilo");		
		PageWrapper<Estilo> pagina = new PageWrapper<Estilo>(estiloRepository.filtrar(estiloFilter, page), httpServletRequest);		
		mv.addObject("pagina", pagina);		
		return mv;
	}
	
	@RequestMapping("/novo")
	public ModelAndView novo(Estilo estilo){
		ModelAndView mv = new ModelAndView("estilo/cadastro-estilo");
		return mv;
	}
	
	@RequestMapping(value = {"/novo", "{\\d+}" }, method = RequestMethod.POST)
	public ModelAndView cadastrar(@Valid Estilo estilo, BindingResult result, RedirectAttributes attributes){
		if (result.hasErrors()){			
			return novo(estilo);
		}
		
		try {		
			estiloService.salvar(estilo);
		} catch (NomeEstiloJaCadastradoException e) {
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return novo(estilo);
		}
		
		attributes.addFlashAttribute("mensagem", "Estilo salvo com sucesso!");		
		return new ModelAndView("redirect:/estilo/novo");
	}
	
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") Estilo estilo) {
		ModelAndView mv = new ModelAndView("estilo/cadastro-estilo");
		mv.addObject(estilo);
		return mv;
	}
	
	@DeleteMapping("/{codigo}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") Estilo estilo) {
		try {
			estiloService.apagar(estilo);
		} catch (EstiloComCervejaCadastradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}
	
	@RequestMapping(method= RequestMethod.POST, consumes={MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseEntity<?> salvar(@RequestBody  @Valid Estilo estilo,BindingResult result){
		if (result.hasErrors()){			
			return ResponseEntity.badRequest().body(result.getFieldError("nome").getDefaultMessage());
		}
		
		estilo = estiloService.salvar(estilo);			
		return ResponseEntity.ok(estilo);
	}
}

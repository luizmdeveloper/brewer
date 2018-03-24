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
import org.springframework.web.bind.annotation.GetMapping;
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
import com.luizmario.brewer.service.execption.NomeEstiloJaCadastradoException;

@Controller
@RequestMapping("/estilo")
public class EstiloController {
	
	@Autowired
	private EstiloService estiloService;
	
	@Autowired
	private EstilosRepository estilos;

	@RequestMapping("/novo")
	public ModelAndView novo(Estilo estilo){
		ModelAndView mv = new ModelAndView("estilo/cadastro-estilos");
		return mv;
	}
	
	@RequestMapping(value = "/novo", method = RequestMethod.POST)
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
		
		attributes.addFlashAttribute("mensagem", "Estilo cadastrado com sucesso!");		
		return new ModelAndView("redirect:/estilo/novo");
	}
	
	@RequestMapping(method= RequestMethod.POST, consumes={MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseEntity<?> salvar(@RequestBody  @Valid Estilo estilo,BindingResult result){
		
		if (result.hasErrors()){			
			return ResponseEntity.badRequest().body(result.getFieldError("nome").getDefaultMessage());
		}
		
		estilo = estiloService.salvar(estilo);
			
		return ResponseEntity.ok(estilo);
	}
	
	@GetMapping
	public ModelAndView buscar(EstiloFilter estiloFilter, BindingResult result, @PageableDefault(size = 5) Pageable page, HttpServletRequest httpServletRequest){
		ModelAndView mv = new ModelAndView("estilo/pesquisa-estilo");		
		PageWrapper<Estilo> pagina = new PageWrapper<Estilo>(estilos.filtrar(estiloFilter, page), httpServletRequest);		
		mv.addObject("pagina", pagina);		
		return mv;
	}
}
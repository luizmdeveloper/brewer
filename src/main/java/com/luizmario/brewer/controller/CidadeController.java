package com.luizmario.brewer.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.luizmario.brewer.controller.page.PageWrapper;
import com.luizmario.brewer.model.Cidade;
import com.luizmario.brewer.respository.CidadeRepository;
import com.luizmario.brewer.respository.EstadoRepository;
import com.luizmario.brewer.respository.filter.CidadeFilter;
import com.luizmario.brewer.service.CidadeService;
import com.luizmario.brewer.service.execption.CidadeComClienteCadastradaException;

@Controller
@RequestMapping("/cidade")
public class CidadeController {
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CidadeService cidadeService;
	
	@GetMapping
	public ModelAndView buscar(CidadeFilter cidadeFilter, BindingResult result, @PageableDefault(size = 5) Pageable page, HttpServletRequest httpServletRequest){
		ModelAndView mv = new ModelAndView("cidade/pesquisa-cidade");
		mv.addObject("estados", estadoRepository.findAll());
		PageWrapper<Cidade> pagina = new PageWrapper<>(cidadeRepository.filtrar(cidadeFilter, page), httpServletRequest);		
		mv.addObject("pagina", pagina);
		
		return mv;
	}
	
	@RequestMapping("/nova")
	public ModelAndView nova(Cidade cidade){
		ModelAndView mv = new ModelAndView("cidade/cadastro-cidade");
		mv.addObject("estados", estadoRepository.findAll());
		
		return mv;
	}
	
	@PostMapping(value= {"/nova", "{\\d+}"})
	@CacheEvict(value = "cidades", key = "#cidade.estado.codigo", condition = "#cidade.temEstado()")
	public ModelAndView novo(@Valid Cidade cidade, BindingResult result, Model model, RedirectAttributes attributes){
		if (result.hasErrors()){
			return nova(cidade);
		}
		
		cidadeService.salvar(cidade);
		attributes.addFlashAttribute("mensagem", "Cidade salva com sucesso!");
		
		return new ModelAndView("redirect:/cidade/nova");
	}
	
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") Long codigo) {
		Cidade cidade = cidadeRepository.buscarComEstado(codigo);
		ModelAndView mv = nova(cidade);
		mv.addObject(cidade);
		return mv;
	}
	
	@DeleteMapping("/{codigo}")
	public @ResponseBody ResponseEntity<?> apagar(@PathVariable("codigo") long codigo){
		try {
			cidadeService.apagar(codigo);
		} catch (CidadeComClienteCadastradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}

	@Cacheable(value = "cidades", key="#codigoEstado")
	@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Cidade> buscarEstado(@RequestParam(name = "estado", defaultValue = "-1") Long codigoEstado){
		return cidadeRepository.findByEstadoCodigo(codigoEstado);
	}

}

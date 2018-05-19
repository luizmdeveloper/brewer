package com.luizmario.brewer.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.luizmario.brewer.controller.page.PageWrapper;
import com.luizmario.brewer.dto.CervejaDTO;
import com.luizmario.brewer.model.Cerveja;
import com.luizmario.brewer.model.Origem;
import com.luizmario.brewer.model.Sabor;
import com.luizmario.brewer.respository.CervejasRepository;
import com.luizmario.brewer.respository.EstilosRepository;
import com.luizmario.brewer.respository.filter.CervejaFilter;
import com.luizmario.brewer.service.CervejaService;

@Controller
@RequestMapping("/cerveja")
public class CervejasController {
		
	@Autowired
	private EstilosRepository estilosRepository;
	
	@Autowired
	private CervejaService cervejaService;
	
	@Autowired
	private CervejasRepository cervejaRepository;

	
	@RequestMapping("/nova")
	public ModelAndView novo(Cerveja cerveja){
		ModelAndView mv = new ModelAndView("cerveja/cadastro-cervejas");
		mv.addObject("sabores", Sabor.values());
		mv.addObject("estilos", estilosRepository.findAll());
		mv.addObject("origens", Origem.values());
		return mv;
	}
	
	@RequestMapping(value = "/nova", method = RequestMethod.POST)
	public ModelAndView cadastrar(@Valid Cerveja cerveja, BindingResult result, Model model, RedirectAttributes attributes){
		
		if (result.hasErrors()){
			return novo(cerveja);
		}
		
		cervejaService.salvar(cerveja);		
		attributes.addFlashAttribute("mensagem", "Cerveja cadastrada com sucesso!");
		
		return new ModelAndView("redirect:/cerveja/nova");
	}
	
	@GetMapping
	public ModelAndView buscar(CervejaFilter cervejaFilter, BindingResult result, @PageableDefault(size = 5) Pageable page, HttpServletRequest httpServletRequest){
		ModelAndView mv = new ModelAndView("cerveja/pesquisa-cerveja");
		mv.addObject("estilos", estilosRepository.findAll());
		mv.addObject("sabores", Sabor.values());
		mv.addObject("origens", Origem.values());
		
		PageWrapper<Cerveja> pagina = new PageWrapper<>(cervejaRepository.filtar(cervejaFilter, page), httpServletRequest);		
		mv.addObject("pagina", pagina);
		
		return mv;
	}
	
	@RequestMapping("/filtro")
	public @ResponseBody List<CervejaDTO> buscar(String skuOuNome){
		return cervejaRepository.buscarPorSkuOuNome(skuOuNome);
	}

}

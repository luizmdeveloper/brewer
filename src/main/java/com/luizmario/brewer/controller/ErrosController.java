package com.luizmario.brewer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrosController {
	
	@GetMapping("/404")
	public String paginaNaoEncontrada() {
		return "pagina-nao-encontrado";
	}

	@GetMapping("/500")
	public String erroInterno() {
		return "erro-interno";
	}

}

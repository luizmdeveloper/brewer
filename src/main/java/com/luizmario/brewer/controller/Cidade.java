package com.luizmario.brewer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Cidade {
	
	@RequestMapping("/cidade/novo")
	public String novo(){
		return "cidade/cadastro-cidades";
	}

}

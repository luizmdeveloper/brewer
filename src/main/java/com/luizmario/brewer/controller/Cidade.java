package com.luizmario.brewer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cidade")
public class Cidade {
	
	@RequestMapping("/novo")
	public String novo(){
		return "cidade/cadastro-cidades";
	}

}

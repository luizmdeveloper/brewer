package com.luizmario.brewer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class EstiloController {

	@RequestMapping("estilo/novo")
	public String novo(){
		return "estilo/cadastro-estilos";
	}
}

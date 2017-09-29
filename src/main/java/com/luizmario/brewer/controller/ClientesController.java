package com.luizmario.brewer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cliente")
public class ClientesController {

	@RequestMapping("/novo")
	public String novo(){
		return "cliente/cadastro-clientes";
	}
}

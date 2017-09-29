package com.luizmario.brewer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuario")
public class UsuariosController {
	
	@RequestMapping("/novo")
	public String novo(){
		return "usuario/cadastro-usuarios";
	}

}

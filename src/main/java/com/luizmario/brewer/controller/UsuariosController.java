package com.luizmario.brewer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UsuariosController {
	
	@RequestMapping("usuario/novo")
	public String novo(){
		return "usuario/cadastro-usuarios";
	}

}

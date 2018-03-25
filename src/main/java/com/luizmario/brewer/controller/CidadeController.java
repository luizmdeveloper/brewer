package com.luizmario.brewer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.luizmario.brewer.model.Cidade;
import com.luizmario.brewer.respository.CidadeRepository;

@Controller
@RequestMapping("/cidade")
public class CidadeController {
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@RequestMapping("/novo")
	public String novo(){
		return "cidade/cadastro-cidades";
	}
	
	
	@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Cidade> buscarEstado(@RequestParam(name = "estado", defaultValue = "-1") Long codigoEstado){
		return cidadeRepository.findByEstadoCodigo(codigoEstado);
	}

}

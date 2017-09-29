package com.luizmario.brewer.controller.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.luizmario.brewer.service.execption.NomeEstiloJaCadastradoException;

@ControllerAdvice
public class ControllerAdviceExceptionHandler{
	
	@ExceptionHandler(NomeEstiloJaCadastradoException.class)
	public ResponseEntity<String> handelerNomeEstiloJaCadastradoException(NomeEstiloJaCadastradoException e){
		return ResponseEntity.badRequest().body(e.getMessage());
	}
}

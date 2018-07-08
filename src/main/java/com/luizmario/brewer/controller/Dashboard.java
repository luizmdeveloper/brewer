package com.luizmario.brewer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Dashboard {
	
	@GetMapping("/")
	public ModelAndView dashboard() {
		ModelAndView mv = new ModelAndView("dashboard");		
		return mv;
	}
}

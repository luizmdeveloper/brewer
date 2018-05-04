package com.luizmario.brewer.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GeracaoSenha {
	
	public static void main(String[] args) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		System.out.println(passwordEncoder.encode("admin"));
	}

}

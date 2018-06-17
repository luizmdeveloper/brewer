package com.luizmario.brewer.mailer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.luizmario.brewer.model.Venda;

@Component
public class Mailer {
	
	@Autowired
	private JavaMailSender mailSender;

	@Async
	public void enviar(Venda venda) {
		SimpleMailMessage mensagem = new SimpleMailMessage();
		mensagem.setFrom("luizmariodev@gmail.com");
		mensagem.setTo(venda.getCliente().getEmail());
		mensagem.setSubject("Nova venda ");
		mensagem.setText("Obrigado, por efetuar a venda com a brewer!!!");
		
		mailSender.send(mensagem);
	}

}

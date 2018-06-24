package com.luizmario.brewer.mailer;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.luizmario.brewer.model.Cerveja;
import com.luizmario.brewer.model.ItemVenda;
import com.luizmario.brewer.model.Venda;
import com.luizmario.brewer.storage.FotoStorage;

@Component
public class Mailer {
	
	public static final Logger logger = LoggerFactory.getLogger(Mailer.class);
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private TemplateEngine thymeleaf;
		
	@Autowired
	private FotoStorage fotoStorage;

	@Async
	public void enviar(Venda venda) {
		
		Context context = new Context(new Locale("pt", "BR"));
		context.setVariable("venda", venda);
		context.setVariable("logo", "logo");
		
		Map<String, String> fotos = new HashMap<>();
		boolean existeCervejaSemFoto = false;
		for(ItemVenda item : venda.getItens()) {
			Cerveja cerveja = item.getCerveja();
			if (cerveja.temFoto()) {
				String cid = "foto-" + cerveja.getCodigo();
				fotos.put(cid, cerveja.getFoto() + "|" + cerveja.getContentType());
			} else {
				existeCervejaSemFoto = true;
				context.setVariable("mockCerveja", "mockCerveja");		
			}
		}
		

		try {
			String corpoEmail = thymeleaf.process("mail/resumo-venda", context);

			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			helper.setFrom("luizmariodev@gmail.com");		
			helper.setTo(venda.getCliente().getEmail());
			helper.setSubject(String.format("Brewer nova venda realizada nº %d", venda.getCodigo()));
			helper.setText(corpoEmail, true);
			
			helper.addInline("logo", new ClassPathResource("static/images/logo-gray.png"));
			
			for (String cid: fotos.keySet()) {
				String[] fotoContentType = fotos.get(cid).split("\\|");
				String foto = fotoContentType[0];
				String contentType = fotoContentType[1];
				
				helper.addInline(cid, new ByteArrayResource(fotoStorage.buscarFotoThumbnail(foto)), contentType); 
			}
			
			if (existeCervejaSemFoto) {
				helper.addInline("mockCerveja", new ClassPathResource("static/images/cerveja-mock.png"));	
			}
		    
			mailSender.send(mimeMessage);
		} catch (MessagingException e) {
			logger.error("erro ao enviar e-mail", e);
		}
		
	}

}

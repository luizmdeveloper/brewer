package com.luizmario.brewer.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@PropertySource({ "classpath:env/mail-local.properties" })
public class MailConfig {
	
	@Autowired
	private Environment environment;
	
	@Bean
	public JavaMailSender mailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.sendgrid.net");
		mailSender.setPort(587);
		mailSender.setUsername("BrewerVendas");
		mailSender.setPassword("brewerVendas1");
		
		System.out.println(">>>>>>> username" + environment.getProperty("mail.username"));
		System.out.println(">>>>>>> password" + environment.getProperty("mail.password"));
		
		Properties props = new Properties();		
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", true);
		props.put("mail.smtp.starttls.enable", true);
		props.put("mail.debug", false);
		props.put("mail.smtp.timeoutconnection", 10000); //mileseconds
		
		mailSender.setJavaMailProperties(props);

		return mailSender;
	}

}
package com.eduhub.app.util;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service("mailService")
public class EmailSender {
	@Autowired
    private JavaMailSender sender;
	
	/* Sends the Email */
	public void sendEmail(String to, String subject, String text){
		MimeMessage message = sender.createMimeMessage();
		try{
		    MimeMessageHelper helper = new MimeMessageHelper(message);
	        helper.setTo(to);
	        helper.setText(text);
	        helper.setSubject(subject);
	        sender.send(message);
		}
		catch(Exception e){
			System.out.println("Email did not sent " + e.toString());
		}
	}
}

package com.inn.cafe.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailUtils {

	@Autowired
	private JavaMailSender javaMailSender;
	
	public void sendSimpleMessage(String to, String subject, String text, List<String> list) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("nandandubey4@gmail.com");
		message.setTo(to.trim());
		message.setSubject(subject);
		message.setText(text);
		if(list!=null && list.size()>0) {
			message.setCc(getCcArray(list));
		}
		javaMailSender.send(message);
	}
	
	private String[] getCcArray(List<String> ccList) {
		String[] cc = new String[ccList.size()];
		
		for(int i=0; i<ccList.size(); i++) {
			cc[i]=ccList.get(i);
		}
		return cc;
		
	}
}

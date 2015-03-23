package com.foal.yonder.thread;

import java.util.Date;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

public class SendEmailThread extends Thread {
	private JavaMailSenderImpl mailSender;
	private String[] email;
	private String subject;
	private String text;
	
	public SendEmailThread(JavaMailSenderImpl mailSender, String subject, String text, String... email) {
		this.mailSender = mailSender;
		this.email = email;
		this.subject = subject;
		this.text = text;
	}
	
	@Override
	public void run() {
		try {
			if (email == null || email.length == 0) {
				return;
			}
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, "GBK");
			messageHelper.setFrom(mailSender.getUsername());
			messageHelper.setSentDate(new Date());
			messageHelper.setSubject(subject);
			messageHelper.setTo(email);
			messageHelper.setText(text, true);
			mailSender.send(mimeMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

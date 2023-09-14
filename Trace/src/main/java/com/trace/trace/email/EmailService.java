package com.trace.trace.email;
import java.util.Random;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
@Service
public class EmailService {
	
	 private final JavaMailSender mailSender;
	 
	 private int authNumber;

	    public EmailService(JavaMailSender mailSender) {
	        this.mailSender = mailSender;
	    }
	    public void makeRandomNumber() {
	        Random random = new Random();
	        int checkNum = random.nextInt(888888) + 111111;
	        authNumber = checkNum;
	    }
	    public String sendVerificationCode(String toEmail) {
	    	makeRandomNumber();
	    	
	        SimpleMailMessage message = new SimpleMailMessage();
	        message.setTo(toEmail);
	        message.setSubject("Email Verification Code");
	        message.setText("Your verification code is: " + authNumber);
	        mailSender.send(message);
	        return Integer.toString(authNumber);
	    }
	   
}

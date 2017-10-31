package com.bridgeit.utilityservices;

import java.util.Properties;
import java.util.Random;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.bridgeit.model.Register;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

@Service
public class Email {

	@Autowired 
	JavaMailSender mailSender;
	
	SimpleMailMessage emailToSend =new SimpleMailMessage();
	
	
	public String generateotp() {
		String alpha = "abcdefghijklmnoprstuvwxyz";
		String numbers = "0123456789";
		String otp = alpha + numbers;
		char[] password = new char[8];
		for (int i = 0; i < 8; i++) {
			password[i] = otp.charAt(new Random().nextInt(otp.length()));
		}
		System.out.println(password);

		return new String(password);
	}

	public String sendEmail(Register reg) {
		String otp1 = generateotp();
		
		SimpleMailMessage emailToSend =new SimpleMailMessage();
		
		emailToSend.setTo(reg.getEmail());
		emailToSend.setSubject("Your OTP");
		emailToSend.setText("Test message" + " " + otp1);
		
		mailSender.send(emailToSend);	
		return otp1;
	}

	public void sendEmailsuccess(Register reg) {
	
		SimpleMailMessage emailToSend =new SimpleMailMessage();
		
		emailToSend.setTo(reg.getEmail());
		emailToSend.setSubject("Your Registration Success");
		emailToSend.setText("Test message" + "You have been successfully Registered " );
		mailSender.send(emailToSend);

	}

}

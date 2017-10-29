package com.bridgeit.services;

import java.util.Properties;
import java.util.Random;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

import com.bridgeit.model.Register;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

@Service
public class Email {

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
		// System.out.println(otp1 +"helllooo");
		Properties properties = new Properties();
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.socketFactory.port", "465");
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("bondadavenkatasai.aniketh2013@vit.ac.in", "gilchrist319");
			}
		});
		MimeMessage message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress("bondadavenkatasai.aniketh2013@vit.ac.in"));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(reg.getEmail()));
			message.setSubject("Your OTP");
			message.setText("Test message" + " " + otp1);
			Transport.send(message);

		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return otp1;
	}

	public void sendEmailsuccess(Register reg) {
	
		Properties properties = new Properties();
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.socketFactory.port", "465");
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("bondadavenkatasai.aniketh2013@vit.ac.in", "gilchrist319");
			}
		});
		MimeMessage message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress("bondadavenkatasai.aniketh2013@vit.ac.in"));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(reg.getEmail()));
			message.setSubject("Registered successfully");
			message.setText("You have been successfuly registered");
			Transport.send(message);

		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		// return otp1;
	}

}

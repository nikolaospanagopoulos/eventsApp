package com.events.app.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.events.app.services.EmailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
@Service
public class EmailServiceImpl implements EmailService {
	@Autowired
	private JavaMailSender mailSender;

	@Override
	public void sendTicketEmail(String to, byte[] qrCode) throws MessagingException {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		helper.setTo(to);
		helper.setSubject("Your Ticket QR Code");
		helper.setText("Attached is your ticket QR code.");

		// Add the QR code as an attachment
		helper.addAttachment("ticket.png", new ByteArrayResource(qrCode));

		mailSender.send(mimeMessage);

	}

}

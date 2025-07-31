package bsc_registration.webInterface.controller;

import bsc_registration.domain.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("api/mail")
@RequiredArgsConstructor
public class MailController {

	private final EmailService emailService;


	@PostMapping("/send")
	public void sendMail() throws MessagingException, IOException {

		emailService.sendInfoEmailToUser("");

	}

}

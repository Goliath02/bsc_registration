package bsc_registration.feature.mail.controller;

import bsc_registration.feature.mail.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/mail")
@RequiredArgsConstructor
public class MailController {

	private final EmailService emailService;


	@PostMapping("/send")
	public void sendMail() throws MessagingException, IOException {

		emailService.sendInfoEmailToUser("");

	}

	@PostMapping("/sendAll")
	public void sendMails(@RequestBody final List<String> emails) throws IOException {

		emailService.sendInfoEmailToUsers(emails);

	}

}

package bsc_registration.webInterface.controller;

import bsc_registration.domain.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/sendVMInvite")
    public void sendVMInviteToAllMembers() throws IOException {
        emailService.sendInviteToVM();
    }

    @PostMapping("/sendXmasInvite")
    public void sendXmasInvite() throws IOException {
        emailService.sendInviteToXmas();
    }

}

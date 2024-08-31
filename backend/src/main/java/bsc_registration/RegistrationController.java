package bsc_registration;


import bsc_registration.dto.FormData;
import jakarta.mail.MessagingException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

@Controller
public class RegistrationController {

	private final RegistrationModule registrationModule;

	public RegistrationController(RegistrationModule registrationModule) {
		this.registrationModule = registrationModule;
	}

	@GetMapping("/")
	public String getFrontend() {
		return "index";
	}


	@PostMapping(value = "/registrate", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void validateRegistration(@RequestBody final FormData formData) {

		try {
			registrationModule.sendEmail(formData);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}


	}
}

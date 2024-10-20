package bsc_registration;


import bsc_registration.dto.FormData;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static java.lang.String.format;

@Controller
public class RegistrationController {

	private final RegistrationModule registrationModule;
	Logger logger = LoggerFactory.getLogger(RegistrationController.class);

	public RegistrationController(RegistrationModule registrationModule) {
		this.registrationModule = registrationModule;
	}

	@GetMapping("/")
	public String getFrontend() {
		return "index";
	}


	@PostMapping(value = "/registrate", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity validateRegistration(@RequestPart FormData formData,
	                                           @RequestPart List<MultipartFile> studentIdentificationFiles) {

		if (formData == null) {
			return ResponseEntity.status(400).build();
		}

		try {
			registrationModule.sendEmail(formData, studentIdentificationFiles);
		} catch (MessagingException | IOException e) {
			logger.error(format("Registration failed with Excpetion: %s", e.getMessage()));
			return ResponseEntity.status(500).build();
		}

		return ResponseEntity.status(200).build();
	}
}

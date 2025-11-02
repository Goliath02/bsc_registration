package bsc_registration.webInterface.controller;

import bsc_registration.domain.service.RegistrationService;
import bsc_registration.webInterface.dto.Errors;
import bsc_registration.webInterface.dto.FormData;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

@Controller
@CrossOrigin
@Slf4j
public class RegistrationController {

	public static final long EIGHT_MB = 8000000L;
	private final RegistrationService registrationService;

	public RegistrationController(final RegistrationService registrationModule) {
		this.registrationService = registrationModule;
	}

	@GetMapping("/courses")
	@ResponseBody()
	public ResponseEntity<List<String>> getConfig() {
		return ResponseEntity.ok(registrationService.getCourses());
	}

	@GetMapping("/priceList")
	@ResponseBody()
	public ResponseEntity<List<String>> getPriceList() {

		return ResponseEntity.ok(registrationService.getPriceList());
	}

    @PostMapping(value = "/registrate", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity validateRegistration(
            @RequestPart final FormData formData,
            @RequestPart(required = false) final List<MultipartFile> studentIdentificationFiles
    ) {

        if (formData == null) {
            return ResponseEntity.status(400).body("Form-data is empty");
        }

        log.info("Received registration data: {}", formData);

        if (studentIdentificationFiles != null) {

            for (MultipartFile file : studentIdentificationFiles) {
                //Is bigger than 8MB
                if (file.getSize() > EIGHT_MB) {
                    return ResponseEntity.status(413).body(Errors.IMAGE_TOO_LARGE);
                }
            }
        }

        registrationService.saveRegistration(formData);

        try {
            CompletableFuture<Void> f1 = registrationService.sendEmailToRegisteredUser(formData);
            CompletableFuture<Void> f2 = registrationService.sendEmailToRegistration(formData, studentIdentificationFiles);
            CompletableFuture<Void> f3 = registrationService.sendEmailToCourseOwner(formData);

            CompletableFuture.allOf(f1, f2, f3).join();

            return ResponseEntity.ok().build();

        } catch (CompletionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof MailSendException) {
                log.error("Mail send failed: {}", cause.getMessage());
                return ResponseEntity.status(400).body(Errors.EMAIL_NOT_FOUND);
            } else if (cause instanceof MessagingException || cause instanceof IOException) {
                log.error("Registration failed: {}", cause.getMessage());
                return ResponseEntity.status(500).body(Errors.INTERNAL_ERROR);
            } else {
                log.error("Unexpected error: {}", cause.getMessage());
                return ResponseEntity.status(500).body(Errors.INTERNAL_ERROR);
            }
        }
    }

}

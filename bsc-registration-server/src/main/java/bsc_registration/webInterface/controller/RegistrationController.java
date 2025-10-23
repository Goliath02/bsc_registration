package bsc_registration.webInterface.controller;

import bsc_registration.domain.service.PdfService;
import bsc_registration.domain.service.RegistrationService;
import bsc_registration.webInterface.dto.Errors;
import bsc_registration.webInterface.dto.FinancialData;
import bsc_registration.webInterface.dto.FormData;
import bsc_registration.webInterface.dto.MainData;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Controller
@CrossOrigin
@RequiredArgsConstructor
@Slf4j
public class RegistrationController {

	public static final long EIGHT_MB = 8000000L;
	private final RegistrationService registrationService;
    private final PdfService pdfService;


    @GetMapping("/pdfTest")
    public ResponseEntity<byte[]> testPdf() {

        final MainData mainData = new MainData(
                "MITGLIED",
                "Swim",
                "Anna",
                "Schmidt",
                LocalDate.of(1992, 5, 15),
                "Weiblich",
                "anna.schmidt@example.com",
                "0176 12345678",
                "Blumenstraße",
                "76133",
                "Karlsruhe",
                LocalDate.of(2024, 2, 1),
                List.of()
        );

        final FinancialData financialData = new FinancialData(
                "DE89 3704 0044 0532 0130 00",
                "Anna",
                "Schmidt"
        );

        final FormData formData = new FormData(mainData, financialData, true, true, true);

        try {
            byte[] tests = pdfService.generateRegistrationPdf(formData);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"anmeldung.pdf\"")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(tests);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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
	public ResponseEntity validateRegistration(@RequestPart final FormData formData,
	                                           @RequestPart(required = false) final List<MultipartFile> studentIdentificationFiles) {

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
			registrationService.sendEmailToRegisteredUser(formData);
			registrationService.sendEmailToRegistration(formData, studentIdentificationFiles);
			registrationService.sendEmailToCourseOwner(formData);
		} catch (MessagingException | IOException e) {
			log.error("Registration failed with Excpetion: {}", e.getMessage());
			return ResponseEntity.status(500).body(Errors.INTERNAL_ERROR);
		} catch (MailSendException e) {
			log.error("Registration failed with Excpetion: {}", e.getMessage());

			return ResponseEntity.status(400).body(Errors.EMAIL_NOT_FOUND);
		}

        return ResponseEntity.ok().build();
	}

	@PostMapping(value = "/registrateNsw", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity validateNswRegistration(@RequestPart final FormData formData) {

		if (formData == null) {
			return ResponseEntity.status(400).body("Form-data is empty");
		}

		log.info("Received new nsw registration data: {}", formData);

		registrationService.setOnRegistrationNswList(formData);

		return ResponseEntity.ok().build();
	}

}

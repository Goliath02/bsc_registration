package bsc_registration;

import bsc_registration.dto.FormData;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static java.lang.String.format;

@Controller
public class RegistrationController {

    public static final long EIGHT_MB = 8000000L;
    private final RegistrationModule registrationModule;
    final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    public RegistrationController(final RegistrationModule registrationModule) {
        this.registrationModule = registrationModule;
    }

    @GetMapping("/")
    public String getFrontend() {
        return "index";
    }

    @GetMapping("/courses")
    @ResponseBody()
    public ResponseEntity<List<String>> getConfig() {
        return ResponseEntity.ok(registrationModule.getCourses());
    }

    @PostMapping(value = "/registrate", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity validateRegistration(@RequestPart final FormData formData,
                                               @RequestPart(required = false) final List<MultipartFile> studentIdentificationFiles) {

        if (formData == null) {
            return ResponseEntity.status(400).body("Form-data is empty");
        }

        if (studentIdentificationFiles != null) {

            for (MultipartFile file : studentIdentificationFiles) {
                //Is bigger than 8MB
                if (file.getSize() > EIGHT_MB) {
                    return ResponseEntity.status(413).body(Errors.IMAGE_TOO_LARGE);
                }
            }
        }

        try {
            registrationModule.sendEmailToRegistratedUser(formData);
            registrationModule.sendEmailToRegistration(formData, studentIdentificationFiles);
//            registrationModule.sendEmailToCourseOwner(formData);
        } catch (MessagingException | IOException e) {
            logger.error(format("Registration failed with Excpetion: %s", e.getMessage()));
            return ResponseEntity.status(500).body(Errors.INTERNAL_ERROR);
        } catch (MailSendException e) {
            logger.error(format("Registration failed with Excpetion: %s", e.getMessage()));

            return ResponseEntity.status(400).body(Errors.EMAIL_NOT_FOUND);
        }

        return ResponseEntity.ok().build();
    }
}

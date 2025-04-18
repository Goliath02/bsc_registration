package bsc_registration;

import bsc_registration.Configuration.ConfigLoader;
import bsc_registration.Mailsender.EmailService;
import bsc_registration.Utils.CsvUtil;
import bsc_registration.dto.FormData;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RegistrationModule {

    final private EmailService emailService;
    final private ConfigLoader configLoader;

    final private CsvUtil csvUtil;

    final Logger logger = LoggerFactory.getLogger(RegistrationModule.class);

    public void sendEmailToRegistration(final FormData formData, final List<MultipartFile> files) throws MessagingException, IOException {

        final var config = configLoader.loadConfig();

        final var csvFromFormData = csvUtil.createCsvFromFormData(formData);
	    logger.info("CSV was created successfully: {}", csvFromFormData);

        final var registrationReceiver = config.registrationReceiver();

        emailService.sendMailToRegistration(registrationReceiver, csvFromFormData, files);
        logger.info("Email was sent successfully to: {}", registrationReceiver);
    }

    public void sendEmailToCourseOwner(final FormData formData) throws MessagingException {

        final var config = configLoader.loadConfig();
        final var courseOwner = config.courses().get(formData.mainData().reason());

        emailService.sendEmailToCourseOwner(courseOwner, formData);
    }

    public void sendEmailToRegistratedUser(FormData formData) throws MessagingException, MailSendException {
        emailService.sendEmailToUser(formData.mainData().email(), formData);
        logger.info("Email was sent successfully to: {}", formData.mainData().email());
    }

    public List<String> getCourses() {
        return configLoader.loadCourses();
    }

}

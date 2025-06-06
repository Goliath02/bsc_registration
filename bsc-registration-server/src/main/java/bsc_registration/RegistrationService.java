package bsc_registration;

import bsc_registration.Configuration.BscCourseConfig;
import bsc_registration.Configuration.ConfigLoader;
import bsc_registration.Mailsender.EmailService;
import bsc_registration.utils.CsvUtil;
import bsc_registration.utils.DevUtil;
import bsc_registration.dto.FormData;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
public class RegistrationService {

    final private EmailService emailService;
    final private DevUtil devUtil;
    final private ConfigLoader configLoader;

    final private CsvUtil csvUtil;

    public List<String> getPriceList() {

        BscCourseConfig bscCourseConfig = configLoader.loadConfig();

        return bscCourseConfig.priceList();
    }

    public void sendEmailToRegistration(final FormData formData, final List<MultipartFile> files) throws MessagingException, IOException {

        final BscCourseConfig config = configLoader.loadConfig();

        final String csvFromFormData = csvUtil.createCsvFromFormData(formData);
	    log.info("CSV was created successfully: {}", csvFromFormData);

        final List<String> registrationReceiver = devUtil.getEmailFromConfig(config.registrationReceiver());

        emailService.sendMailToRegistration(registrationReceiver, csvFromFormData, files);
        log.info("Email was sent successfully to: {}", registrationReceiver);
    }

    public void sendEmailToCourseOwner(final FormData formData) throws MessagingException {

        final BscCourseConfig config = configLoader.loadConfig();
        final List<String> courseOwner = devUtil.getEmailFromConfig(config.courses().get(formData.mainData().reason()));

        emailService.sendEmailToCourseOwner(courseOwner, formData);
    }

    public void sendEmailToRegisteredUser(final FormData formData) throws MessagingException, MailSendException {
        emailService.sendEmailToUser(formData.mainData().email(), formData);
        log.info("Email was sent successfully to: {}", formData.mainData().email());
    }

    public List<String> getCourses() {
        return configLoader.loadCourses();
    }

}

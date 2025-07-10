package bsc_registration.Service;

import bsc_registration.Configuration.BscCourseConfig;
import bsc_registration.Configuration.ConfigLoader;
import bsc_registration.Mailsender.EmailService;
import bsc_registration.Repository.NswRegistrationRepository;
import bsc_registration.dto.ExtraPerson;
import bsc_registration.dto.FormData;
import bsc_registration.dto.MainData;
import bsc_registration.dto.NswRegistration;
import bsc_registration.utils.CsvUtil;
import bsc_registration.utils.DevUtil;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
public class RegistrationService {

    final private EmailService emailService;
    final private ConfigLoader configLoader;

    final private NswRegistrationRepository nswRepository;

    final private DevUtil devUtil;
    final private CsvUtil csvUtil;

    public List<String> getPriceList() {

        BscCourseConfig bscCourseConfig = configLoader.loadConfig();

        return bscCourseConfig.getPriceList();
    }

    public void sendEmailToRegistration(final FormData formData, final List<MultipartFile> files) throws MessagingException, IOException {

        final BscCourseConfig config = configLoader.loadConfig();

        final String csvFromFormData = csvUtil.createCsvFromFormData(formData);
        log.info("CSV was created successfully: {}", csvFromFormData);

        final List<String> registrationReceiver = devUtil.getEmailFromConfig(config.getRegistrationReceiver());

        emailService.sendMailToRegistration(registrationReceiver, csvFromFormData, files);
        log.info("Email was sent successfully to: {}", registrationReceiver);
    }

    public void sendEmailToCourseOwner(final FormData formData) throws MessagingException {

        final BscCourseConfig config = configLoader.loadConfig();
        final List<String> courseOwner = devUtil.getEmailFromConfig(config.getCourses().get(formData.mainData().reason()));

        emailService.sendEmailToCourseOwner(courseOwner, formData);
    }

    public void sendEmailToRegisteredUser(final FormData formData) throws MessagingException, MailSendException {
        emailService.sendEmailToUser(formData.mainData().email(), formData);
        log.info("Email was sent successfully to: {}", formData.mainData().email());
    }

    public void setOnRegistrationNswList(final FormData formData) {

        final MainData mainData = formData.mainData();
        final List<ExtraPerson> extraPeople = formData.morePersons();

        final List<NswRegistration> registrations = new ArrayList<>();

        final var mainRegistration = new NswRegistration(
                null,
                mainData.name(),
                mainData.surename(),
                mainData.email(),
                mainData.phone(),
                LocalDate.now()
        );

        registrations.add(mainRegistration);

        for (ExtraPerson extraPerson : extraPeople) {
            final var extraRegistration = new NswRegistration(
                    null,
                    extraPerson.name(),
                    extraPerson.surename(),
                    mainData.email(),
                    mainData.phone(),
                    LocalDate.now()
            );
            registrations.add(extraRegistration);
        }
        nswRepository.saveAll(registrations);
    }

    public List<String> getCourses() {
        return configLoader.loadCourses();
    }

}

package bsc_registration.domain.service;

import bsc_registration.domain.entities.BscMember;
import bsc_registration.domain.entities.NswRegistration;
import bsc_registration.domain.utils.CsvUtil;
import bsc_registration.domain.utils.DevUtil;
import bsc_registration.infrastructure.config.BscCourseConfig;
import bsc_registration.infrastructure.config.ConfigLoader;
import bsc_registration.infrastructure.repository.BscMemberRepository;
import bsc_registration.infrastructure.repository.NswRegistrationRepository;
import bsc_registration.webInterface.dto.ExtraPerson;
import bsc_registration.webInterface.dto.FinancialData;
import bsc_registration.webInterface.dto.FormData;
import bsc_registration.webInterface.dto.MainData;
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
@RequiredArgsConstructor
@Slf4j
public class RegistrationService {

	final private EmailService emailService;
	final private ConfigLoader configLoader;

	final private NswRegistrationRepository nswRepository;
	final private BscMemberRepository memberRepository;

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
    final FinancialData financialData = formData.financial();
    final List<ExtraPerson> extraPeople = formData.mainData().morePersons();

    final List<NswRegistration> registrations = new ArrayList<>();

    final var mainRegistration = new NswRegistration(
      null,
      mainData.name(),
      mainData.surename(),
      mainData.email(),
      mainData.phone(),
      LocalDate.now(),
      mainData.type(),
      mainData.reason(),
      mainData.birthday(),
      mainData.gender(),
      mainData.street(),
      mainData.plz(),
      mainData.place(),
      mainData.entryDate(),
      financialData.iban(),
      financialData.nameOfBankOwner(),
      financialData.sureNameBankOwner()
    );

    registrations.add(mainRegistration);

    for (ExtraPerson extraPerson : extraPeople) {
      final var extraRegistration = new NswRegistration(
        null,
        extraPerson.extraName(),
        extraPerson.extraSureName(),
        mainData.email(),
        mainData.phone(),
        LocalDate.now(),
        mainData.type(),
        mainData.reason(),
        mainData.birthday(),
        extraPerson.extraGender(),
        mainData.street(),
        mainData.plz(),
        mainData.place(),
        mainData.entryDate(),
        financialData.iban(),
        financialData.nameOfBankOwner(),
        financialData.sureNameBankOwner()

      );
      registrations.add(extraRegistration);
    }
    nswRepository.saveAll(registrations);
  }

	public List<String> getCourses() {
		return configLoader.loadCourses();
	}

  public void saveRegistration(final FormData formData) {

    final MainData mainData = formData.mainData();
    final FinancialData financialData = formData.financial();
    final List<ExtraPerson> extraPeople = formData.mainData().morePersons();

    final List<BscMember> registrations = new ArrayList<>();

    final var mainRegistration = new BscMember(
      null,
      mainData.name(),
      mainData.surename(),
      mainData.email(),
      mainData.phone(),
      LocalDate.now(),
      mainData.type(),
      mainData.reason(),
      mainData.birthday(),
      mainData.gender(),
      mainData.street(),
      mainData.plz(),
      mainData.place(),
      mainData.entryDate(),
      financialData.iban(),
      financialData.nameOfBankOwner(),
      financialData.sureNameBankOwner()
    );

    registrations.add(mainRegistration);

    for (ExtraPerson extraPerson : extraPeople) {
      final var extraRegistration = new BscMember(
        null,
        extraPerson.extraName(),
        extraPerson.extraSureName(),
        mainData.email(),
        mainData.phone(),
        LocalDate.now(),
        mainData.type(),
        mainData.reason(),
        extraPerson.extraBirthday(),
        extraPerson.extraGender(),
        mainData.street(),
        mainData.plz(),
        mainData.place(),
        mainData.entryDate(),
        financialData.iban(),
        financialData.nameOfBankOwner(),
        financialData.sureNameBankOwner()

      );
      registrations.add(extraRegistration);
    }
    memberRepository.saveAll(registrations);
  }
}

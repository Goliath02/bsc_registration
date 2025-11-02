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
import jakarta.mail.util.ByteArrayDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import static bsc_registration.domain.utils.FormUtil.calculateAge;
import static bsc_registration.domain.utils.FormUtil.formatDate;
import static java.lang.String.format;


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

    public CompletableFuture<Void> sendEmailToRegistration(FormData formData, List<MultipartFile> files) {
        try {
            BscCourseConfig config = configLoader.loadConfig();
            String csv = csvUtil.createCsvFromFormData(formData);
            List<String> receiver = devUtil.getEmailFromConfig(config.getRegistrationReceiver());

            final var inputStream = new ByteArrayDataSource(csv.getBytes(StandardCharsets.UTF_8), "application/octet-stream");

            EmailService.BscNameMailInfo info = new EmailService.BscNameMailInfo(
                    String.join(",", receiver),
                    "Neue Anmeldung vom für den 1.BSC!",
                    "MailMessageTemplate",
                    "Neue Anmeldedaten im Anhang",
                    "NeueMitglieder.csv",
                    inputStream,
                    files
            );

            return emailService.sendMail(info);
        } catch (Exception e) {
            CompletableFuture<Void> failed = new CompletableFuture<>();
            failed.completeExceptionally(e);
            return failed;
        }
    }

    public CompletableFuture<Void> sendEmailToCourseOwner(FormData formData) {
        try {
            BscCourseConfig config = configLoader.loadConfig();
            List<String> owner = devUtil.getEmailFromConfig(config.getCourses().get(formData.mainData().reason()));

            EmailService.BscNameMailInfo info = new EmailService.BscNameMailInfo(
                    String.join(",", owner),
                    "Neue Anmeldung für deinen Kurs",
                    "MailMessageTemplate",
                    buildCourseOwnerHtml(formData),
                    null,
                    null
            );


            return emailService.sendMail(info);
        } catch (Exception e) {
            CompletableFuture<Void> failed = new CompletableFuture<>();
            failed.completeExceptionally(e);
            return failed;
        }
    }

    public CompletableFuture<Void> sendEmailToRegisteredUser(FormData formData) {
        try {
            EmailService.BscNameMailInfo info = new EmailService.BscNameMailInfo(
                    formData.mainData().email(),
                    "Anmeldebestätigung",
                    "MailMessageTemplate",
                    buildUserMailHtml(formData),
                    null,
                    null
            );
            return emailService.sendMail(info);
        } catch (Exception e) {
            CompletableFuture<Void> failed = new CompletableFuture<>();
            failed.completeExceptionally(e);
            return failed;
        }
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

    private String buildUserMailHtml(final FormData formData) {

        StringBuilder morePersonsInfoText = new StringBuilder();

        if (! formData.mainData().morePersons().isEmpty()) {
            morePersonsInfoText.append("<p>Weitere Personen anghaben:</p><br>");

            for (ExtraPerson person : formData.mainData().morePersons()) {

                final String personInfo = format(
                        """
                                <p>Geschlecht: %s</p>
                                <p>Vorname: %s</p>
                                <p>Nachname: %s</p>
                                <p>Geburtsdatum: %s</p>
                                <br>
                                """, person.extraGender(), person.extraName(), person.extraSureName(), person.extraBirthday()
                );

                morePersonsInfoText.append(personInfo).append("\n");
            }
        }

        final MainData mainData = formData.mainData();
        final FinancialData fData = formData.financial();

        final String infoText = format(
                """
                        <p>Wir haben folgende Informationen über Sie erhalten:</p>
                        <p>Art der Registrierung: %s</p>
                        <p>Kategorie: %s</p>
                        <p>Geschlecht: %s</p>
                        <p>Vorname: %s</p>
                        <p>Nachname: %s</p>
                        <p>Geburtsdatum: %s</p>
                        <p>Email: %s</p>
                        <p>Telefon/Mobil: %s</p>
                        <p>Straße: %s</p>
                        <p>Postleitzahl: %s</p>
                        <p>Ort: %s</p>
                        <br>
                        <p>IBAN: %s</p>
                        <p>Bankkonto Besitzer Vorname: %s</p>
                        <p>Bankkonto Besitzer Nachname: %s</p>
                        <br>
                        %s
                        
                        <p>Bitte gedulden Sie sich bitte noch etwas und warten Sie auf eine Antwort von einem unserer Spaten-Leitung.</p>
                        <p>Wir melden uns persönlich bei Ihnen und teilen Sie in eine passende Gruppe ein, dass Ihrem Niveu entspricht.</p>
                        
                        """,
                mainData.type(), mainData.reason(), mainData.gender(), mainData.name(),
                mainData.surename(), mainData.birthday(), mainData.email(), mainData.phone(),
                mainData.street(), mainData.plz(), mainData.place(),

                fData.iban(), fData.nameOfBankOwner(), fData.sureNameBankOwner(),

                morePersonsInfoText
        );

        return format(
                """
                                  <div class="bodyWrapper">
                                  	<h1>Information zu Ihrer Registrierung</h1>
                                  	%s
                                  	<p>Weitere Informationen finden Sie unter der <a href="https://erster-bsc-pforzheim.de/" style="color: cornflowerblue">Website</a>.</p>
                                  	<p>Wenn Sie weitere Fragen haben kontaktieren Sie gerne <a href="mailto: vorstand@erster-bsc-pforzheim.de" style="color: cornflowerblue">vorstand@erster-bsc-pforzheim.de</a>.</p>
                        """, infoText
        );
    }

    private String buildCourseOwnerHtml(final FormData formData) {

        final var mainData = formData.mainData();

        return format(
                """
                                <h1>Neue Registration</h1>
                                <div>
                                 Name: %s %s
                                </div>
                                <div>
                                 Geschlecht: %s
                                </div>
                                <div>
                                 Geburtstag: %s (%d)
                                </div>
                                <div>
                                 Email: %s
                                </div>
                                <div>
                                 Telefon: %s
                                </div>
                        """,
                mainData.name(),
                mainData.surename(),
                mainData.gender(),
                formatDate(mainData.birthday()),
                calculateAge(mainData.birthday()),
                mainData.email(),
                mainData.phone()
        );
    }

}

package bsc_registration.domain.service;


import bsc_registration.domain.entities.BscMember;
import bsc_registration.infrastructure.config.MailSenderConfig;
import bsc_registration.infrastructure.repository.BscMemberRepository;
import bsc_registration.webInterface.dto.*;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.CharEncoding;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static bsc_registration.domain.utils.FormUtil.calculateAge;
import static bsc_registration.domain.utils.FormUtil.formatDate;
import static java.lang.String.format;


@Service
@RequiredArgsConstructor()
@Slf4j
public class EmailService {

	public static final String BASIC_MAIL_TITEL = "1.BSC Pforzheim Info";
	private final MailSenderConfig mailSenderConfig;

	private final BscMemberRepository bscMemberRepository;

	@Value("${mail.from}")
	private String sendFrom;

	public void sendMailToRegistration(final List<String> targetEmails, final String csv, final List<MultipartFile> files) throws MessagingException {

		final var mailSender = mailSenderConfig.getJavaMailSender();

		final var message = mailSender.createMimeMessage();

		message.setFrom(new InternetAddress(sendFrom));

		message.setRecipients(Message.RecipientType.TO, String.join(",", targetEmails));

		MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, CharEncoding.UTF_8);
		messageHelper.setFrom(sendFrom);
		messageHelper.setTo(targetEmails.toArray(new String[0]));
		messageHelper.setSubject("Neue Anmeldung vom für den 1.BSC!");
		messageHelper.setText("Neue Anmeldedaten im Anhang");

		if (files != null) {
			for (MultipartFile file : files) {
				messageHelper.addAttachment(file.getOriginalFilename(), file);
			}
		}

		ByteArrayDataSource inputStream = new ByteArrayDataSource(csv.getBytes(StandardCharsets.UTF_8), "application/octet-stream");

		messageHelper.addAttachment("NeueMitglieder.csv", inputStream);

		mailSender.send(message);
	}

	//Is keept for future purposes
	public void sendEmailToCourseOwner(final List<String> targetEmails, final FormData formData) throws MessagingException {

		final var mailSender = mailSenderConfig.getJavaMailSender();

		final var message = mailSender.createMimeMessage();

		message.setFrom(new InternetAddress(sendFrom));

		message.setRecipients(Message.RecipientType.TO, String.join(",", targetEmails));

		MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, CharEncoding.UTF_8);
		messageHelper.setFrom(sendFrom);
		messageHelper.setTo(targetEmails.toArray(new String[0]));
		messageHelper.setSubject("1.BSC Pforzheim Info");

		messageHelper.setText(buildCourseOwnerHtml(formData), true);

		mailSender.send(message);

	}


	public void sendEmailToUser(final String email, final FormData formData) throws MessagingException, MailSendException {

		final var mailSender = mailSenderConfig.getJavaMailSender();

		final var message = mailSender.createMimeMessage();

		message.setFrom(new InternetAddress(sendFrom));
		message.setRecipients(MimeMessage.RecipientType.TO, email);

		final var messageHelper = new MimeMessageHelper(message, true, CharEncoding.UTF_8);
		messageHelper.setFrom(sendFrom);
		messageHelper.setTo(InternetAddress.parse(email));
		messageHelper.setSubject("1.BSC Pforzheim Info");

		messageHelper.setText(buildUserMailHtml(formData), true);

		mailSender.send(message);
	}

	public String loadHtmlTemplate(String filename) throws IOException {
		ClassPathResource resource = new ClassPathResource("templates/email/" + filename);
		byte[] data = resource.getInputStream().readAllBytes();
		return new String(data, StandardCharsets.UTF_8);
	}

	public void sendInfoEmailToUser(final String email) throws MessagingException,
			MailSendException, IOException {

		final String template = loadHtmlTemplate("MailTemplate.html");

		final var mailSender = mailSenderConfig.getJavaMailSender();

		final var message = mailSender.createMimeMessage();

		message.setFrom(new InternetAddress(sendFrom));
		message.setRecipients(MimeMessage.RecipientType.TO, email);

		final var messageHelper = new MimeMessageHelper(message, true, CharEncoding.UTF_8);
		messageHelper.setFrom(sendFrom);
		messageHelper.setTo(InternetAddress.parse(email));
		messageHelper.setSubject("1.BSC Pforzheim Info");

		messageHelper.setText(template, true);

		mailSender.send(message);

	}

	public void sendInfoEmailToUsers(final List<String> emails) throws
			IOException {

		final ArrayList<BscNameMail> invalidMails = new ArrayList<>();

		final String template = loadHtmlTemplate("MailTemplate.html");

		List<BscNameMail> receiversMails;

		if (emails.isEmpty()) {
			receiversMails =
					bscMemberRepository.findAll().stream().filter(bscMember -> bscMember.getEmail() != null).map(member -> new BscNameMail(member.getName(),
							member.getEmail())).toList();
		} else {
			receiversMails = emails.stream().map(e -> new BscNameMail("anonymous", e )).toList();
		}
		final var mailSender = mailSenderConfig.getJavaMailSender();

		receiversMails.forEach(member -> {

			try {
				final var message = mailSender.createMimeMessage();

				message.setFrom(new InternetAddress(sendFrom));
				message.setRecipients(MimeMessage.RecipientType.TO, member.getEmail());

				final var messageHelper = new MimeMessageHelper(message, true, CharEncoding.UTF_8);
				messageHelper.setFrom(sendFrom);
				messageHelper.setTo(InternetAddress.parse(member.getEmail()));
				messageHelper.setSubject(BASIC_MAIL_TITEL);

				messageHelper.setText(template, true);

				mailSender.send(message);
			} catch (MailSendException e) {
				invalidMails.add(new BscNameMail(member.getName(), member.getEmail() ));
				log.error("Error sending email to user: {}", member, e);
			} catch (MessagingException e) {
				log.error("Could nor authenticate with mail server: {}", e.getMessage());
			} catch (MailAuthenticationException e) {
				log.error("Error occurred: {}", e.getMessage());
			}
		});

		log.info("Sending email to {} users was successful from {}.", (receiversMails.size() - invalidMails.size()), invalidMails.size());

		if (!invalidMails.isEmpty()) {
			log.warn("The following emails could not be sent: {}", invalidMails);
		}
	}

	private String buildUserMailHtml(final FormData formData) {

		StringBuilder morePersonsInfoText = new StringBuilder();

		if (!formData.morePersons().isEmpty()) {
			morePersonsInfoText.append("<p>Weitere Personen anghaben:</p><br>");

			for (ExtraPerson person : formData.morePersons()) {

				final String personInfo = format("""
						<p>Geschlecht: %s</p>
						<p>Vorname: %s</p>
						<p>Nachname: %s</p>
						<p>Geburtsdatum: %s</p>
						<br>
						""", person.gender(), person.name(), person.surename(), person.birthday());

				morePersonsInfoText.append(personInfo).append("\n");
			}
		}

		final MainData mainData = formData.mainData();
		final FinancialData fData = formData.financial();

		final String infoText = format("""
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

		return format("""
				<!DOCTYPE html>
				          <head>
				          	<meta charset="UTF-8">
				          	<title>Info</title>
				          </head>
				
				          <div class="headerWrapper">
				          	<img src="https://erster-bsc-pforzheim.de/wp-content/uploads/2023/03/BcsLogoCutout.png" style="height: 5em">
				          </div>
				
				          <div class="bodyWrapper">
				          	<h1>Information zu Ihrer Registrierung</h1>
				          	%s
				          	<p>Weitere Informationen finden Sie unter der <a href="https://erster-bsc-pforzheim.de/" style="color: cornflowerblue">Website</a>.</p>
				          	<p>Wenn Sie weitere Fragen haben kontaktieren Sie gerne <a href="mailto: vorstand@erster-bsc-pforzheim.de" style="color: cornflowerblue">vorstand@erster-bsc-pforzheim.de</a>.</p>
				""", infoText);
	}

	private String buildCourseOwnerHtml(final FormData formData) {

		final var mainData = formData.mainData();

		return format("""
						<!DOCTYPE html>
						<html>
						    <head>
						        <meta charset="UTF-8">
						        <title>Registration</title>
						    </head>
						    <body>
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
						    </body>
						</html>
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

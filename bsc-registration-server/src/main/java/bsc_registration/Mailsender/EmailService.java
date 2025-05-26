package bsc_registration.Mailsender;


import bsc_registration.dto.ExtraPerson;
import bsc_registration.dto.FinancialData;
import bsc_registration.dto.FormData;
import bsc_registration.dto.MainData;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.CharEncoding;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static bsc_registration.utils.FormUtil.calculateAge;
import static bsc_registration.utils.FormUtil.formatDate;
import static java.lang.String.format;


@Service
@RequiredArgsConstructor()
public class EmailService {

	private final MailSenderConfig mailSenderConfig;

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

	private String buildUserMailHtml(final FormData formData) {

		StringBuilder morePersonsInfoText = new StringBuilder();
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
						<p>Weitere Personen anghaben:</p>
						<br>
						%s
						""",
				mainData.type(), mainData.section(), mainData.gender(), mainData.name(),
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
				
				          	<style>
				                 %s
				          	</style>
				          </head>
				
				          <div class="headerWrapper">
				          	<img src="https://erster-bsc-pforzheim.de/wp-content/uploads/2023/03/BcsLogoCutout.png" style="height: 5em">
				          </div>
				
				          <div class="bodyWrapper">
				          	<h1>Information zu Ihrer Registrierung</h1>
				          	%s
				          	<p>Mit dieser Email erhalten Sie auch alle nötigen Informationen für Ihr erstes Training</p>
				          	<a href="https://erster-bsc-pforzheim.de/kursplan-schwimmen">Aktueller Trainingplan fürs Schwimmen</a>
				          	<p>oder</p>
				          	<a href="https://erster-bsc-pforzheim.de/wasserball">Wasserball</a>
				
				          	<p>Weitere Informationen finden Sie unter der <a href="https://erster-bsc-pforzheim.de/" style="color: cornflowerblue">Website</a>.</p>
				          	<p>Wenn Sie weitere Fragen haben kontaktieren Sie gerne <a href="mailto: vorstand@erster-bsc-pforzheim.de" style="color: cornflowerblue">vorstand@erster-bsc-pforzheim.de</a>.</p>
				""", CSS, infoText);
	}

	private String getTrainingPlanLink() {

		return "";
	}

	//Is keept for future purposes
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

	final String CSS = """
				td{
					padding: 0.5em;
			        border: solid rgb(34 34 34);
			    }
			
				table{
					width: 100%;
				}
			
				tbody{
					table-layout: auto;
				}
			
				p{
			        font-size: 1.125rem; /* 18px */
			        line-height: 1.75rem; /* 28px */
				}
			
				h1{
			        font-size: 1.875rem; /* 30px */
			        line-height: 2.25rem; /* 36px */
			        padding-top: 1em;
			        padding-bottom: 1em;
				}
			
				.bodyWrapper{
					display: flex;
					flex-direction: column;
					width: 100%;
					font-weight: bold;
			        padding-left: 1em;
			        padding-right: 1em;
					gap: 1em;
				}
			
			
				.headerWrapper{
					color: white;
					display: flex;
					justify-content: center;
					padding-top: 1em;
					padding-bottom: 1em;
					background-color: black;
				}
			
					.tableWrapper {
			         background-color: rgb(64 64 64);
			         border-radius: 0.5rem;
			         overflow-x: auto;
			     }
			
			""";
}

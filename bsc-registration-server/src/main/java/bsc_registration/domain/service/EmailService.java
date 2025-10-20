package bsc_registration.domain.service;


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
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static bsc_registration.domain.utils.FormUtil.calculateAge;
import static bsc_registration.domain.utils.FormUtil.formatDate;
import static java.lang.String.format;


@Service
@RequiredArgsConstructor()
@Slf4j
@EnableAsync
public class EmailService {

	public static final String BASIC_MAIL_TITEL = "1.BSC Pforzheim Info";
    public static final String INVITE_TEMPLATE = "InviteTemplate";
    private final MailSenderConfig mailSenderConfig;

	private final BscMemberRepository bscMemberRepository;

    private final ExecutorService executor = Executors.newFixedThreadPool(5);

    public void sendTrainerInviteMail(final String email, final String signUpKey) throws MessagingException, MailSendException, IOException {

        final var mailSender = mailSenderConfig.getJavaMailSender();

        final var message = mailSender.createMimeMessage();

        message.setFrom(new InternetAddress(sendFrom));
        message.setRecipients(MimeMessage.RecipientType.TO, email);

        final var messageHelper = new MimeMessageHelper(message, true, CharEncoding.UTF_8);
        messageHelper.setFrom(sendFrom);
        messageHelper.setTo(InternetAddress.parse(email));
        messageHelper.setSubject("1.BSC Trainer-Einladung");

        messageHelper.setText(buildInviteMailHtml("1.BSC Trainer-Einladung", "Einladung", signUpKey), true);

        mailSender.send(message);
    }

	@Value("${mail.from}")
	private String sendFrom;


    public void sendInviteToVM() throws IOException {

        final String messageForVM = """
                Liebe BSC-Mitglieder,
                <br><br>
                es ist wieder so weit – die Vereinsmeisterschaften Teil 2 stehen vor der Tür! 🎉
                Wir möchten alle Mitglieder herzlich einladen, dabei zu sein – ganz egal ob jung oder alt, ob Wettkampferfahren oder Freizeit-Schwimmer. Hauptsache, ihr habt Spaß im Wasser! 💦
                <br><br>
                Im Anhang findet ihr die Ausschreibung mit allen Disziplinen und Strecken. Dort seht ihr auch, welche Lagen und Distanzen für welche Altersgruppen empfohlen sind – aber keine Sorge: Das sind keine festen Vorgaben.
                Wenn du lieber eine kürzere Strecke schwimmen möchtest oder dich in einer anderen Lage ausprobieren willst – mach es einfach! Jeder darf mitmachen, so wie es für ihn oder sie am besten passt. 🙌
                <br><br>
                Wir freuen uns auf viele motivierte Schwimmerinnen und Schwimmer, spannende Läufe und vor allem auf einen Tag voller Teamgeist, Spaß und gemeinsamer BSC-Energie! ❤️
                <br><br>
                Also: Sei dabei, wenn wir gemeinsam zeigen, was in uns steckt! 🏆
                <br><br>
                Mit sportlichen Grüßen
                """;


        this.sendMailToAllBscMembers("1.BSC Vereinsmeisterschafter Teil 2 2025", "MailMessageTemplate", messageForVM, "Ausschreibung_VM2_2025.pdf");
    }

    public void sendMailToAllBscMembers(final String title, final String templateFileName, final String message, final String attachmentTitle) {
        bscMemberRepository.findAll()
                .stream()
                .filter(bscMember -> bscMember.getEmail() != null)
                .forEach(bscMember -> executor.submit(() -> sendMail(bscMember.getEmail(), title, templateFileName, message, attachmentTitle)));
    }

    @Async
    protected void sendMail(String receiverMail, String title, String templateFileName, String message, String attachmentFileName) {
        try {

            String template = loadHtmlTemplate(templateFileName);

            if (message != null) {
                template = insertMessageIntoTemplate(template, title, message);
            }

            final var mailSender = mailSenderConfig.getJavaMailSender();

            final var mail = mailSender.createMimeMessage();

            mail.setFrom(new InternetAddress(sendFrom));
            mail.setRecipients(MimeMessage.RecipientType.TO, receiverMail);

            final var messageHelper = new MimeMessageHelper(mail, true, CharEncoding.UTF_8);
            messageHelper.setFrom(sendFrom);
            messageHelper.setTo(InternetAddress.parse(receiverMail));
            messageHelper.setSubject(title);

            messageHelper.setText(template, true);

            if (attachmentFileName != null) {
                final InputStreamSource attachment = loadPdfFromResource(attachmentFileName);
                System.out.println("attachment file exists " + attachmentFileName);
                messageHelper.addAttachment(attachmentFileName, attachment);
            }

            mailSender.send(mail);
            System.out.println("Mail erfolgreich gesendet an: " + receiverMail);
        } catch (Exception e) {
            System.err.println("Fehler beim Senden an " + receiverMail + ": " + e.getMessage());
        }
    }

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
		ClassPathResource resource = new ClassPathResource("templates/email/" + filename + ".html");
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
					bscMemberRepository.findAll()
							.stream()
							.filter(bscMember -> bscMember.getEmail() != null)
							.map(member -> new BscNameMail(member.getName(),
									member.getEmail()))
							.toList();
		} else {
			receiversMails = emails.stream().map(e -> new BscNameMail("anonymous", e)).toList();
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
				invalidMails.add(new BscNameMail(member.getName(), member.getEmail()));
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

		if (!formData.mainData().morePersons().isEmpty()) {
			morePersonsInfoText.append("<p>Weitere Personen anghaben:</p><br>");

			for (ExtraPerson person : formData.mainData().morePersons()) {

				final String personInfo = format("""
						<p>Geschlecht: %s</p>
						<p>Vorname: %s</p>
						<p>Nachname: %s</p>
						<p>Geburtsdatum: %s</p>
						<br>
						""", person.extraGender(), person.extraName(), person.extraSureName(), person.extraBirthday());

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

    private String insertMessageIntoTemplate(final String template, final String title, final String message) {

        return template.replace("${title}", title)
                .replace("${message}", message);
    }

    private String buildInviteMailHtml(final String title, final String message, final String signUpKey) throws IOException {

        final String template = loadTemplate(INVITE_TEMPLATE);

        return template.replace("${title}", title)
                .replace("${message}", message)
                .replace("${link}", signUpKey);
    }

    private String buildLinkToken(final String signUpKey) {
        return format("https://registration.erster-bsc-pforzheim.de/sign-up?token=%s", signUpKey);
    }

    private String loadTemplate(final String fileName) throws IOException {
        var resource = new ClassPathResource("templates/email/" + fileName + ".html");
        return Files.readString(resource.getFile().toPath());
    }

    private InputStreamSource loadPdfFromResource(final String filename) throws IOException {
        final ClassPathResource resource = new ClassPathResource("attachments/" + filename);

        if (!resource.exists()) {
            throw new IOException("File not found with name: " + filename);
        }

        return resource;
    }

}

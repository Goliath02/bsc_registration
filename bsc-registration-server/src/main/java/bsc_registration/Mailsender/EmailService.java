package bsc_registration.Mailsender;


import bsc_registration.dto.FormData;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.codec.CharEncoding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

import static java.lang.String.format;


@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class EmailService {

	private final MailSenderConfig mailSenderConfig;

	@Value("${mail.from}")
	private String sendFrom;

	@SneakyThrows
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

    @SneakyThrows
    public void sendEmailToCourseOwner(final List<String> targetEmails, final FormData formData) throws MessagingException {

        final var mailSender = mailSenderConfig.getJavaMailSender();

        final var message = mailSender.createMimeMessage();

        message.setFrom(new InternetAddress(sendFrom));

        message.setRecipients(Message.RecipientType.TO, String.join(",", targetEmails));

        MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, CharEncoding.UTF_8);
        messageHelper.setFrom(sendFrom);
        messageHelper.setTo(targetEmails.toArray(new String[0]));
        messageHelper.setSubject("1.BSC Pforzheim Info");

        messageHelper.setText(buildCourseOwnerHtml(formData),true);

        mailSender.send(message);

    }


	public void sendEmailToUser(final String email, final String type) throws MessagingException, MailSendException {

		final JavaMailSender mailSender = mailSenderConfig.getJavaMailSender();

		final var message = mailSender.createMimeMessage();

		message.setFrom(new InternetAddress(sendFrom));
		message.setRecipients(MimeMessage.RecipientType.TO, email);

		final var messageHelper = new MimeMessageHelper(message, true, CharEncoding.UTF_8);
		messageHelper.setFrom(sendFrom);
		messageHelper.setTo(InternetAddress.parse(email));
		messageHelper.setSubject("1.BSC Pforzheim Info");

		messageHelper.setText(buildUserMailHtml(type),true);

		mailSender.send(message);
	}

	private String buildUserMailHtml(String option) {

		String selectedTable;

		switch (option) {
			case "Nichtschwimmerkurs" -> selectedTable = NICHTSCHWIMMER_TABLE;
			case "Schwimm-Kurs" -> selectedTable = SCHWIMMKURS_TABLE;
			case "Wasserball" -> selectedTable = WASSERBALL_TABLE;
			default -> selectedTable = "";
		}

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
				          	<p>Mit dieser Email erhalten Sie alle nötigen Informationenn für Ihr erstes Training</p>
				          	<p>Trainingszeiten anhand Ihrere Daten:</p>
				
				          	<div class="tableWrapper">
				          		<table>
				          			%s
				          		</table>
				          	</div>
				
				          	<p>Weitere Informationen finden Sie unter der <a href="https://erster-bsc-pforzheim.de/kursplan-schwimmen" style="color: cornflowerblue">Website</a>.</p>
				          	<p>Wenn Sie wetiere Fragen haben kontaktieren Sie gerne <a href="mailto: test@mail.com" style="color: cornflowerblue">test@mail.com</a>.</p>
				""", CSS, selectedTable);
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
                mainData.birthday(),
                this.calculateAge(mainData.birthday()),
                mainData.email(),
                mainData.phone()
        );
    }

    private int calculateAge(String birthday) {

		LocalDate birthdayDate = LocalDate.parse(birthday);
		LocalDate today = LocalDate.now();

		return today.getYear() - birthdayDate.getYear();
    }

	private static String NICHTSCHWIMMER_TABLE = """
			<tbody class="table-auto overflow-hidden">
											<tr class="bg-bsc-gray">
												<th class="p-[0.5em]">Niveu</th>
												<th class="p-[0.5em]">Tag</th>
												<th class="p-[0.5em]">Uhrzeit</th>
												<th class="p-[0.5em]">Halle</th>
												<th class="p-[0.5em]">Swimstars Niveu</th>
											</tr>
				
											<tr>
												<td ROWSPAN="2" class="p-[0.5em]">Nichtschwimmerkurs</td>
												<td class="p-[0.5em] border border-bsc-gray">Dienstag</td>
												<td class="p-[0.5em] border border-bsc-gray">16:00 - 17:00 Uhr</td>
												<td class="p-[0.5em] border border-bsc-gray">Konrad-Adenauer-Halle</td>
												<td ROWSPAN="2" class="p-[0.5em]">Türkis/Grün/Blau</td>
											</tr>
				
											<tr>
												<td class="p-[0.5em] border border-bsc-gray">Samstag</td>
												<td class="p-[0.5em] border border-bsc-gray">13:00 - 14:00 Uhr</td>
												<td class="p-[0.5em] border border-bsc-gray">Fritz-Erler-Halle</td>
											</tr>
										</tbody>
			""";

	private static String SCHWIMMKURS_TABLE = """
			<tbody class="table-auto overflow-hidden">
										<tr class="bg-bsc-gray">
											<th class="p-[0.5em]">Niveu</th>
											<th class="p-[0.5em]">Tag</th>
											<th class="p-[0.5em]">Uhrzeit</th>
											<th class="p-[0.5em]">Halle</th>
											<th class="p-[0.5em]">Swimstars Niveu</th>
										</tr>
			
										<tr>
											<td ROWSPAN="2" class="p-[0.5em] border border-bsc-gray">Anfänger 1</td>
											<td class="p-[0.5em] border border-bsc-gray">Dienstag</td>
											<td class="p-[0.5em] border border-bsc-gray">17:00 - 18:00 Uhr</td>
											<td class="p-[0.5em] border border-bsc-gray">Konrad-Adenauer-Halle</td>
											<td ROWSPAN="2" class="p-[0.5em]">Schwarz</td>
										</tr>
			
										<tr>
											<td class="p-[0.5em] border border-bsc-gray">Samstag</td>
											<td class="p-[0.5em] border border-bsc-gray">13:00 - 14:00 Uhr</td>
											<td class="p-[0.5em] border border-bsc-gray">Fritz-Erler-Halle</td>
										</tr>
			
										<tr>
											<td ROWSPAN="3" class="p-[0.5em] border border-bsc-gray">Anfänger 2</td>
											<td ROWSPAN="2" class="p-[0.5em] border border-bsc-gray">Samstag</td>
											<td class="p-[0.5em] border border-bsc-gray">11:00 - 12:00 Uhr</td>
											<td ROWSPAN="2" class="p-[0.5em] border border-bsc-gray">Fritz-Erler-Halle</td>
											<td ROWSPAN="3" class="p-[0.5em] border border-bsc-gray">Schwarz/Rot</td>
										</tr>
			
									<tr>
										<td class="p-[0.5em] border border-bsc-gray">12:00 - 13:00 Uhr</td>
									</tr>
			
										<tr>
											<td class="p-[0.5em] border border-bsc-gray">Dienstag</td>
											<td class="p-[0.5em] border border-bsc-gray">18: 00 - 19:00 Uhr </td>
											<td class="p-[0.5em] border border-bsc-gray">Konrad-Adenauer-Halle</td>
										</tr>
			
			
									<tr>
										<td rowspan="2" class="p-[0.5em] border border-bsc-gray">Fortgeschrittene 1</td>
										<td class="p-[0.5em] border border-bsc-gray">Dienstag</td>
										<td class="p-[0.5em] border border-bsc-gray">16:00 - 17:00 Uhr</td>
										<td rowspan="2" class="p-[0.5em] border border-bsc-gray">Fritz-Erler-Halle</td>
										<td rowspan="2" class="p-[0.5em] border border-bsc-gray">Rot</td>
									</tr>
			
									<tr>
										<td class="p-[0.5em] border border-bsc-gray">Samstag</td>
										<td class="p-[0.5em] border border-bsc-gray">11:00 - 12:00 Uhr</td>
									</tr>
			
			
									<tr>
										<td rowspan="2" class="p-[0.5em] border border-bsc-gray">Fortgeschrittene 2</td>
										<td class="p-[0.5em] border border-bsc-gray">Mittwoch</td>
										<td class="p-[0.5em] border border-bsc-gray">18:00 - 19:00 Uhr</td>
										<td class="p-[0.5em] border border-bsc-gray">Konrad-Adenauer-Halle</td>
										<td rowspan="2" class="p-[0.5em] border border-bsc-gray">Schwarz/Rot</td>
									</tr>
			
									<tr>
										<td class="p-[0.5em] border border-bsc-gray">Samstag</td>
										<td class="p-[0.5em] border border-bsc-gray">11:00 - 12:00 Uhr</td>
										<td class="p-[0.5em] border border-bsc-gray">Fritz-Erler-Halle</td>
									</tr>
			
									<tr>
										<td rowspan="2" class="p-[0.5em] border border-bsc-gray">Fortgeschrittene 3</td>
										<td class="p-[0.5em] border border-bsc-gray">Mittwoch</td>
										<td class="p-[0.5em] border border-bsc-gray">18:00 - 19:00 Uhr</td>
										<td class="p-[0.5em] border border-bsc-gray">Konrad-Adenauer-Halle</td>
										<td rowspan="2" class="p-[0.5em] border border-bsc-gray">Gold</td>
									</tr>
			
									<tr>
										<td class="p-[0.5em] border border-bsc-gray">Samstag</td>
										<td class="p-[0.5em] border border-bsc-gray">11:00 - 12:00 Uhr</td>
										<td class="p-[0.5em] border border-bsc-gray">Fritz-Erler-Halle</td>
									</tr>
			
			
										<tr>
											<td rowspan="2" class="p-[0.5em] border border-bsc-gray">Fortgeschrittene 4</td>
											<td class="p-[0.5em] border border-bsc-gray">Dienstag</td>
											<td class="p-[0.5em] border border-bsc-gray">20:00 - 21:00 Uhr</td>
											<td class="p-[0.5em] border border-bsc-gray">Eutinger-Halle</td>
											<td rowspan="2" class="p-[0.5em] border border-bsc-gray">Gold +</td>
										</tr>
			
										<tr>
											<td class="p-[0.5em] border border-bsc-gray">Samstag</td>
											<td class="p-[0.5em] border border-bsc-gray">12:00 - 13:00 Uhr</td>
											<td class="p-[0.5em] border border-bsc-gray">Fritz-Erler-Halle</td>
										</tr>
			
									<tr>
										<td rowspan="2" class="p-[0.5em] border border-bsc-gray">Masters</td>
										<td class="p-[0.5em] border border-bsc-gray">Mittwoch</td>
										<td class="p-[0.5em] border border-bsc-gray">17:00 - 18:00 Uhr</td>
										<td rowspan="2" class="p-[0.5em] border border-bsc-gray">Fritz-Erler-Halle</td>
										<td rowspan="2" class="p-[0.5em] border border-bsc-gray">-</td>
									</tr>
			
									<tr>
										<td class="p-[0.5em] border border-bsc-gray">Samstag</td>
										<td class="p-[0.5em] border border-bsc-gray">13:00 - 14:00 Uhr</td>
									</tr>
			
									<tr>
										<td rowspan="2" class="p-[0.5em] border border-bsc-gray">Breitensport</td>
										<td class="p-[0.5em] border border-bsc-gray">Dienstag</td>
										<td rowspan="2" class="p-[0.5em] border border-bsc-gray">20:00 - 21:00 Uhr</td>
										<td class="p-[0.5em] border border-bsc-gray">Eutinger-Halle</td>
										<td rowspan="2" class="p-[0.5em] border border-bsc-gray">-</td>
									</tr>
			
									<tr>
										<td class="p-[0.5em] border border-bsc-gray">Freitag</td>
										<td class="p-[0.5em] border border-bsc-gray">Konrad-Adenauer-Halle</td>
									</tr>
								</tbody>
			
			""";

	private static String WASSERBALL_TABLE = """
			<tbody class="table-auto overflow-hidden">
										<tr class="bg-bsc-gray">
											<th class="p-[0.5em]">Niveu</th>
											<th class="p-[0.5em]">Tag</th>
											<th class="p-[0.5em]">Uhrzeit</th>
											<th class="p-[0.5em]">Halle</th>
											<th class="p-[0.5em]">Altersgruppe</th>
										</tr>
			
										<tr>
											<td ROWSPAN="3" class="p-[0.5em] border border-bsc-gray">Jugend Wasserball / Anfänger</td>
											<td class="p-[0.5em] border border-bsc-gray">Dienstag</td>
											<td class="p-[0.5em] border border-bsc-gray">16:00 - 17:00 Uhr</td>
											<td ROWSPAN="3" class="p-[0.5em] border border-bsc-gray">Fritz-Erler-Halle</td>
											<td ROWSPAN="3" class="p-[0.5em]">8 - 18 Jahre</td>
										</tr>
			
										<tr>
											<td class="p-[0.5em] border border-bsc-gray">Donnerstag</td>
											<td class="p-[0.5em] border border-bsc-gray">16:30 - 18:00 Uhr</td>
										</tr>
			
										<tr>
											<td class="p-[0.5em] border border-bsc-gray">Samstag</td>
											<td class="p-[0.5em] border border-bsc-gray">16:00 - 18:00 Uhr</td>
										</tr>
			
									<tr>
										<td rowspan="2" class="p-[0.5em] border border-bsc-gray">1. & 2. Wasserball Manschaft</td>
										<td class="p-[0.5em] border border-bsc-gray">Montag</td>
										<td rowspan="2" class="p-[0.5em] border border-bsc-gray">20:00 - 22:00 Uhr</td>
										<td rowspan="2" class="p-[0.5em] border border-bsc-gray">Fritz-Erler-Halle</td>
										<td rowspan="2" class="p-[0.5em] border border-bsc-gray">18 Jahre +</td>
									</tr>
			
									<tr>
										<td class="p-[0.5em] border border-bsc-gray">Freitag</td>
									</tr>
			
									<tr>
										<td class="p-[0.5em] border border-bsc-gray">Masters</td>
										<td class="p-[0.5em] border border-bsc-gray">Dienstag</td>
										<td class="p-[0.5em] border border-bsc-gray">21:00 - 22:00 Uhr</td>
										<td class="p-[0.5em] border border-bsc-gray">Fritz-Erler-Halle</td>
										<td class="p-[0.5em] border border-bsc-gray">-</td>
									</tr>
			
									</tbody>
			""";


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

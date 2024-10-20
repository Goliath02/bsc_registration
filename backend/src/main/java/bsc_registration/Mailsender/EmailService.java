package bsc_registration.Mailsender;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.CharEncoding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.List;


@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class EmailService {

	private final MailSenderConfig mailSenderConfig;

	@Value("${mail.from}")
	private String sendFrom;

	@Value("${mail.to}")
	private String sendTo;

	public void sendMail(String csv, List<MultipartFile> files) throws MessagingException {

		final JavaMailSender mailSender = mailSenderConfig.getJavaMailSender();

		MimeMessage message = mailSender.createMimeMessage();

		message.setFrom(new InternetAddress(sendFrom));
		message.setRecipients(MimeMessage.RecipientType.TO, sendTo);

		message.setText("Eine neue Anmeldung befindet sich im Anhang");

		MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, CharEncoding.UTF_8);
		messageHelper.setFrom(sendFrom);
		messageHelper.setTo(InternetAddress.parse(sendTo));
		messageHelper.setSubject("Neue Anmeldung vom für den 1.BSC!");
		messageHelper.setText("Neue Anmeldedaten im Anhang");

		for (MultipartFile file : files) {
			messageHelper.addAttachment(file.getOriginalFilename(), file);
		}

		ByteArrayDataSource inputStream = new ByteArrayDataSource(csv.getBytes(StandardCharsets.UTF_8), "application/octet-stream");

		messageHelper.addAttachment("NeueMitglieder.csv", inputStream);

		mailSender.send(message);

	}


}

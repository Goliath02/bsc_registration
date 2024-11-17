package bsc_registration;

import bsc_registration.Mailsender.EmailService;
import bsc_registration.dto.FormData;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
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

	final private CsvUtil csvUtil;

	public void sendEmailToRegistration(FormData formData, List<MultipartFile> files) throws MessagingException, IOException {

		final String csvFromFormData = csvUtil.createCsvFromFormData(formData);

		emailService.sendMail(csvFromFormData, files);

	}


	public void sendEmailToRegistratedUser(FormData formData) throws MessagingException, MailSendException {
		emailService.sendEmailToUser(formData.mainData().email(), formData.mainData().reason());
	}
}

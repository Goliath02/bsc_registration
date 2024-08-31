package bsc_registration;

import bsc_registration.Mailsender.EmailService;
import bsc_registration.dto.FormData;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RegistrationModule {

	final private EmailService emailService;

	final private CsvUtil csvUtil;

	public void sendEmail(FormData formData) throws MessagingException, IOException {

		final String csvFromFormData = csvUtil.createCsvFromFormData(formData);

		emailService.sendMail(csvFromFormData);

	}

}

package bsc_registration.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DevUtil {

	@Value("${dev.email}")
	private String devEmail;

	public String getEmailFromConfig(final String email) {

		if (devEmail == null) {
			return email;
		}

		if (email.isBlank() || email.equals("example@mail.com")) {
			return devEmail;
		}

		return email;
	}

	public List<String> getEmailFromConfig(final List<String> emails) {

		if (devEmail == null) {
			return emails;
		}

		return emails.stream().map(email -> {

			if (email.isBlank() || email.equals("example@mail.com")) {
				return devEmail;
			}

			return email;
		}).toList();
	}
}

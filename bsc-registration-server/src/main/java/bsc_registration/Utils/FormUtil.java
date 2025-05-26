package bsc_registration.Utils;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class FormUtil {

	public static final int FULL_AGE = 18;

	public static String formatDate(final LocalDate date) {

		if (date == null) {
			return "";
		}

		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return date.format(formatter);
	}

	public static String getTypeByBirthday(final LocalDate birthday) {

		final int age = calculateAge(birthday);
		return age <= FULL_AGE ? "Erwachsener" : "Kind";
	}

	public static int calculateAge(final LocalDate birthday) {
		return Period.between(birthday, LocalDate.now()).getYears();
	}
}

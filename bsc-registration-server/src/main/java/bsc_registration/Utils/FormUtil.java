package bsc_registration.Utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FormUtil {

    public static final int FULL_AGE = 18;

    public static String formatDate(final LocalDate date) {
        final var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date.format(formatter);
    }

    public static String getTypeByBirthday(final LocalDate birthday) {

        final var age = calculateAge(birthday);
        return age <= FULL_AGE ? "Erwachsener" : "Kind";
    }

    public static int calculateAge(final LocalDate birthday) {

        final var today = LocalDate.now();

        return today.getYear() - birthday.getYear();
    }
}

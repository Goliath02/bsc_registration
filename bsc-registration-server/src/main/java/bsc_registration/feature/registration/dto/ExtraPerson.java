package bsc_registration.feature.registration.dto;

import java.time.LocalDate;

public record ExtraPerson(
		String extraName,
		String extraSureName,
		LocalDate extraBirthday,
		String extraGender
) {
}
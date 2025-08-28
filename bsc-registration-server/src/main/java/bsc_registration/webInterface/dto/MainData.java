package bsc_registration.webInterface.dto;

import java.time.LocalDate;
import java.util.List;

public record MainData(
		String type,
		String reason,
		String name,
		String surename,
		LocalDate birthday,
		String gender,
		String email,
		String phone,
		String street,
		String plz,
		String place,
		LocalDate entryDate,
		List<ExtraPerson> morePersons
) {
}
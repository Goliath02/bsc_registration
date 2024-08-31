package bsc_registration.dto;

public record MainData(
		String type,
		String reason,
		String name,
		String surename,
		String birthday,
		String gender,
		String email,
		String phone,
		String street,
		String plz,
		String place
) {
}

package bsc_registration.dto;

import java.time.LocalDate;

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
        LocalDate entryDate
) {}
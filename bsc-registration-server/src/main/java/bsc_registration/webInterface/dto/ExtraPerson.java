package bsc_registration.webInterface.dto;

import java.time.LocalDate;

public record ExtraPerson(
        String name,
        String surename,
        LocalDate birthday,
        String gender
) {
}

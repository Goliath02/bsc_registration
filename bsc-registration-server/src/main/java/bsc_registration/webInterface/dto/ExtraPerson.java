package bsc_registration.webInterface.dto;

import java.time.LocalDate;

public record ExtraPerson(
        String extraName,
        String extraSureName,
        LocalDate extraBirthday,
        String extraGender
) {
}
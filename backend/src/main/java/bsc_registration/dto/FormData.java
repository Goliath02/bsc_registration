package bsc_registration.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class FormData {

    String name;
    String sureName;
    LocalDate birthdate;
    String IBAN;


}

package bsc_registration.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class FormData {

    String contributionType;

    String name;
    String sureName;
    LocalDate birthday;
    String gender;
    String email;
    String phone;
    String street;
    Integer postalCode;
    String place;

    List<SubMemberData> subMemberData;

    String bankName;
    String bankOrt;
    String IBAN;
    String BIC;
    String KontoinhaberVorname;
    String KontoinhaberNachname;

    Boolean securityCheck;
}

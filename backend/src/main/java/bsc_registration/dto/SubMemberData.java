package bsc_registration.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SubMemberData {

    String name;
    String sureName;
    LocalDate birthday;
    Gender gender;
}

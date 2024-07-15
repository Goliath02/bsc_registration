package bsc_registration.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Enumerated(EnumType.STRING)
    ContributionType contributionType;

    String name;
    String sureName;
    LocalDate birthday;
    @Enumerated(EnumType.STRING)
    Gender gender;
    String email;
    String phone;
    String street;
    Integer postalCode;
    String place;

    String bankName;
    String bankOrt;
    String IBAN;
    String BIC;
    String KontoinhaberVorname;
    String KontoinhaberNachname;


}

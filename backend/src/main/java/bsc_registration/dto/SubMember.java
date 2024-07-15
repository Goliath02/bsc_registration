package bsc_registration.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;

@Entity
public class SubMember extends Member {

    @ManyToOne
    Member parentMember;

    String name;
    String sureName;
    LocalDate birthday;
    Gender gender;
}

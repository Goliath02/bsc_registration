package bsc_registration.dto;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    ContributionType contributionType;


    @OneToMany
    List<Member> subMember;

    String name;
    String sureName;
    LocalDate birthday;
    Gender gender;
    String email;
    String phone;
    String street;
    String postalCode;
    String place;





}

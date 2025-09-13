package bsc_registration.domain.entities;

import bsc_registration.webInterface.dto.ExtraPerson;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity(name = "NSWRegistration")
@Table(name = "nsw_registration")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NswRegistration {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String firstName;

  @Column
  private String sureName;

  @Column
  private String email;

  @Column
  private String phone;

  @Column
  private LocalDate registrationDate;

  @Column
  private String type;

  @Column
  private String reason;

  @Column
  private String name;

  @Column
  private String surename;

  @Column
  private LocalDate birthday;

  @Column
  private String gender;

  @Column
  private String street;

  @Column
  private String plz;

  @Column
  private String place;

  @Column
  private LocalDate entryDate;

  @Column
  private List<ExtraPerson> morePersons;

  @Column
  private String iban;

  @Column
  private String nameOfBankOwner;

  @Column
  private String sureNameBankOwner;
}

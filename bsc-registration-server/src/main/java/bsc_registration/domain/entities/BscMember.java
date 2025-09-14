package bsc_registration.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "bsc_member")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BscMember {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String name;

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
  private String iban;

  @Column
  private String nameOfBankOwner;

  @Column
  private String sureNameBankOwner;
}

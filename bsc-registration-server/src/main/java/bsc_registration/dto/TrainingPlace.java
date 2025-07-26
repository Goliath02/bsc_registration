package bsc_registration.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "training_place")
public class TrainingPlace {

    @Id
    @Column
    private long id;

    private String name;

    private String street;

    private String houseNumber;

    private String streetNumberAddition;

    private String city;

    private String state;

    private String country;

    private String postalCode;

}

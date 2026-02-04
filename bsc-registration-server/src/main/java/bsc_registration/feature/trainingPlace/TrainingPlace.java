package bsc_registration.feature.trainingPlace;

import jakarta.persistence.*;

@Entity
@Table(name = "training_place")
public class TrainingPlace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column
    private String name;

    @Column
    private String street;

    @Column
    private String houseNumber;

    @Column
    private String streetNumberAddition;

    @Column
    private String city;

    @Column
    private String state;

    @Column
    private String country;

    @Column
    private String postalCode;

}

package bsc_registration.webInterface.dto;

import lombok.Data;

@Data
public class TrainingPlaceDto {
    private String name;
    private String street;
    private String houseNumber;
    private String streetNumberAddition;
    private String city;
    private String postalCode;
}

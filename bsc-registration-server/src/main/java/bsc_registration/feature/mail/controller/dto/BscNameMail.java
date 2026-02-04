package bsc_registration.feature.mail.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BscNameMail {

    private String name;
    private String email;
}
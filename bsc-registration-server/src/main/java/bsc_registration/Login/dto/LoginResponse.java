package bsc_registration.Login.dto;

import lombok.Data;

@Data
public class LoginResponse {

    private long expiresIn;
    private String token;
}

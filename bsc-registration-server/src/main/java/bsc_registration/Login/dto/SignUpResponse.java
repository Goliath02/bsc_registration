package bsc_registration.Login.dto;

import lombok.Data;

@Data
public class SignUpResponse {
	private long expiresIn;
	private String token;
}

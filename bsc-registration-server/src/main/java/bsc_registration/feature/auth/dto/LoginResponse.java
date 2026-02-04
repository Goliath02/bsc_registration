package bsc_registration.feature.auth.dto;

import lombok.Data;

@Data
public class LoginResponse {

	private long expiresIn;
	private String token;
}

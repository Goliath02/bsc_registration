package bsc_registration.webInterface.dto;

import lombok.Data;

@Data
public class LoginResponse {

	private long expiresIn;
	private String token;
}

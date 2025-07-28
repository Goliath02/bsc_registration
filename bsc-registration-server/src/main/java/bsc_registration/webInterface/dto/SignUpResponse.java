package bsc_registration.webInterface.dto;

import lombok.Data;

@Data
public class SignUpResponse {
	private long expiresIn;
	private String token;
}

package bsc_registration.feature.auth.dto;

import lombok.Data;

@Data
public class LoginDto {
	private String email;
	private String password;
}

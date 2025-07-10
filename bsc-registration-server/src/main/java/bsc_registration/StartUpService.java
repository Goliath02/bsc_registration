package bsc_registration;

import bsc_registration.Login.AuthService;
import bsc_registration.Login.AuthorityRepository;
import bsc_registration.Login.UserRepository;
import bsc_registration.dto.BscAuthority;
import bsc_registration.dto.BscUser;
import bsc_registration.dto.SignUpKey;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StartUpService {

	@Value("${bsc.admin.email}")
	private String adminEmail;

	@Value("${bsc.admin.password}")
	private String adminPassword;

	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final UserRepository userRepository;
	private final AuthorityRepository authRepository;
	private final AuthService authService;

	@EventListener(ApplicationReadyEvent.class)
	public void createAdminIfNotPresent() {

		createUserAuthoritiesIfNotPresent();

		final Optional<BscUser> byEmail = userRepository.findByEmail(adminEmail);

		if (byEmail.isEmpty()) {

			final BscUser admin = new BscUser();

			admin.setEmail(adminEmail);
			admin.setPassword(bCryptPasswordEncoder.encode(adminPassword));
			admin.setUserName("Goliath");

			final BscAuthority adminAuthority = authRepository.findByAuthority("admin")
					.orElseGet(() -> authRepository.save(new BscAuthority(null, "admin")));

			admin.setAuthorities(List.of(adminAuthority));

			final SignUpKey signUpKey = authService.createSignUpKey();
			admin.setSignUpKey(signUpKey);

			userRepository.save(admin);
		}
	}

	private void createUserAuthoritiesIfNotPresent() {

		List<BscAuthority> allAuthorities = authRepository.findAll();

		if (allAuthorities.isEmpty()) {

			final BscAuthority courseOwner = new BscAuthority();
			final BscAuthority user = new BscAuthority();
			final BscAuthority admin = new BscAuthority();

			admin.setAuthority("admin");
			courseOwner.setAuthority("courseOwner");
			user.setAuthority("user");

			authRepository.saveAllAndFlush(List.of(admin, courseOwner, user));
		}
	}
}

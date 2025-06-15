package bsc_registration.Login;

import bsc_registration.Login.dto.LoginDto;
import bsc_registration.Login.dto.SignUpDto;
import bsc_registration.dto.BscUser;
import bsc_registration.dto.SignUpKey;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final KeyRepository keyRepository;

    public BscUser signup(SignUpDto input) {
        final BscUser user = new BscUser();

        user.setUserName(input.getUsername());
        user.setEmail(input.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(input.getPassword()));

        SignUpKey signUpKey = new SignUpKey();
        signUpKey.setKey(input.getSignUpKey());
        user.setSignUpKey(signUpKey);

        return userRepository.save(user);
    }

    public BscUser authenticate(final LoginDto loginDto) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

        final BscUser bscUser = userRepository.findByEmail(loginDto.getEmail()).orElseThrow();

        if (bCryptPasswordEncoder.matches(loginDto.getPassword(), bscUser.getPassword())) {
            return bscUser;
        }

        return null;
    }

    public SignUpKey createSignUpKey() {
        final var signUpKey = new SignUpKey();

        signUpKey.setKey(UUID.randomUUID().toString());

        return signUpKey;
    }
}

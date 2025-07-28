package bsc_registration.domain.service;

import bsc_registration.webInterface.dto.AuthorityType;
import bsc_registration.infrastructure.repository.AuthorityRepository;
import bsc_registration.infrastructure.repository.KeyRepository;
import bsc_registration.infrastructure.repository.UserRepository;
import bsc_registration.webInterface.dto.LoginDto;
import bsc_registration.webInterface.dto.SignUpDto;
import bsc_registration.domain.entities.BscAuthority;
import bsc_registration.domain.entities.BscUser;
import bsc_registration.domain.entities.SignUpKey;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final KeyRepository keyRepository;

    public BscUser signup(SignUpDto input) {
        final BscUser user = new BscUser();

        user.setUserName(input.getUsername());
        user.setEmail(input.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(input.getPassword()));



        Optional<SignUpKey> optionalKey = keyRepository.getKeyByKey(input.getSignUpKey());

        final SignUpKey signUpKey = optionalKey.orElseThrow();

        if (userRepository.hasUserWithKey(signUpKey)) {
            throw new IllegalArgumentException();
        }

        user.setSignUpKey(signUpKey);

        final Optional<BscAuthority> byAuthority = authorityRepository.findByAuthority(signUpKey.getAuthority());

        user.setAuthorities(List.of(byAuthority.orElseThrow()));

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

    public SignUpKey createSignUpKey(final AuthorityType authority) {
        final var signUpKey = new SignUpKey();

        signUpKey.setAuthority(authority);

        signUpKey.setKey(UUID.randomUUID().toString());

        return signUpKey;
    }

    public void saveSignUpKey(SignUpKey signUpKey) {
        keyRepository.save(signUpKey);
    }
}

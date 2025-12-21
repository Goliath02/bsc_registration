package bsc_registration.domain.service;

import bsc_registration.domain.entities.BscAuthority;
import bsc_registration.domain.entities.BscUser;
import bsc_registration.domain.entities.SignUpKey;
import bsc_registration.features.auth.repository.AuthorityRepository;
import bsc_registration.features.auth.repository.KeyRepository;
import bsc_registration.features.auth.repository.UserRepository;
import bsc_registration.webInterface.dto.AuthorityType;
import bsc_registration.webInterface.dto.LoginDto;
import bsc_registration.webInterface.dto.SignUpDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service class for authentication-related operations.
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final KeyRepository keyRepository;

    /**
     * Registers a new user.
     *
     * @param input the sign up DTO
     * @return the created BscUser entity
     */
    public BscUser signup(SignUpDto input) {
        final BscUser user = new BscUser();

        user.setUserName(input.getUsername());
        user.setEmail(input.getEmail());

        // Encode password
        user.setPassword(bCryptPasswordEncoder.encode(input.getPassword()));

        Optional<SignUpKey> optionalKey = keyRepository.getKeyByKey(input.getSignUpKey());

        final SignUpKey signUpKey = optionalKey.orElseThrow(() ->
                new IllegalArgumentException("Invalid Sign Up Key provided")
        );

        if (userRepository.hasUserWithKey(signUpKey)) {
            throw new IllegalArgumentException("User already exists with this Sign Up Key");
        }

        user.setSignUpKey(signUpKey);

        final Optional<BscAuthority> authority =
                authorityRepository.findByAuthority(AuthorityType.valueOf(signUpKey.getAuthority().getAuthority()));

        user.setAuthorities(List.of(authority.orElseThrow(() ->
                new IllegalArgumentException("Invalid authority")
        )));

        return userRepository.save(user);
    }

    /**
     * Authenticates a user.
     *
     * @param loginDto the login DTO
     * @return the authenticated BscUser entity
     */
    public BscUser authenticate(final LoginDto loginDto) {
        // Authenticate user
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

        final BscUser bscUser = userRepository.findByEmail(loginDto.getEmail()).orElseThrow(() ->
                new IllegalArgumentException("Invalid username or password")
        );

        // Verify password
        if (bCryptPasswordEncoder.matches(loginDto.getPassword(), bscUser.getPassword())) {
            return bscUser;
        }

        throw new IllegalArgumentException("Invalid username or password");
    }

    /**
     * Creates a sign up key.
     *
     * @param authorityEnum the authority type
     * @return the created SignUpKey entity
     */
    @Transactional(rollbackOn = Exception.class)
    public SignUpKey createSignUpKey(final AuthorityType authorityEnum) {
        final var signUpKey = new SignUpKey();

        final BscAuthority bscAuthority = authorityRepository.findByAuthority(authorityEnum).orElseThrow(() ->
                new IllegalArgumentException("Invalid authority")
        );

        signUpKey.setAuthority(bscAuthority);

        // Generate and set unique key
        signUpKey.setKey(UUID.randomUUID().toString());

        return signUpKey;
    }

    /**
     * Saves a sign up key.
     *
     * @param signUpKey the SignUpKey entity to save
     */
    public void saveSignUpKey(final SignUpKey signUpKey) {
        keyRepository.save(signUpKey);
    }

    /**
     * Deletes a member associated with the given keyId.
     *
     * @param keyId the ID of the sign up key
     */
    public void deleteMemberWithKey(final long keyId) {
        final SignUpKey signUpKey = keyRepository.findById(keyId).orElseThrow(() ->
                new IllegalArgumentException("Invalid Sign Up Key ID")
        );

        keyRepository.delete(signUpKey);
    }
}
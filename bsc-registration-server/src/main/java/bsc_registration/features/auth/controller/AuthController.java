package bsc_registration.features.auth.controller;

import bsc_registration.features.auth.entity.BscUser;
import bsc_registration.features.auth.entity.SignUpKey;
import bsc_registration.features.auth.service.AuthService;
import bsc_registration.features.auth.service.JwtService;
import bsc_registration.webInterface.dto.AuthorityType;
import bsc_registration.webInterface.dto.LoginDto;
import bsc_registration.webInterface.dto.SignUpDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;
    private final JwtService jwtService;

    @PostMapping("/signUp")
    public ResponseEntity<BscUser> signUp(@RequestBody final SignUpDto signUpDto) {
        try {
            logger.info("Starting user registration with email: {}", signUpDto.getEmail());
            final BscUser signup = authService.signup(signUpDto);
            logger.info("User registered successfully");
            return ResponseEntity.ok(signup);
        } catch (Exception e) {
            logger.warn("Error during user registration: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BscUser()); // Return an empty or appropriate response for error
        }
    }

    @PostMapping("/key/create")
    public ResponseEntity<String> createSignUpKey(@RequestParam final AuthorityType authority) {
        try {
            logger.info("Creating sign-up key for authority: {}", authority);
            final SignUpKey signUpKey = authService.createSignUpKey(authority);
            logger.info("Sign-up key created successfully");
            authService.saveSignUpKey(signUpKey);

            return ResponseEntity.status(HttpStatus.CREATED).body(signUpKey.getKey());
        } catch (Exception e) {
            logger.warn("Error during sign-up key creation: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create sign-up key");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody final LoginDto loginDto, HttpServletResponse response) {
        try {
            logger.info("Starting user login with email: {}", loginDto.getEmail());
            final BscUser authenticate = authService.authenticate(loginDto);
            logger.info("User authenticated successfully");
            if (authenticate == null) {
                logger.warn("Authentication failed, user not found");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
            }

            final String token = jwtService.generateToken(authenticate);

            Cookie cookie = new Cookie("jwt", token);
            cookie.setHttpOnly(true);
            cookie.setSecure(true); // in dev ggf. false
            cookie.setPath("/");
            cookie.setMaxAge(7 * 24 * 60 * 60); // 7 Tage
            response.addCookie(cookie);

            return ResponseEntity.ok("Login successful");
        } catch (Exception e) {
            logger.warn("Error during user login: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to log in");
        }
    }

    @GetMapping("/authenticated")
    public ResponseEntity<String> authenticated(HttpServletRequest request) {
        try {
            logger.info("Checking if user is authenticated");
            Cookie[] cookies = request.getCookies();
            String token = null;

            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("jwt".equals(cookie.getName())) {
                        token = cookie.getValue();
                    }
                }
            }

            if (token == null || !jwtService.isTokenValid(token)) {
                logger.warn("Authentication failed, token invalid or expired");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
            }

            logger.info("User is authenticated");
            return ResponseEntity.ok("Valid");
        } catch (Exception e) {
            logger.warn("Error during authentication check: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to check authentication");
        }
    }
}
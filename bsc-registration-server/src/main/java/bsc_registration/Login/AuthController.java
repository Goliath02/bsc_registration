package bsc_registration.Login;

import bsc_registration.JWT.JwtService;
import bsc_registration.Login.dto.LoginDto;
import bsc_registration.Login.dto.LoginResponse;
import bsc_registration.Login.dto.SignUpDto;
import bsc_registration.dto.AuthorityType;
import bsc_registration.dto.BscUser;
import bsc_registration.dto.SignUpKey;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;


    @PostMapping("/signUp")
    public ResponseEntity<BscUser> signUp(@RequestBody final SignUpDto signUpDto) {

        final BscUser signup = authService.signup(signUpDto);

        return ResponseEntity.ok(signup);

    }

    @PostMapping("/key/create")
    public ResponseEntity<String> createSignUpKey(@RequestParam final AuthorityType authority) {

        final SignUpKey signUpKey = authService.createSignUpKey(authority);

        authService.saveSignUpKey(signUpKey);

        return ResponseEntity.status(HttpStatus.CREATED).body(signUpKey.getKey());
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody final LoginDto loginDto, HttpServletResponse response) {

        final BscUser authenticate = authService.authenticate(loginDto);

        if (authenticate == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        final String token = jwtService.generateToken(authenticate);

        final var loginResponse = new LoginResponse();

        Cookie cookie = new Cookie("jwt", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true); // in dev ggf. false
        cookie.setPath("/");
        cookie.setMaxAge(7 * 24 * 60 * 60); // 7 Tage
        response.addCookie(cookie);

        return ResponseEntity.ok("Login successfull");
    }

    @GetMapping("/authenticated")
    public ResponseEntity<String> authenticated(HttpServletRequest request) {

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
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok("valid");

    }
}

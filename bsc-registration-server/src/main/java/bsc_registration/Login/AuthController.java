package bsc_registration.Login;

import bsc_registration.JWT.JwtService;
import bsc_registration.Login.dto.LoginDto;
import bsc_registration.Login.dto.LoginResponse;
import bsc_registration.Login.dto.SignUpDto;
import bsc_registration.dto.BscUser;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;


    @PostMapping("/signUp")
    public ResponseEntity<BscUser> signUp(@RequestBody final SignUpDto signUpDto) {

        final BscUser signup = authService.signup(signUpDto);

        return ResponseEntity.ok(signup);

    }

    @PostMapping("/createKey")
    @PreAuthorize("hasRole('admin')")
    public void createSignUpKey() {
        authService.createSignUpKey();

    }

    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestBody final LoginDto loginDto,  HttpServletResponse response) {

        final BscUser authenticate = authService.authenticate(loginDto);

        if (authenticate == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        final String token = jwtService.generateToken(authenticate);

        final var loginResponse = new LoginResponse();

        loginResponse.setToken(token);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        Cookie cookie = new Cookie("jwt", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true); // in dev ggf. false
        cookie.setPath("/");
        cookie.setMaxAge(7 * 24 * 60 * 60); // 7 Tage
        response.addCookie(cookie);

        return ResponseEntity.ok("Login successfull");
    }
}

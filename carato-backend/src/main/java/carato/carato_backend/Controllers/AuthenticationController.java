package carato.carato_backend.Controllers;

import carato.carato_backend.DTOs.Post_Put_Requests.Authentications.LoginRequest;
import carato.carato_backend.DTOs.Post_Put_Requests.Authentications.SignupRequest;
import carato.carato_backend.Services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(maxAge = 3600)
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@RequestBody SignupRequest signupRequest) {

        return ResponseEntity.ok(authenticationService.signUp(signupRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

        return ResponseEntity.ok(authenticationService.login(loginRequest));
    }
}
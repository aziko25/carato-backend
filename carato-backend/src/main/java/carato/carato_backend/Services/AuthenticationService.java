package carato.carato_backend.Services;

import carato.carato_backend.DTOs.Post_Put_Requests.Authentications.LoginRequest;
import carato.carato_backend.DTOs.Post_Put_Requests.Authentications.SignupRequest;
import carato.carato_backend.Models.Users.Users;
import carato.carato_backend.Repositories.Users.UsersRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

import static carato.carato_backend.Configurations.JWT.AuthorizationMethods.getSecretKey;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UsersRepository usersRepository;

    @Value("${jwt.token.expired}")
    private Long expired;

    public Users signUp(SignupRequest request) {

        if (!request.getPassword().equals(request.getRePassword())) {

            throw new IllegalArgumentException("Passwords do not match");
        }

        if (usersRepository.existsByUsername(request.getUsername())) {

            throw new EntityExistsException("Username already exists");
        }

        Users user = Users.builder()
                .username(request.getUsername())
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .password(request.getPassword())
                .registrationTime(LocalDateTime.now())
                .role("USER")
                .build();

        return usersRepository.save(user);
    }

    public String login(LoginRequest request) {

        Users user = usersRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("Username Not Found"));

        if (Objects.equals(user.getPassword(), request.getPassword())) {

            Claims claims = Jwts.claims();

            claims.put("id", user.getId());
            claims.put("role", user.getRole());

            // Expires in a week
            Date expiration = new Date(System.currentTimeMillis() + expired);

            return Jwts.builder()
                    .setClaims(claims)
                    .setExpiration(expiration)
                    .signWith(getSecretKey())
                    .compact();
        }
        else {

            throw new IllegalArgumentException("Password Didn't Match!");
        }
    }
}
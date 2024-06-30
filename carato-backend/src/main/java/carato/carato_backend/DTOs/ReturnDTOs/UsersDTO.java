package carato.carato_backend.DTOs.ReturnDTOs;

import carato.carato_backend.Models.Users.Users;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsersDTO {

    private Long id;
    private String username;
    private String email;
    private String phone;

    @JsonFormat(shape = STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime registrationTime;

    private String fullName;
    private String role;

    public UsersDTO(Users user) {

        id = user.getId();
        username = user.getUsername();
        email = user.getEmail();
        phone = user.getPhone();
        registrationTime = user.getRegistrationTime();
        fullName = user.getFullName();
        role = user.getRole();
    }
}
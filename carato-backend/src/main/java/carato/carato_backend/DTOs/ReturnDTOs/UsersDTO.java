package carato.carato_backend.DTOs.ReturnDTOs;

import carato.carato_backend.Models.Users.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsersDTO {

    private Long id;
    private String username;
    private String email;
    private String phone;
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
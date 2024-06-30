package carato.carato_backend.DTOs.Post_Put_Requests.Users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsersRequest {

    private String username;
    private String email;
    private String phone;
    private String password;
    private String fullName;
    private String role;
}
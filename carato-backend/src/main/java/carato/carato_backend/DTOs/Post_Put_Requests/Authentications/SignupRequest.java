package carato.carato_backend.DTOs.Post_Put_Requests.Authentications;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {

    private String username;
    private String fullName;
    private String email;
    private String phone;
    private String password;
    private String rePassword;
}
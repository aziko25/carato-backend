package carato.carato_backend.DTOs.ReturnDTOs;

import carato.carato_backend.Models.Users.UserAddresses;
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
public class UserAddressesDTO {

    private Long id;

    @JsonFormat(shape = STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createdTime;

    private String address;
    private Long userId;
    private String username;
    private String email;
    private String phone;

    public UserAddressesDTO(UserAddresses userAddress) {

        id = userAddress.getId();
        createdTime = userAddress.getCreatedTime();
        address = userAddress.getAddress();
        userId = userAddress.getUserId().getId();
        username = userAddress.getUserId().getUsername();
        email = userAddress.getUserId().getEmail();
        phone = userAddress.getUserId().getPhone();
    }
}
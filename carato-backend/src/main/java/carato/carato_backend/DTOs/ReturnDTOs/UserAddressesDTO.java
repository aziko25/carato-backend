package carato.carato_backend.DTOs.ReturnDTOs;

import carato.carato_backend.Models.Users.UserAddresses;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAddressesDTO {

    private Long id;
    private String address;
    private Long userId;
    private String username;
    private String email;
    private String phone;

    public UserAddressesDTO(UserAddresses userAddress) {

        id = userAddress.getId();
        address = userAddress.getAddress();
        userId = userAddress.getUserId().getId();
        username = userAddress.getUserId().getUsername();
        email = userAddress.getUserId().getEmail();
        phone = userAddress.getUserId().getPhone();
    }
}
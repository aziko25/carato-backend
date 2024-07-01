package carato.carato_backend.Services.Users;

import carato.carato_backend.DTOs.Post_Put_Requests.Users.UserAddressesRequest;
import carato.carato_backend.DTOs.ReturnDTOs.UserAddressesDTO;
import carato.carato_backend.Models.Users.UserAddresses;
import carato.carato_backend.Models.Users.Users;
import carato.carato_backend.Repositories.Users.UserAddressesRepository;
import carato.carato_backend.Repositories.Users.UsersRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static carato.carato_backend.Configurations.JWT.AuthorizationMethods.USER_ID;

@Service
@RequiredArgsConstructor
public class UserAddressesService {

    private final UserAddressesRepository userAddressesRepository;
    private final UsersRepository usersRepository;

    @Value("${pageSize}")
    private Integer pageSize;

    public UserAddressesDTO create(UserAddressesRequest userAddressesRequest) {

        Users user = usersRepository.findById(USER_ID).orElseThrow(() -> new EntityNotFoundException("User not found"));

        UserAddresses userAddress = UserAddresses.builder()
                .createdTime(LocalDateTime.now())
                .address(userAddressesRequest.getAddress())
                .userId(user)
                .build();

        return new UserAddressesDTO(userAddressesRepository.save(userAddress));
    }

    public Page<UserAddressesDTO> getAll(Integer page) {

        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by("createdTime").descending());

        Users user = usersRepository.findById(USER_ID).orElseThrow(() -> new EntityNotFoundException("User not found"));

        Page<UserAddresses> userAddresses = userAddressesRepository.findAllByUserId(user, pageable);

        return userAddresses.map(UserAddressesDTO::new);
    }

    public UserAddressesDTO getById(Long id) {

        Users user = usersRepository.findById(USER_ID).orElseThrow(() -> new EntityNotFoundException("User not found"));

        return new UserAddressesDTO(userAddressesRepository.findByIdAndUserId(id, user)
                .orElseThrow(() -> new EntityNotFoundException("User Address not found")));
    }

    public UserAddressesDTO update(Long id, UserAddressesRequest userAddressesRequest) {

        Users user = usersRepository.findById(USER_ID).orElseThrow(() -> new EntityNotFoundException("User not found"));

        UserAddresses userAddress = userAddressesRepository.findByIdAndUserId(id, user)
                .orElseThrow(() -> new EntityNotFoundException("User Address not found"));

        if (userAddressesRequest.getAddress() != null) {

            userAddress.setAddress(userAddressesRequest.getAddress());
        }

        return new UserAddressesDTO(userAddressesRepository.save(userAddress));
    }

    public String delete(Long id) {

        Users user = usersRepository.findById(USER_ID).orElseThrow(() -> new EntityNotFoundException("User not found"));

        UserAddresses userAddress = userAddressesRepository.findByIdAndUserId(id, user)
                .orElseThrow(() -> new EntityNotFoundException("User Address not found"));

        userAddressesRepository.delete(userAddress);

        return "User Addresses Deleted";
    }
}
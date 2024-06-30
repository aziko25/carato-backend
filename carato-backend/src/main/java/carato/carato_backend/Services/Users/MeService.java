package carato.carato_backend.Services.Users;

import carato.carato_backend.DTOs.Post_Put_Requests.Users.UsersRequest;
import carato.carato_backend.DTOs.ReturnDTOs.UsersDTO;
import carato.carato_backend.Models.Users.Users;
import carato.carato_backend.Repositories.Users.UsersRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static carato.carato_backend.Configurations.JWT.AuthorizationMethods.USER_ID;

@Service
@RequiredArgsConstructor
public class MeService {

    private final UsersRepository usersRepository;

    public UsersDTO getMe() {

        return new UsersDTO(usersRepository.findById(USER_ID).orElseThrow(() -> new EntityNotFoundException("User not found")));
    }

    public UsersDTO update(UsersRequest usersRequest) {

        Users user = usersRepository.findById(USER_ID).orElseThrow(() -> new EntityNotFoundException("User not found"));

        Optional.ofNullable(usersRequest.getUsername()).ifPresent(user::setUsername);
        Optional.ofNullable(usersRequest.getPassword()).ifPresent(user::setPassword);
        Optional.ofNullable(usersRequest.getEmail()).ifPresent(user::setEmail);
        Optional.ofNullable(usersRequest.getPhone()).ifPresent(user::setPhone);
        Optional.ofNullable(usersRequest.getFullName()).ifPresent(user::setFullName);

        return new UsersDTO(usersRepository.save(user));
    }
}
package carato.carato_backend.Services.Users;

import carato.carato_backend.DTOs.Filters.Users.UserFilters;
import carato.carato_backend.DTOs.Post_Put_Requests.Users.UsersRequest;
import carato.carato_backend.DTOs.ReturnDTOs.UsersDTO;
import carato.carato_backend.Models.Users.Users;
import carato.carato_backend.Repositories.Users.UsersRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;

    @Value("${pageSize}")
    private Integer pageSize;

    public UsersDTO create(UsersRequest userRequest) {

        if (!userRequest.getRole().equalsIgnoreCase("USER")
                && !userRequest.getRole().equalsIgnoreCase("ADMIN")) {

            throw new IllegalArgumentException("User Role Should Be Either User Or Admin");
        }

        boolean existsByUsername = usersRepository.existsByUsername(userRequest.getUsername());

        if (existsByUsername) {

            throw new EntityExistsException("This username already exists");
        }

        Users user = Users.builder()
                .username(userRequest.getUsername())
                .email(userRequest.getEmail())
                .phone(userRequest.getPhone())
                .password(userRequest.getPassword())
                .fullName(userRequest.getFullName())
                .role(userRequest.getRole().toUpperCase())
                .registrationTime(LocalDateTime.now())
                .build();

        return new UsersDTO(usersRepository.save(user));
    }

    public Page<UsersDTO> getAll(Integer page, UserFilters filter) {

        Pageable pageable = PageRequest.of(page - 1, pageSize);

        if (filter != null && filter.getName() != null) {

            return usersRepository.searchUsers(filter.getName(), pageable).map(UsersDTO::new);
        }

        return usersRepository.findAll(pageable).map(UsersDTO::new);
    }

    public UsersDTO getById(Long id) {

        return usersRepository.findById(id).map(UsersDTO::new).orElseThrow(() -> new EntityNotFoundException("User Not Found"));
    }

    public UsersDTO update(Long id, UsersRequest userRequest) {

        Users user = usersRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User Not Found"));

        if (userRequest != null && userRequest.getUsername() != null && !userRequest.getUsername().equals(user.getUsername())) {

            boolean existsByUsername = usersRepository.existsByUsername(userRequest.getUsername());

            if (existsByUsername) {

                throw new EntityExistsException("This username already exists");
            }

            user.setUsername(userRequest.getUsername());
        }

        assert userRequest != null;
        Optional.ofNullable(userRequest.getEmail()).ifPresent(user::setEmail);
        Optional.ofNullable(userRequest.getPhone()).ifPresent(user::setPhone);
        Optional.ofNullable(userRequest.getFullName()).ifPresent(user::setFullName);

        if (userRequest.getRole() != null && !userRequest.getRole().equalsIgnoreCase("USER")
            && !userRequest.getRole().equalsIgnoreCase("ADMIN")) {

            throw new IllegalArgumentException("Role Should Be Either User Or Admin");
        }
        else if (userRequest.getRole() != null) {

            user.setRole(userRequest.getRole().toUpperCase());
        }

        return new UsersDTO(usersRepository.save(user));
    }

    public String delete(Long id) {

        Users user = usersRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User Not Found"));

        usersRepository.delete(user);

        return "Successfully deleted";
    }
}
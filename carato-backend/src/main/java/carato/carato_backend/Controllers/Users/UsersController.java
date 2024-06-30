package carato.carato_backend.Controllers.Users;

import carato.carato_backend.Configurations.JWT.Authorization;
import carato.carato_backend.DTOs.Filters.Users.UserFilters;
import carato.carato_backend.DTOs.Post_Put_Requests.Users.UsersRequest;
import carato.carato_backend.Services.Users.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admins/users")
@CrossOrigin(maxAge = 3600)
@AllArgsConstructor
public class UsersController {

    private final UsersService usersService;

    @Authorization(requiredRoles = {"ADMIN"})
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody UsersRequest userRequest) {

        return new ResponseEntity<>(usersService.create(userRequest), HttpStatus.CREATED);
    }

    @Authorization(requiredRoles = {"ADMIN"})
    @PostMapping("/all")
    public ResponseEntity<?> getAll(@RequestParam Integer page, @RequestBody(required = false) UserFilters filter) {

        return ResponseEntity.ok(usersService.getAll(page, filter));
    }

    @Authorization(requiredRoles = {"ADMIN"})
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {

        return ResponseEntity.ok(usersService.getById(id));
    }

    @Authorization(requiredRoles = {"ADMIN"})
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody UsersRequest userRequest) {

        return ResponseEntity.ok(usersService.update(id, userRequest));
    }

    @Authorization(requiredRoles = {"ADMIN"})
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        return ResponseEntity.ok(usersService.delete(id));
    }
}
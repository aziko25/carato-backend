package carato.carato_backend.Controllers.Users;

import carato.carato_backend.Configurations.JWT.Authorization;
import carato.carato_backend.DTOs.Post_Put_Requests.Users.UserAddressesRequest;
import carato.carato_backend.Services.Users.UserAddressesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-addresses")
@CrossOrigin(maxAge = 3600)
@RequiredArgsConstructor
public class UserAddressesController {

    private final UserAddressesService userAddressesService;

    @Authorization(requiredRoles = {"ADMIN", "USER"})
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody UserAddressesRequest userAddressesRequest) {

        return new ResponseEntity<>(userAddressesService.create(userAddressesRequest), HttpStatus.CREATED);
    }

    @Authorization(requiredRoles = {"ADMIN", "USER"})
    @PostMapping("/all")
    public ResponseEntity<?> getAll(@RequestParam Integer page) {

        return ResponseEntity.ok(userAddressesService.getAll(page));
    }

    @Authorization(requiredRoles = {"ADMIN", "USER"})
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {

        return ResponseEntity.ok(userAddressesService.getById(id));
    }

    @Authorization(requiredRoles = {"ADMIN", "USER"})
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody UserAddressesRequest userAddressesRequest) {

        return ResponseEntity.ok(userAddressesService.update(id, userAddressesRequest));
    }

    @Authorization(requiredRoles = {"ADMIN", "USER"})
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        return ResponseEntity.ok(userAddressesService.delete(id));
    }
}
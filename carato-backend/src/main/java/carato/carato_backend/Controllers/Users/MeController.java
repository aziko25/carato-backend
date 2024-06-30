package carato.carato_backend.Controllers.Users;

import carato.carato_backend.Configurations.JWT.Authorization;
import carato.carato_backend.DTOs.Post_Put_Requests.Users.UsersRequest;
import carato.carato_backend.Services.Users.MeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/me/users")
@CrossOrigin(maxAge = 3600)
@RequiredArgsConstructor
public class MeController {

    private final MeService meService;

    @Authorization(requiredRoles = {"ADMIN", "USER"})
    @GetMapping
    public ResponseEntity<?> getMe() {

        return ResponseEntity.ok(meService.getMe());
    }

    @Authorization(requiredRoles = {"ADMIN", "USER"})
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody UsersRequest userRequest) {

        return ResponseEntity.ok(meService.update(userRequest));
    }
}
package carato.carato_backend.Controllers.Products;

import carato.carato_backend.Configurations.JWT.Authorization;
import carato.carato_backend.DTOs.Filters.Products.MetalFilters;
import carato.carato_backend.DTOs.Post_Put_Requests.Products.MetalsRequest;
import carato.carato_backend.Services.Products.MetalsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/metals")
@CrossOrigin(maxAge = 3600)
@RequiredArgsConstructor
public class MetalsController {

    private final MetalsService metalsService;

    @Authorization(requiredRoles = {"ADMIN"})
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody MetalsRequest metalRequest) {

        return new ResponseEntity<>(metalsService.create(metalRequest), HttpStatus.CREATED);
    }

    @PostMapping("/all")
    public ResponseEntity<?> getAll(@RequestParam Integer page, @RequestBody(required = false) MetalFilters filter) {

        return ResponseEntity.ok(metalsService.getAll(page, filter));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {

        return ResponseEntity.ok(metalsService.getById(id));
    }

    @Authorization(requiredRoles = {"ADMIN"})
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody MetalsRequest metalRequest) {

        return ResponseEntity.ok(metalsService.update(id, metalRequest));
    }

    @Authorization(requiredRoles = {"ADMIN"})
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        return ResponseEntity.ok(metalsService.delete(id));
    }
}
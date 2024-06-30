package carato.carato_backend.Controllers.Products;

import carato.carato_backend.Configurations.JWT.Authorization;
import carato.carato_backend.DTOs.Filters.Products.SizeFilters;
import carato.carato_backend.DTOs.Post_Put_Requests.Products.SizesRequest;
import carato.carato_backend.Services.Products.SizesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sizes")
@CrossOrigin(maxAge = 3600)
@RequiredArgsConstructor
public class SizesController {

    private final SizesService sizesService;

    @Authorization(requiredRoles = {"ADMIN"})
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody SizesRequest sizesRequest) {

        return new ResponseEntity<>(sizesService.create(sizesRequest), HttpStatus.CREATED);
    }

    @PostMapping("/all")
    public ResponseEntity<?> getAll(@RequestParam Integer page, @RequestBody(required = false) SizeFilters filter) {

        return new ResponseEntity<>(sizesService.getAll(page, filter), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {

        return ResponseEntity.ok(sizesService.getById(id));
    }

    @Authorization(requiredRoles = {"ADMIN"})
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody SizesRequest sizesRequest) {

        return ResponseEntity.ok(sizesService.update(id, sizesRequest));
    }

    @Authorization(requiredRoles = {"ADMIN"})
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        return ResponseEntity.ok(sizesService.delete(id));
    }
}
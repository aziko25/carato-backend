package carato.carato_backend.Controllers.Products;

import carato.carato_backend.Configurations.JWT.Authorization;
import carato.carato_backend.DTOs.Filters.Products.BrandFilters;
import carato.carato_backend.DTOs.Post_Put_Requests.Products.BrandsRequest;
import carato.carato_backend.Services.Products.BrandsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/brands")
@CrossOrigin(maxAge = 3600)
@RequiredArgsConstructor
public class BrandsController {

    private final BrandsService brandsService;

    @Authorization(requiredRoles = {"ADMIN"})
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody BrandsRequest brandRequest) {

        return new ResponseEntity<>(brandsService.create(brandRequest), HttpStatus.CREATED);
    }

    @PostMapping("/all")
    public ResponseEntity<?> getAll(@RequestParam Integer page, @RequestBody(required = false) BrandFilters filter) {

        return ResponseEntity.ok(brandsService.getAll(page, filter));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {

        return ResponseEntity.ok(brandsService.getById(id));
    }

    @Authorization(requiredRoles = {"ADMIN"})
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody BrandsRequest brandRequest) {

        return ResponseEntity.ok(brandsService.update(id, brandRequest));
    }

    @Authorization(requiredRoles = {"ADMIN"})
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        return ResponseEntity.ok(brandsService.delete(id));
    }
}
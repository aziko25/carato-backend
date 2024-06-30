package carato.carato_backend.Controllers.Products;

import carato.carato_backend.Configurations.JWT.Authorization;
import carato.carato_backend.DTOs.Filters.Products.SizeCategoryFilters;
import carato.carato_backend.DTOs.Post_Put_Requests.Products.SizeCategoriesRequest;
import carato.carato_backend.Services.Products.SizeCategoriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/size-categories")
@CrossOrigin(maxAge = 3600)
@RequiredArgsConstructor
public class SizeCategoriesController {

    private final SizeCategoriesService sizeCategoriesService;

    @Authorization(requiredRoles = {"ADMIN"})
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody SizeCategoriesRequest sizeCategoriesRequest) {

        return new ResponseEntity<>(sizeCategoriesService.create(sizeCategoriesRequest), HttpStatus.CREATED);
    }

    @PostMapping("/all")
    public ResponseEntity<?> getAll(@RequestParam Integer page, @RequestBody(required = false) SizeCategoryFilters filter) {

        return ResponseEntity.ok(sizeCategoriesService.getAll(page, filter));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {

        return ResponseEntity.ok(sizeCategoriesService.getById(id));
    }

    @Authorization(requiredRoles = {"ADMIN"})
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody SizeCategoriesRequest sizeCategoriesRequest) {

        return ResponseEntity.ok(sizeCategoriesService.update(id, sizeCategoriesRequest));
    }

    @Authorization(requiredRoles = {"ADMIN"})
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        return ResponseEntity.ok(sizeCategoriesService.delete(id));
    }
}
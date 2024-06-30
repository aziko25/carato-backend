package carato.carato_backend.Controllers.Products;

import carato.carato_backend.Configurations.JWT.Authorization;
import carato.carato_backend.DTOs.Filters.Products.CategoryFilters;
import carato.carato_backend.DTOs.Post_Put_Requests.Products.CategoriesRequest;
import carato.carato_backend.Services.Products.CategoriesService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(maxAge = 3600)
@RequiredArgsConstructor
public class CategoriesController {

    private final CategoriesService categoriesService;

    @Authorization(requiredRoles = {"ADMIN"})
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestParam(value = "media", required = false) MultipartFile media,
                                    @RequestParam("category") String categoryJson) throws JsonProcessingException {

        CategoriesRequest categoryRequest = new ObjectMapper().readValue(categoryJson, CategoriesRequest.class);

        return new ResponseEntity<>(categoriesService.create(media, categoryRequest), HttpStatus.CREATED);
    }

    @PostMapping("/all")
    public ResponseEntity<?> getAll(@RequestParam Integer page, @RequestBody(required = false) CategoryFilters filter) {

        return ResponseEntity.ok(categoriesService.getAll(page, filter));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {

        return ResponseEntity.ok(categoriesService.getById(id));
    }

    @Authorization(requiredRoles = {"ADMIN"})
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id,
                                    @RequestParam(value = "media", required = false) MultipartFile media,
                                    @RequestParam("category") String categoryJson) throws JsonProcessingException {

        CategoriesRequest categoryRequest = new ObjectMapper().readValue(categoryJson, CategoriesRequest.class);

        return ResponseEntity.ok(categoriesService.update(id, media, categoryRequest));
    }

    @Authorization(requiredRoles = {"ADMIN"})
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {

        return ResponseEntity.ok(categoriesService.delete(id));
    }
}
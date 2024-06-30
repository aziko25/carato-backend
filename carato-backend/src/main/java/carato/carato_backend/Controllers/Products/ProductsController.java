package carato.carato_backend.Controllers.Products;

import carato.carato_backend.Configurations.JWT.Authorization;
import carato.carato_backend.DTOs.Filters.Products.ProductFilters;
import carato.carato_backend.DTOs.Post_Put_Requests.Products.ProductsRequest;
import carato.carato_backend.Services.Products.ProductsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(maxAge = 3600)
@RequiredArgsConstructor
public class ProductsController {

    private final ProductsService productsService;

    @Authorization(requiredRoles = {"ADMIN"})
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestParam(value = "media", required = false) List<MultipartFile> media,
                                    @RequestParam("product") String productJson) throws JsonProcessingException {

        ProductsRequest productRequest = new ObjectMapper().readValue(productJson, ProductsRequest.class);

        return new ResponseEntity<>(productsService.create(media, productRequest), HttpStatus.CREATED);
    }

    @PostMapping("/all")
    public ResponseEntity<?> getAll(@RequestBody(required = false) ProductFilters filter,
                                    @RequestParam int page) {

        return ResponseEntity.ok(productsService.getAll(page, filter));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {

        return ResponseEntity.ok(productsService.getById(id));
    }

    @Authorization(requiredRoles = {"ADMIN"})
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id,
                                    @RequestParam(value = "media", required = false) List<MultipartFile> media,
                                    @RequestParam("product") String productJson) throws JsonProcessingException {

        ProductsRequest productRequest = new ObjectMapper().readValue(productJson, ProductsRequest.class);

        return ResponseEntity.ok(productsService.update(id, media, productRequest));
    }

    @Authorization(requiredRoles = {"ADMIN"})
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {

        return ResponseEntity.ok(productsService.delete(id));
    }
}
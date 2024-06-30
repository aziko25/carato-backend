package carato.carato_backend.Controllers;

import carato.carato_backend.Configurations.JWT.Authorization;
import carato.carato_backend.DTOs.Filters.BannerFilters;
import carato.carato_backend.DTOs.Post_Put_Requests.BannersRequest;
import carato.carato_backend.Services.BannersService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/banners")
@CrossOrigin(maxAge = 3600)
@RequiredArgsConstructor
public class BannersController {

    private final BannersService bannersService;

    @Authorization(requiredRoles = {"ADMIN"})
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestParam(value = "media", required = false) MultipartFile media,
                                    @RequestParam("banner") String bannerJson) throws JsonProcessingException {

        BannersRequest bannerRequest = new ObjectMapper().readValue(bannerJson, BannersRequest.class);

        return new ResponseEntity<>(bannersService.create(media, bannerRequest), HttpStatus.CREATED);
    }

    @PostMapping("/all")
    public ResponseEntity<?> getAll(@RequestParam Integer page, @RequestBody(required = false) BannerFilters filter) {

        return ResponseEntity.ok(bannersService.getAll(page, filter));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {

        return ResponseEntity.ok(bannersService.getById(id));
    }

    @Authorization(requiredRoles = {"ADMIN"})
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id,
                                    @RequestParam(value = "image", required = false) MultipartFile image,
                                    @RequestParam("banner") String bannerJson) throws JsonProcessingException {

        BannersRequest bannerRequest = new ObjectMapper().readValue(bannerJson, BannersRequest.class);

        return ResponseEntity.ok(bannersService.update(id, image, bannerRequest));
    }

    @Authorization(requiredRoles = {"ADMIN"})
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {

        return ResponseEntity.ok(bannersService.delete(id));
    }
}
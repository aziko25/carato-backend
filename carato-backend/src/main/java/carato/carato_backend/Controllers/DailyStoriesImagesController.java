package carato.carato_backend.Controllers;

import carato.carato_backend.Configurations.JWT.Authorization;
import carato.carato_backend.Services.DailyStoriesImages.DailyStoriesImagesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/daily-stories")
@CrossOrigin(maxAge = 3600)
@RequiredArgsConstructor
public class DailyStoriesImagesController {

    private final DailyStoriesImagesService dailyStoriesImagesService;

    @Authorization(requiredRoles = {"ADMIN"})
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestParam List<MultipartFile> media) {

        return ResponseEntity.ok(dailyStoriesImagesService.create(media));
    }

    @PostMapping("/all")
    public ResponseEntity<?> getAll() {

        return ResponseEntity.ok(dailyStoriesImagesService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {

        return ResponseEntity.ok(dailyStoriesImagesService.getById(id));
    }
}
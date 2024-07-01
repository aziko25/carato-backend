package carato.carato_backend.Controllers.Payments;

import carato.carato_backend.Services.Payments.ClickService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/click")
@CrossOrigin(maxAge = 3600)
@RequiredArgsConstructor
public class ClickController {

    private final ClickService clickService;

    @PostMapping("/prepare-order")
    public ResponseEntity<?> prepareOrder(@RequestParam Map<String, String> body) {

        return ResponseEntity.ok(clickService.prepareOrder(body));
    }

    @PostMapping("/complete-order")
    public ResponseEntity<?> completeOrder(@RequestParam Map<String, String> body) {

        return ResponseEntity.ok(clickService.completeOrder(body));
    }
}
package carato.carato_backend.Controllers.Orders;

import carato.carato_backend.Configurations.JWT.Authorization;
import carato.carato_backend.DTOs.Filters.Orders.OrderFilters;
import carato.carato_backend.DTOs.Post_Put_Requests.Orders.OrdersRequest;
import carato.carato_backend.Services.Orders.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(maxAge = 3600)
@RequiredArgsConstructor
public class OrdersController {

    private final OrdersService ordersService;

    @Authorization(requiredRoles = {"ADMIN", "USER"})
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody OrdersRequest orderRequest) {

        return new ResponseEntity<>(ordersService.create(orderRequest), HttpStatus.CREATED);
    }

    @Authorization(requiredRoles = {"ADMIN"})
    @PostMapping("/all")
    public ResponseEntity<?> getAll(@RequestParam Integer page, @RequestBody(required = false) OrderFilters filter) {

        return ResponseEntity.ok(ordersService.getAll(page, filter));
    }

    @Authorization(requiredRoles = {"ADMIN", "USER"})
    @PostMapping("/allMy")
    public ResponseEntity<?> getAllMy(@RequestParam Integer page, @RequestBody(required = false) OrderFilters filter) {

        return ResponseEntity.ok(ordersService.getAllMy(page, filter));
    }

    @Authorization(requiredRoles = {"ADMIN"})
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody OrdersRequest orderRequest) {

        return ResponseEntity.ok(ordersService.update(id, orderRequest));
    }

    @Authorization(requiredRoles = {"ADMIN"})
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        return ResponseEntity.ok(ordersService.delete(id));
    }
}
package carato.carato_backend.DTOs.Filters.Orders;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class OrderFilters {

    private LocalDate startTime;
    private LocalDate endTime;
    private Boolean isPreOrder;
}
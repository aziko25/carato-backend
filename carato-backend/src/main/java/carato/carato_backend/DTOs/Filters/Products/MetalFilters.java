package carato.carato_backend.DTOs.Filters.Products;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MetalFilters {

    private Double priceMin;
    private Double priceMax;
    private Boolean firstCheap;
    private Boolean firstExpensive;
    private Boolean firstFinenessHigh;
    private Boolean firstFinenessLow;
    private String name;
}
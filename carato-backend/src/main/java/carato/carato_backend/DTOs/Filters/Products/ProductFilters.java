package carato.carato_backend.DTOs.Filters.Products;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductFilters {

    private Double priceMin;
    private Double priceMax;

    private Boolean firstCheap;
    private Boolean firstExpensive;

    private String name;
    private String style;
    private Long categoryId;

    private List<Long> brandsList;
    private List<Long> metalsList;
    private List<Long> sizeCategoriesList;
}
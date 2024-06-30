package carato.carato_backend.DTOs.Post_Put_Requests.Products;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductsRequest {

    private String name;
    private String description;
    private Double price;
    private Long brandId;
    private String style;
    private Long categoryId;

    private List<Long> metalsList;
    private List<Products_Sizes_Request> sizesList;
}
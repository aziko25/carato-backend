package carato.carato_backend.DTOs.Post_Put_Requests.Products;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Products_Sizes_Request {

    private Long sizeId;
    private Double price;
    private Integer quantity;
}
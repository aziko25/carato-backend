package carato.carato_backend.DTOs.Post_Put_Requests.Products;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MetalsRequest {

    private String name;
    private Integer metalFineness;
    private String description;
    private Double price;
}
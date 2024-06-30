package carato.carato_backend.DTOs.Post_Put_Requests.Products;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoriesRequest {

    private String name;
    private String description;
}
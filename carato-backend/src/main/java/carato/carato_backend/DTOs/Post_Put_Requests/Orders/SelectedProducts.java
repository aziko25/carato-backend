package carato.carato_backend.DTOs.Post_Put_Requests.Orders;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SelectedProducts {
    
    private Long productId;
    private Long sizeId;
    private Integer quantity;
}

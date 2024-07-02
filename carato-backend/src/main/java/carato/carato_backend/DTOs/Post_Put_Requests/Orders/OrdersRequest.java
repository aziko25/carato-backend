package carato.carato_backend.DTOs.Post_Put_Requests.Orders;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrdersRequest {

    private Boolean isPreOrder;
    private String deliveryType;
    private String paymentType;
    private String phone;
    private String email;
    private String comment;
    private Boolean isPaymentDone;
    private String returnUrl;

    private Long addressId;

    private List<SelectedProducts> selectedProductsList;
}


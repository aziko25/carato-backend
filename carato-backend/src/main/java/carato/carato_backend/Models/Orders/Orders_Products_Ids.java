package carato.carato_backend.Models.Orders;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Orders_Products_Ids implements Serializable {

    private Long orderId;
    private Long productId;
    private Long userId;

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Orders_Products_Ids that = (Orders_Products_Ids) o;

        return Objects.equals(orderId, that.orderId) &&
                Objects.equals(productId, that.productId) &&
                Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(orderId, productId, userId);
    }
}
package carato.carato_backend.Models.Orders;

import carato.carato_backend.Models.Products.Products;
import carato.carato_backend.Models.Products.Sizes;
import carato.carato_backend.Models.Users.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(Orders_Products_Ids.class)
@Table(name = "orders_products")
public class Orders_Products {

    @Id
    @ManyToOne
    private Orders orderId;

    @Id
    @ManyToOne
    private Products productId;

    @Id
    @ManyToOne
    private Users userId;

    @ManyToOne
    @JoinColumn(name = "product_size_id")
    private Sizes sizeId;

    private String productName;
    private String sizeName;

    private Integer quantity;
    private Double price;
}
package carato.carato_backend.Models.Products.ManyToMany;

import carato.carato_backend.Models.Products.Products;
import carato.carato_backend.Models.Products.Sizes;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "products_sizes")
@IdClass(Products_Sizes_Ids.class)
public class Products_Sizes {

    @Id
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Products productId;

    @Id
    @ManyToOne
    @JoinColumn(name = "size_id")
    private Sizes sizeId;

    private Integer quantity;
    private Double price;
}
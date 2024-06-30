package carato.carato_backend.Models.Products.ManyToMany;

import carato.carato_backend.Models.Products.Metals;
import carato.carato_backend.Models.Products.Products;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "products_metals")
@IdClass(Products_Metals_Ids.class)
public class Products_Metals {

    @Id
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Products productId;

    @Id
    @ManyToOne
    @JoinColumn(name = "metal_id")
    private Metals metalId;
}
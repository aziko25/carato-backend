package carato.carato_backend.Models.Products;

import carato.carato_backend.Models.Products.ManyToMany.Products_Metals;
import carato.carato_backend.Models.Products.ManyToMany.Products_Sizes;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "products")
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdTime;

    @Column(unique = true)
    private String name;

    private String description;
    private Double price;
    private String style;
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Categories categoryId;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brands brandId;

    @OneToMany(mappedBy = "productId")
    private List<Products_Metals> productsMetals;

    @OneToMany(mappedBy = "productId")
    private List<Products_Sizes> productsSizes;

    @OneToMany(mappedBy = "productId")
    private List<Product_Images> productImages;
}
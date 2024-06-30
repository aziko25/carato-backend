package carato.carato_backend.DTOs.ReturnDTOs;

import carato.carato_backend.Models.Products.ManyToMany.Products_Metals;
import carato.carato_backend.Models.Products.ManyToMany.Products_Sizes;
import carato.carato_backend.Models.Products.Product_Images;
import carato.carato_backend.Models.Products.Products;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductsDTO {

    private Long id;
    private String name;

    @JsonFormat(shape = STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createdTime;

    private String description;
    private Double price;
    private String previewImageUrl;
    private Long brandId;
    private String brandName;
    private String style;
    private Long categoryId;
    private String categoryName;
    private List<Map<String, Object>> metalsList;
    private List<Map<String, Object>> sizesList;
    private List<Map<String, Object>> imagesList;

    public ProductsDTO(Products product) {

        id = product.getId();
        name = product.getName();
        createdTime = product.getCreatedTime();
        description = product.getDescription();
        price = product.getPrice();
        previewImageUrl = product.getImageUrl();

        if (product.getBrandId() != null) {

            brandId = product.getBrandId().getId();
            brandName = product.getBrandId().getName();
        }

        style = product.getStyle();

        if (product.getCategoryId() != null) {

            categoryId = product.getCategoryId().getId();
            categoryName = product.getCategoryId().getName();
        }

        Set<Products_Metals> metals = new HashSet<>(product.getProductsMetals());
        metalsList = metals.stream()
                .sorted(Comparator.comparing((Products_Metals pm) -> pm.getMetalId().getId())
                        .thenComparing(pm -> pm.getMetalId().getPrice()))
                .map(metal -> {

                    Map<String, Object> map = new LinkedHashMap<>();
                    map.put("id", metal.getMetalId().getId());
                    map.put("name", metal.getMetalId().getName());
                    map.put("metalFineness", metal.getMetalId().getMetalFineness());

                    return map;
                }).collect(Collectors.toList());


        Set<Products_Sizes> sizes = new HashSet<>(product.getProductsSizes());
        sizesList = sizes.stream()
                .sorted(Comparator.comparing((Products_Sizes ps) -> ps.getSizeId().getId())
                        .thenComparing(ps -> ps.getSizeId().getName()))
                .map(size -> {

                    Map<String, Object> map = new LinkedHashMap<>();

                    map.put("id", size.getSizeId().getId());
                    map.put("name", size.getSizeId().getName());
                    map.put("price", size.getPrice());
                    map.put("quantity", size.getQuantity());
                    map.put("sizeCategoryId", size.getSizeId().getSizeCategoryId().getId());
                    map.put("sizeCategoryName", size.getSizeId().getSizeCategoryId().getName());

                    return map;
                }).collect(Collectors.toList());

        Set<Product_Images> images = new HashSet<>(product.getProductImages());
        imagesList = images.stream()
                .sorted(Comparator.comparing(Product_Images::getId))
                .map(image -> {

                    Map<String, Object> map = new LinkedHashMap<>();

                    map.put("id", image.getId());
                    map.put("imageUrl", image.getImage());

                    return map;
                }).collect(Collectors.toList());
    }
}
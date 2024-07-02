package carato.carato_backend.DTOs.ReturnDTOs;

import carato.carato_backend.Models.Orders.Orders;
import carato.carato_backend.Models.Orders.Orders_Products;
import carato.carato_backend.Models.Products.Product_Images;
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
public class OrdersDTO {

    private Long id;

    private String payTransactionUrl;
    private Boolean isPreOrder;
    private String deliveryType;

    @JsonFormat(shape = STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createdTime;

    private String address;
    private Double totalSum;
    private String phone;
    private String email;
    private String comment;
    private Boolean isPaymentDone;

    private Long userId;
    private String username;

    private List<Map<String, Object>> productsList;

    public OrdersDTO(Orders order) {

        id = order.getId();
        payTransactionUrl = order.getPayTransactionUrl();
        isPreOrder = order.getIsPreOrder();
        deliveryType = order.getDeliveryType();
        createdTime = order.getCreatedTime();
        address = order.getAddress();
        totalSum = order.getTotalSum();
        phone = order.getPhone();
        email = order.getEmail();
        comment = order.getComment();
        isPaymentDone = order.getIsPaymentDone();
        userId = order.getUserId().getId();
        username = order.getUserId().getUsername();

        Set<Orders_Products> products = new HashSet<>(order.getOrdersProductsList());
        System.out.println(products.size());
        productsList = products.stream()
                .map(product -> {

                    System.out.println(product.getProductName());
                    Map<String, Object> map = new LinkedHashMap<>();

                    if (product.getProductId() != null) {

                        map.put("id", product.getProductId().getId());
                        map.put("style", product.getProductId().getStyle());
                        map.put("previewImageUrl", product.getProductId().getImageUrl());

                        if (product.getProductId().getBrandId() != null) {

                            map.put("brandId", product.getProductId().getBrandId().getId());
                            map.put("brandName", product.getProductId().getBrandId().getName());
                        }

                        if (product.getProductId().getCategoryId() != null) {

                            map.put("categoryId", product.getProductId().getCategoryId().getId());
                            map.put("categoryName", product.getProductId().getCategoryId().getName());
                        }

                        if (product.getProductId().getProductImages() != null && !product.getProductId().getProductImages().isEmpty()) {

                            Set<Product_Images> images = new HashSet<>(product.getProductId().getProductImages());
                            List<Map<String, Object>> imagesList = images.stream()
                                    .sorted(Comparator.comparing(Product_Images::getId))
                                    .map(image -> {

                                        Map<String, Object> imagesMap = new LinkedHashMap<>();

                                        imagesMap.put("id", image.getId());
                                        imagesMap.put("imageUrl", image.getImage());

                                        return imagesMap;
                                    }).toList();
                            map.put("imagesList", imagesList);
                        }

                    }

                    map.put("name", product.getProductName());
                    map.put("price", product.getPrice());
                    map.put("quantity", product.getQuantity());
                    map.put("size", product.getSizeName());

                    return map;
                }).collect(Collectors.toList());
    }
}
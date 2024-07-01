package carato.carato_backend.Repositories.Orders;

import carato.carato_backend.Models.Orders.Orders;
import carato.carato_backend.Models.Orders.Orders_Products;
import carato.carato_backend.Models.Products.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Orders_Products_Repository extends JpaRepository<Orders_Products, Long> {

    List<Orders_Products> findAllByOrderId(Orders orderId);

    List<Orders_Products> findAllByProductId(Products product);
}
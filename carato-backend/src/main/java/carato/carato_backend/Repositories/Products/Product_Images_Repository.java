package carato.carato_backend.Repositories.Products;

import carato.carato_backend.Models.Products.Product_Images;
import carato.carato_backend.Models.Products.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Product_Images_Repository extends JpaRepository<Product_Images, Long> {

    List<Product_Images> findAllByProductId(Products productId);
}
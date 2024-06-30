package carato.carato_backend.Repositories.Products;

import carato.carato_backend.Models.Products.Brands;
import carato.carato_backend.Models.Products.Categories;
import carato.carato_backend.Models.Products.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Long> {

    boolean existsByName(String name);

    List<Products> findAllByCategoryId(Categories category);

    @Query("SELECT p FROM Products p " +
            "LEFT JOIN p.categoryId c " +
            "LEFT JOIN p.productsMetals pm " +
            "LEFT JOIN pm.metalId m " +
            "LEFT JOIN p.productsSizes ps " +
            "LEFT JOIN ps.sizeId s " +
            "LEFT JOIN p.brandId b " +
            "WHERE (:name IS NULL OR p.name ILIKE %:name%) " +
            "AND (:style IS NULL OR p.style ILIKE %:style%) " +
            "AND (:categoryId IS NULL OR c.id = :categoryId) " +
            "AND (:priceMin IS NULL OR p.price >= :priceMin) " +
            "AND (:priceMax IS NULL OR p.price <= :priceMax) " +
            "AND (:metalsList IS NULL OR m.id IN :metalsList) " +
            "AND (:sizesList IS NULL OR s.id IN :sizesList) " +
            "AND (:brandsList IS NULL OR b.id IN :brandsList) " +
            "ORDER BY " +
            "CASE WHEN :firstCheap = true THEN p.price END ASC, " +
            "CASE WHEN :firstExpensive = true THEN p.price END DESC")
    Page<Products> findAllByFilters(
            @Param("priceMin") Double priceMin,
            @Param("priceMax") Double priceMax,
            @Param("firstCheap") Boolean firstCheap,
            @Param("firstExpensive") Boolean firstExpensive,
            @Param("name") String name,
            @Param("style") String style,
            @Param("categoryId") Long categoryId,
            @Param("metalsList") List<Long> metalsList,
            @Param("sizesList") List<Long> sizesList,
            @Param("brandsList") List<Long> brandsList,
            Pageable pageable);

    List<Products> findAllByBrandId(Brands brand);
}
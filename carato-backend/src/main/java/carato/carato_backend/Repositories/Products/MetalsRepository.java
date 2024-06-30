package carato.carato_backend.Repositories.Products;

import carato.carato_backend.Models.Products.Metals;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MetalsRepository extends JpaRepository<Metals, Long> {

    @Query("SELECT m FROM Metals m " +
            "WHERE (:name IS NULL OR m.name ILIKE %:name%) " +
            "AND (:priceMin IS NULL OR m.price >= :priceMin) " +
            "AND (:priceMax IS NULL OR m.price <= :priceMax) " +
            "ORDER BY " +
            "CASE WHEN :firstCheap = true THEN m.price END ASC, " +
            "CASE WHEN :firstExpensive = true THEN m.price END DESC, " +
            "CASE WHEN :firstFinenessHigh = true THEN m.metalFineness END DESC, " +
            "CASE WHEN :firstFinenessLow = true THEN m.metalFineness END ASC")
    Page<Metals> findAllByFilters(
            @Param("priceMin") Double priceMin,
            @Param("priceMax") Double priceMax,
            @Param("firstCheap") Boolean firstCheap,
            @Param("firstExpensive") Boolean firstExpensive,
            @Param("firstFinenessHigh") Boolean firstFinenessHigh,
            @Param("firstFinenessLow") Boolean firstFinenessLow,
            @Param("name") String name,
            Pageable pageable);
}
package carato.carato_backend.Repositories.Products;

import carato.carato_backend.DTOs.Filters.Products.SizeFilters;
import carato.carato_backend.Models.Products.SizeCategories;
import carato.carato_backend.Models.Products.Sizes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SizesRepository extends JpaRepository<Sizes, Long> {

    boolean existsByName(String name);

    @Query("SELECT s FROM Sizes s " +
            "WHERE (:#{#filters.name} IS NULL OR s.name ILIKE %:#{#filters.name}%) " +
            "AND (:#{#filters.sizeCategoryId} IS NULL OR s.sizeCategoryId.id = :#{#filters.sizeCategoryId})")
    Page<Sizes> findAllByFilters(@Param("filters") SizeFilters filters, Pageable pageable);

    List<Sizes> findAllBySizeCategoryId(SizeCategories sizeCategories);

    List<Sizes> findAllBySizeCategoryIdIn(List<SizeCategories> sizeCategoriesList);
}
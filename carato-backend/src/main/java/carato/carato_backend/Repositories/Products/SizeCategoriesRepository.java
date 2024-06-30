package carato.carato_backend.Repositories.Products;

import carato.carato_backend.Models.Products.SizeCategories;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SizeCategoriesRepository extends JpaRepository<SizeCategories, Long> {

    boolean existsByName(String name);

    Page<SizeCategories> findAllByNameLikeIgnoreCase(String name, Pageable pageable);
}
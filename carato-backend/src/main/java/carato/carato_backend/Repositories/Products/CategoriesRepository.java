package carato.carato_backend.Repositories.Products;

import carato.carato_backend.Models.Products.Categories;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, Long> {

    boolean existsByName(String name);

    Page<Categories> findAllByNameLikeIgnoreCase(String name, Pageable pageable);
}
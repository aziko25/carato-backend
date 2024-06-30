package carato.carato_backend.Repositories.Products;

import carato.carato_backend.Models.Products.Brands;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandsRepository extends JpaRepository<Brands, Long> {

    boolean existsByName(String name);

    Page<Brands> findAllByNameLikeIgnoreCase(String name, Pageable pageable);
}
package carato.carato_backend.Repositories;

import carato.carato_backend.Models.Banners;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BannersRepository extends JpaRepository<Banners, Long> {

    boolean existsByName(String name);

    @Query("SELECT b FROM Banners b WHERE " +
            "(:name IS NULL OR b.name ILIKE %:name%) AND " +
            "(:isActive IS NULL OR b.isActive = :isActive)")
    Page<Banners> findAllByFilters(@Param("name") String name, @Param("isActive") Boolean isActive, Pageable pageable);
}
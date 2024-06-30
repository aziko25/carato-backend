package carato.carato_backend.Repositories.Products.ManyToMany;

import carato.carato_backend.Models.Products.ManyToMany.Products_Sizes;
import carato.carato_backend.Models.Products.ManyToMany.Products_Sizes_Ids;
import carato.carato_backend.Models.Products.Sizes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Products_Sizes_Repository extends JpaRepository<Products_Sizes, Products_Sizes_Ids> {

    List<Products_Sizes> findAllBySizeId(Sizes size);
}
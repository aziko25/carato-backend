package carato.carato_backend.Repositories.Products.ManyToMany;

import carato.carato_backend.Models.Products.ManyToMany.Products_Metals;
import carato.carato_backend.Models.Products.ManyToMany.Products_Metals_Ids;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Products_Metals_Repository extends JpaRepository<Products_Metals, Products_Metals_Ids> {
}
package carato.carato_backend.Models.Products.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Products_Metals_Ids implements Serializable {

    private Long productId;
    private Long metalId;

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Products_Metals_Ids that = (Products_Metals_Ids) o;

        return Objects.equals(productId, that.productId) &&
                Objects.equals(metalId, that.metalId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(productId, metalId);
    }
}

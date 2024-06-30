package carato.carato_backend.Models.Products.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Products_Sizes_Ids implements Serializable {

    private Long productId;
    private Long sizeId;

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Products_Sizes_Ids that = (Products_Sizes_Ids) o;

        return Objects.equals(productId, that.productId) &&
                Objects.equals(sizeId, that.sizeId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(productId, sizeId);
    }
}
package carato.carato_backend.DTOs.ReturnDTOs;

import carato.carato_backend.Models.Products.Sizes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SizesDTO {

    private Long id;
    private String name;
    private Long sizeCategoryId;
    private String sizeCategoryName;

    public SizesDTO(Sizes size) {

        id = size.getId();
        name = size.getName();
        sizeCategoryId = size.getSizeCategoryId().getId();
        sizeCategoryName = size.getSizeCategoryId().getName();
    }
}
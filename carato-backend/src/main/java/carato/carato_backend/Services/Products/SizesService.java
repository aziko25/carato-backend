package carato.carato_backend.Services.Products;

import carato.carato_backend.DTOs.Filters.Products.SizeFilters;
import carato.carato_backend.DTOs.Post_Put_Requests.Products.SizesRequest;
import carato.carato_backend.DTOs.ReturnDTOs.SizesDTO;
import carato.carato_backend.Models.Products.ManyToMany.Products_Sizes;
import carato.carato_backend.Models.Products.SizeCategories;
import carato.carato_backend.Models.Products.Sizes;
import carato.carato_backend.Repositories.Products.ManyToMany.Products_Sizes_Repository;
import carato.carato_backend.Repositories.Products.SizeCategoriesRepository;
import carato.carato_backend.Repositories.Products.SizesRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SizesService {

    private final SizesRepository sizesRepository;
    private final SizeCategoriesRepository sizeCategoriesRepository;

    private final Products_Sizes_Repository productsSizesRepository;

    @Value("${pageSize}")
    private Integer pageSize;

    public SizesDTO create(SizesRequest sizesRequest) {

        boolean existsByName = sizesRepository.existsByName(sizesRequest.getName());

        if (existsByName) {

            throw new EntityExistsException("Size already exists");
        }

        SizeCategories sizeCategory = null;

        if (sizesRequest.getSizeCategoryId() != null) {

            sizeCategory = sizeCategoriesRepository.findById(sizesRequest.getSizeCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException("Size Category Not Found"));
        }

        Sizes sizes = Sizes.builder()
                .name(sizesRequest.getName())
                .sizeCategoryId(sizeCategory)
                .build();

        return new SizesDTO(sizesRepository.save(sizes));
    }

    public Page<SizesDTO> getAll(Integer page, SizeFilters filter) {

        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by("name").ascending());

        if (filter != null) {

            Page<Sizes> sizesPage = sizesRepository.findAllByFilters(filter, pageable);

            return sizesPage.map(SizesDTO::new);
        }

        Page<Sizes> sizesPage = sizesRepository.findAll(pageable);

        return sizesPage.map(SizesDTO::new);
    }

    public SizesDTO getById(Long id) {

        return sizesRepository.findById(id).map(SizesDTO::new)
                .orElseThrow(() -> new EntityNotFoundException("Size Not Found"));
    }

    public SizesDTO update(Long id, SizesRequest sizesRequest) {

        Sizes size = sizesRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Size Not Found"));

        if (sizesRequest.getName() != null) {

            boolean existsByName = sizesRepository.existsByName(sizesRequest.getName());

            if (existsByName) {

                throw new EntityExistsException("Size already exists");
            }

            size.setName(sizesRequest.getName());
        }

        if (sizesRequest.getSizeCategoryId() != null) {

            SizeCategories sizeCategory = sizeCategoriesRepository.findById(sizesRequest.getSizeCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException("Size Category Not Found"));

            size.setSizeCategoryId(sizeCategory);
        }

        return new SizesDTO(sizesRepository.save(size));
    }

    public String delete(Long id) {

        Sizes size = sizesRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Size Not Found"));

        List<Products_Sizes> productsSizesList = productsSizesRepository.findAllBySizeId(size);

        productsSizesRepository.deleteAll(productsSizesList);

        sizesRepository.delete(size);

        return "Successfully deleted";
    }
}
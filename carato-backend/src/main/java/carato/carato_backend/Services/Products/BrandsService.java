package carato.carato_backend.Services.Products;

import carato.carato_backend.DTOs.Filters.Products.BrandFilters;
import carato.carato_backend.DTOs.Post_Put_Requests.Products.BrandsRequest;
import carato.carato_backend.Models.Products.Brands;
import carato.carato_backend.Models.Products.Products;
import carato.carato_backend.Repositories.Products.BrandsRepository;
import carato.carato_backend.Repositories.Products.ProductsRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandsService {

    private final BrandsRepository brandsRepository;
    private final ProductsRepository productsRepository;

    @Value("${pageSize}")
    private Integer pageSize;

    public Brands create(BrandsRequest brandRequest) {

        boolean existsByName = brandsRepository.existsByName(brandRequest.getName());

        if (existsByName) {

            throw new EntityExistsException("This brand already exists");
        }

        Brands brand = Brands.builder()
                .createdTime(LocalDateTime.now())
                .name(brandRequest.getName())
                .description(brandRequest.getDescription())
                .build();

        return brandsRepository.save(brand);
    }

    public Page<Brands> getAll(Integer page, BrandFilters filter) {

        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by("name").ascending());

        if (filter != null && filter.getName() != null) {

            return brandsRepository.findAllByNameLikeIgnoreCase("%" + filter.getName() + "%", pageable);
        }

        return brandsRepository.findAll(pageable);
    }

    public Brands getById(Long id) {

        return brandsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Brand not found"));
    }

    public Brands update(Long id, BrandsRequest brandRequest) {

        Brands brand = getById(id);

        if (brandRequest.getName() != null && !brandRequest.getName().equals(brand.getName())) {

            boolean existsByName = brandsRepository.existsByName(brandRequest.getName());

            if (existsByName) {

                throw new EntityExistsException("This brand already exists");
            }

            brand.setName(brandRequest.getName());
        }

        if (brandRequest.getDescription() != null) {

            brand.setDescription(brandRequest.getDescription());
        }

        return brandsRepository.save(brand);
    }

    public String delete(Long id) {

        Brands brand = getById(id);

        List<Products> productsList = productsRepository.findAllByBrandId(brand);
        List<Products> batchSaveList = new ArrayList<>();

        productsList.forEach(products -> {

            products.setBrandId(null);

            batchSaveList.add(products);
        });

        productsRepository.saveAll(batchSaveList);

        brandsRepository.delete(brand);

        return "Brand deleted Successfully";
    }
}
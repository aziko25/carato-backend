package carato.carato_backend.Services.Products;

import carato.carato_backend.Configurations.Images.FileUploadUtil;
import carato.carato_backend.DTOs.Filters.Products.CategoryFilters;
import carato.carato_backend.DTOs.Post_Put_Requests.Products.CategoriesRequest;
import carato.carato_backend.Models.Products.Categories;
import carato.carato_backend.Models.Products.Products;
import carato.carato_backend.Repositories.Products.CategoriesRepository;
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
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriesService {

    private final CategoriesRepository categoriesRepository;
    private final ProductsRepository productsRepository;

    private final FileUploadUtil fileUploadUtil;

    @Value("${pageSize}")
    private Integer pageSize;

    public Categories create(MultipartFile media, CategoriesRequest categoryRequest) {

        boolean existsByName = categoriesRepository.existsByName(categoryRequest.getName());

        if (existsByName) {

            throw new EntityExistsException("Category with name " + categoryRequest.getName() + " already exists");
        }

        String imageUrl = null;
        if (media != null && !media.isEmpty()) {

            imageUrl = fileUploadUtil.handleMediaUpload(categoryRequest.getName(), media);
        }

        Categories category = Categories.builder()
                .name(categoryRequest.getName())
                .description(categoryRequest.getDescription())
                .createdTime(LocalDateTime.now())
                .imageUrl(imageUrl)
                .build();

        return categoriesRepository.save(category);
    }

    public Page<Categories> getAll(Integer page, CategoryFilters filter) {

        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by("name").ascending());

        if (filter != null && filter.getName() != null) {

            return categoriesRepository.findAllByNameLikeIgnoreCase("%" + filter.getName() + "%", pageable);
        }

        return categoriesRepository.findAll(pageable);
    }

    public Categories getById(Long id) {

        return categoriesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
    }

    public Categories update(Long id, MultipartFile media, CategoriesRequest categoryRequest) {

        Categories category = getById(id);

        String name = category.getName();
        if (categoryRequest.getName() != null && !categoryRequest.getName().equals(category.getName())) {

            boolean existsByName = categoriesRepository.existsByName(categoryRequest.getName());

            if (existsByName) {

                throw new EntityExistsException("Category with name " + categoryRequest.getName() + " already exists");
            }

            name = categoryRequest.getName();

            category.setName(categoryRequest.getName());
        }

        if (media != null && !media.isEmpty()) {

            String imageUrl = fileUploadUtil.handleMediaUpload(name, media);

            category.setImageUrl(imageUrl);
        }

        if (categoryRequest.getDescription() != null) {

            category.setDescription(categoryRequest.getDescription());
        }

        return categoriesRepository.save(category);
    }

    public String delete(Long id) {

        Categories category = getById(id);

        List<Products> productsList = productsRepository.findAllByCategoryId(category);
        List<Products> batchSaveList = new ArrayList<>();

        for (Products product : productsList) {

            product.setCategoryId(null);

            batchSaveList.add(product);
        }

        productsRepository.saveAll(batchSaveList);

        categoriesRepository.delete(category);

        fileUploadUtil.handleMediaDeletion(category.getName());

        return "Successfully deleted";
    }
}
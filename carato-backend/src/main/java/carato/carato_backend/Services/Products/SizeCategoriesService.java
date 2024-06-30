package carato.carato_backend.Services.Products;

import carato.carato_backend.DTOs.Filters.Products.SizeCategoryFilters;
import carato.carato_backend.DTOs.Post_Put_Requests.Products.SizeCategoriesRequest;
import carato.carato_backend.Models.Products.SizeCategories;
import carato.carato_backend.Models.Products.Sizes;
import carato.carato_backend.Repositories.Products.SizeCategoriesRepository;
import carato.carato_backend.Repositories.Products.SizesRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SizeCategoriesService {

    private final SizeCategoriesRepository sizeCategoriesRepository;
    private final SizesRepository sizesRepository;

    @Value("${pageSize}")
    private Integer pageSize;

    public SizeCategories create(SizeCategoriesRequest sizeCategoriesRequest) {

        boolean existsByName = sizeCategoriesRepository.existsByName(sizeCategoriesRequest.getName());

        if (existsByName) {

            throw new EntityExistsException("Size categories already exists");
        }

        return sizeCategoriesRepository.save(SizeCategories.builder()
                .name(sizeCategoriesRequest.getName())
                .build());
    }

    public Page<SizeCategories> getAll(Integer page, SizeCategoryFilters filter) {

        Pageable pageable = PageRequest.of(page - 1, pageSize);

        if (filter != null && filter.getName() != null) {

            return sizeCategoriesRepository.findAllByNameLikeIgnoreCase("%" + filter.getName() + "%", pageable);
        }

        return sizeCategoriesRepository.findAll(pageable);
    }

    public SizeCategories getById(Long id) {

        return sizeCategoriesRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Size category not found"));
    }

    public SizeCategories update(Long id, SizeCategoriesRequest sizeCategoriesRequest) {

        SizeCategories sizeCategories = getById(id);

        if (sizeCategoriesRequest != null && sizeCategoriesRequest.getName() != null
                && !sizeCategoriesRepository.existsByName(sizeCategoriesRequest.getName())) {

            sizeCategories.setName(sizeCategoriesRequest.getName());
        }

        return sizeCategoriesRepository.save(sizeCategories);
    }

    public String delete(Long id) {

        SizeCategories sizeCategories = getById(id);

        List<Sizes> sizesList = sizesRepository.findAllBySizeCategoryId(sizeCategories);
        List<Sizes> batchSaveList = new ArrayList<>();

        for (Sizes size : sizesList) {

            size.setSizeCategoryId(null);

            batchSaveList.add(size);
        }

        sizesRepository.saveAll(batchSaveList);

        sizeCategoriesRepository.delete(sizeCategories);

        return "Successfully deleted";
    }
}
package carato.carato_backend.Services.Products;

import carato.carato_backend.DTOs.Filters.Products.MetalFilters;
import carato.carato_backend.DTOs.Post_Put_Requests.Products.MetalsRequest;
import carato.carato_backend.Models.Products.Metals;
import carato.carato_backend.Repositories.Products.MetalsRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MetalsService {

    private final MetalsRepository metalsRepository;

    @Value("${pageSize}")
    private Integer pageSize;

    public Metals create(MetalsRequest metalsRequest) {

        Metals metal = Metals.builder()
                .name(metalsRequest.getName())
                .description(metalsRequest.getDescription())
                .createdTime(LocalDateTime.now())
                .metalFineness(metalsRequest.getMetalFineness())
                .price(metalsRequest.getPrice())
                .build();

        return metalsRepository.save(metal);
    }

    public Page<Metals> getAll(Integer page, MetalFilters filter) {

        Pageable pageable = PageRequest.of(page - 1, pageSize);

        if (filter != null) {

            return metalsRepository.findAllByFilters(
                    filter.getPriceMin(), filter.getPriceMax(),
                    filter.getFirstCheap(), filter.getFirstExpensive(),
                    filter.getFirstFinenessHigh(), filter.getFirstFinenessLow(),
                    filter.getName(), pageable);
        }

        Pageable sortedPageable = PageRequest.of(page - 1, pageSize, Sort.by("price").descending());

        return metalsRepository.findAll(sortedPageable);
    }

    public Metals getById(Long id) {

        return metalsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Metal not found"));
    }

    public Metals update(Long id, MetalsRequest metalsRequest) {

        Metals metal = getById(id);

        Optional.ofNullable(metalsRequest.getMetalFineness()).ifPresent(metal::setMetalFineness);
        Optional.ofNullable(metalsRequest.getDescription()).ifPresent(metal::setDescription);
        Optional.ofNullable(metalsRequest.getName()).ifPresent(metal::setName);
        Optional.ofNullable(metalsRequest.getPrice()).ifPresent(metal::setPrice);

        return metalsRepository.save(metal);
    }

    public String delete(Long id) {

        Metals metal = getById(id);

        metalsRepository.delete(metal);

        return "Successfully deleted";
    }
}
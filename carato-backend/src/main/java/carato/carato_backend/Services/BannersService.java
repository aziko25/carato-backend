package carato.carato_backend.Services;

import carato.carato_backend.Configurations.Images.FileUploadUtil;
import carato.carato_backend.DTOs.Filters.BannerFilters;
import carato.carato_backend.DTOs.Post_Put_Requests.BannersRequest;
import carato.carato_backend.Models.Banners;
import carato.carato_backend.Repositories.BannersRepository;
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

@Service
@RequiredArgsConstructor
public class BannersService {

    private final BannersRepository bannersRepository;
    private final FileUploadUtil fileUploadUtil;

    @Value("${pageSize}")
    private Integer pageSize;

    public Banners create(MultipartFile media, BannersRequest bannerRequest) {

        boolean existsByName = bannersRepository.existsByName(bannerRequest.getName());

        if (existsByName) {

            throw new EntityExistsException("Banner already exists");
        }

        String imageUrl = null;
        if (media != null && !media.isEmpty()) {

            imageUrl = fileUploadUtil.handleMediaUpload(bannerRequest.getName(), media);
        }

        Banners banners = Banners.builder()
                .name(bannerRequest.getName())
                .createdTime(LocalDateTime.now())
                .isActive(bannerRequest.getIsActive())
                .imageUrl(imageUrl)
                .build();

        return bannersRepository.save(banners);
    }

    public Page<Banners> getAll(Integer page, BannerFilters filter) {

        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by("createdTime").descending());

        if (filter != null) {

            return bannersRepository.findAllByFilters(filter.getName(), filter.getIsActive(), pageable);
        }

        return bannersRepository.findAll(pageable);
    }

    public Banners getById(Long id) {

        return bannersRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Banner not found"));
    }

    public Banners update(Long id, MultipartFile image, BannersRequest bannersRequest) {

        Banners banners = getById(id);

        String name = banners.getName();
        if (bannersRequest.getName() != null && !bannersRequest.getName().equals(banners.getName())) {

            boolean existsByName = bannersRepository.existsByName(bannersRequest.getName());

            if (existsByName) {

                throw new EntityExistsException("Banner with this name already exists");
            }

            name = bannersRequest.getName();
        }

        if (image != null && !image.isEmpty()) {

            banners.setImageUrl(fileUploadUtil.handleMediaUpload(name, image));
        }

        if (bannersRequest.getIsActive() != null) {

            banners.setIsActive(bannersRequest.getIsActive());
        }

        return bannersRepository.save(banners);
    }

    public String delete(Long id) {

        Banners banner = getById(id);

        bannersRepository.delete(banner);

        fileUploadUtil.handleMediaDeletion(banner.getImageUrl());

        return "Successfully deleted";
    }
}
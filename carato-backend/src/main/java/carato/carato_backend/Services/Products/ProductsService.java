package carato.carato_backend.Services.Products;

import carato.carato_backend.Configurations.Images.FileUploadUtil;
import carato.carato_backend.DTOs.Filters.Products.ProductFilters;
import carato.carato_backend.DTOs.Post_Put_Requests.Products.ProductsRequest;
import carato.carato_backend.DTOs.Post_Put_Requests.Products.Products_Sizes_Request;
import carato.carato_backend.DTOs.ReturnDTOs.ProductsDTO;
import carato.carato_backend.Models.Orders.Orders_Products;
import carato.carato_backend.Models.Products.*;
import carato.carato_backend.Models.Products.ManyToMany.Products_Metals;
import carato.carato_backend.Models.Products.ManyToMany.Products_Sizes;
import carato.carato_backend.Repositories.Orders.Orders_Products_Repository;
import carato.carato_backend.Repositories.Products.*;
import carato.carato_backend.Repositories.Products.ManyToMany.Products_Metals_Repository;
import carato.carato_backend.Repositories.Products.ManyToMany.Products_Sizes_Repository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductsService {

    private final ProductsRepository productsRepository;
    private final Product_Images_Repository productImagesRepository;
    private final CategoriesRepository categoriesRepository;
    private final BrandsRepository brandsRepository;

    private final SizeCategoriesRepository sizeCategoriesRepository;
    private final SizesRepository sizesRepository;
    private final MetalsRepository metalsRepository;

    private final Products_Metals_Repository productsMetalsRepository;
    private final Products_Sizes_Repository productsSizesRepository;
    private final Orders_Products_Repository ordersProductsRepository;

    private final FileUploadUtil fileUploadUtil;

    @Value("${pageSize}")
    private Integer pageSize;

    public ProductsDTO create(List<MultipartFile> media, ProductsRequest productRequest) {

        boolean existsByName = productsRepository.existsByName(productRequest.getName());

        if (existsByName) {

            throw new EntityExistsException("Product already exists");
        }

        String previewImageUrl = null;
        if (media != null && !media.isEmpty()) {

            previewImageUrl = fileUploadUtil.handleMediaUpload(productRequest.getName(), media.get(0));
        }

        Categories category = null;
        if (productRequest.getCategoryId() != null) {

            category = categoriesRepository.findById(productRequest.getCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        }

        Brands brand = null;
        if (productRequest.getBrandId() != null) {

            brand = brandsRepository.findById(productRequest.getBrandId())
                    .orElseThrow(() -> new EntityNotFoundException("Brand not found"));
        }

        Products product = Products.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .categoryId(category)
                .price(productRequest.getPrice())
                .brandId(brand)
                .style(productRequest.getStyle())
                .createdTime(LocalDateTime.now())
                .imageUrl(previewImageUrl)
                .build();

        productsRepository.save(product);

        if (media != null && media.size() > 1) {

            product.setProductImages(handleProductMultipleMedia(media, product));
        }

        product.setProductsSizes(setSizesList(productRequest, product));
        product.setProductsMetals(setMetalsList(productRequest, product));

        return new ProductsDTO(product);
    }

    public Page<ProductsDTO> getAll(Integer page, ProductFilters filter) {

        Pageable pageable = PageRequest.of(page - 1, pageSize);

        if (filter != null) {

            List<Long> sizeIdsList = null;
            if (filter.getSizeCategoriesList() != null) {

                List<SizeCategories> sizeCategoriesList = sizeCategoriesRepository.findAllById(filter.getSizeCategoriesList());
                List<Sizes> sizesList = sizesRepository.findAllBySizeCategoryIdIn(sizeCategoriesList);

                sizeIdsList = sizesList.stream().map(Sizes::getId).toList();
            }

            return productsRepository.findAllByFilters(
                    filter.getPriceMin(), filter.getPriceMax(),
                    filter.getFirstCheap(), filter.getFirstExpensive(),
                    filter.getName(), filter.getStyle(), filter.getCategoryId(),
                    filter.getMetalsList(), sizeIdsList,
                    filter.getBrandsList(), pageable)
                    .map(ProductsDTO::new);
        }

        return productsRepository.findAll(pageable).map(ProductsDTO::new);
    }

    public ProductsDTO getById(Long id) {

        return productsRepository.findById(id).map(ProductsDTO::new)
                .orElseThrow(() -> new EntityNotFoundException("Product Not Found"));
    }

    public ProductsDTO update(Long id, List<MultipartFile> media, ProductsRequest productRequest) {

        Products product = productsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product Not Found"));

        String name = product.getName();
        if (productRequest.getName() != null && !product.getName().equals(productRequest.getName())) {

            boolean existsByName = productsRepository.existsByName(productRequest.getName());

            if (existsByName) {

                throw new EntityExistsException("Product already exists");
            }

            product.setName(name);
            name = productRequest.getName();
        }

        String previewImageUrl;
        if (media != null && !media.isEmpty()) {

            previewImageUrl = fileUploadUtil.handleMediaUpload(name, media.get(0));

            product.setImageUrl(previewImageUrl);

            List<Product_Images> productImagesList = productImagesRepository.findAllByProductId(product);

            productImagesRepository.deleteAll(productImagesList);
            fileUploadUtil.handleMultipleMediaDeletion(productImagesList.stream().map(Product_Images::getImage).toList());

            if (media.size() > 1) {

                product.setProductImages(handleProductMultipleMedia(media, product));
            }
        }

        Optional.ofNullable(productRequest.getDescription()).ifPresent(product::setDescription);
        Optional.ofNullable(productRequest.getPrice()).ifPresent(product::setPrice);
        Optional.ofNullable(productRequest.getStyle()).ifPresent(product::setStyle);

        if (productRequest.getCategoryId() != null) {

            Categories category = categoriesRepository.findById(productRequest.getCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException("Category not found"));

            product.setCategoryId(category);
        }

        if (productRequest.getBrandId() != null) {

            Brands brand = brandsRepository.findById(productRequest.getBrandId())
                    .orElseThrow(() -> new EntityNotFoundException("Brand not found"));

            product.setBrandId(brand);
        }

        if (productRequest.getSizesList() != null) {

            product.setProductsSizes(setSizesList(productRequest, product));
        }

        if (productRequest.getMetalsList() != null) {

            product.setProductsMetals(setMetalsList(productRequest, product));
        }

        return new ProductsDTO(productsRepository.save(product));
    }

    public String delete(Long id) {

        Products product = productsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product Not Found"));

        List<Products_Metals> productsMetalsList = product.getProductsMetals();
        List<Products_Sizes> productsSizesList = product.getProductsSizes();
        List<Product_Images> productImagesList = productImagesRepository.findAllByProductId(product);
        List<Orders_Products> ordersProductsList = ordersProductsRepository.findAllByProductId(product);

        productsMetalsRepository.deleteAll(productsMetalsList);
        productsSizesRepository.deleteAll(productsSizesList);
        productImagesRepository.deleteAll(productImagesList);
        ordersProductsRepository.deleteAll(ordersProductsList);

        fileUploadUtil.handleMediaDeletion(product.getImageUrl());
        fileUploadUtil.handleMultipleMediaDeletion(productImagesList.stream().map(Product_Images::getImage).toList());

        productsRepository.delete(product);

        return "Successfully deleted";
    }

    private List<Product_Images> handleProductMultipleMedia(List<MultipartFile> media, Products product) {

        List<Product_Images> productImagesList = new ArrayList<>();

        for (int i = 1; i < media.size(); i++) {

            String productName = product.getName() + "_" + i;

            MultipartFile image = media.get(i);

            Product_Images productImage = new Product_Images();

            productImage.setProductId(product);
            productImage.setImage(fileUploadUtil.handleMediaUpload(productName, image));

            productImagesRepository.save(productImage);

            productImagesList.add(productImage);
        }

        return productImagesList;
    }

    private List<Products_Sizes> setSizesList(ProductsRequest productRequest, Products product) {

        List<Products_Sizes> productSizesList = new ArrayList<>();

        for (Products_Sizes_Request size : productRequest.getSizesList()) {

            Sizes foundSize = sizesRepository.findById(size.getSizeId())
                    .orElseThrow(() -> new EntityNotFoundException("Size Not Found"));

            Products_Sizes productsSize = Products_Sizes.builder()
                    .productId(product)
                    .sizeId(foundSize)
                    .quantity(size.getQuantity())
                    .price(size.getPrice())
                    .build();

            productsSizesRepository.save(productsSize);

            productSizesList.add(productsSize);
        }

        product.setProductsSizes(productSizesList);

        return productSizesList;
    }

    private List<Products_Metals> setMetalsList(ProductsRequest productRequest, Products product) {

        List<Products_Metals> productMetalsList = new ArrayList<>();

        for (Long metal : productRequest.getMetalsList()) {

            Metals foundMetal = metalsRepository.findById(metal)
                    .orElseThrow(() -> new EntityNotFoundException("Metal Not Found"));

            Products_Metals productsMetal = new Products_Metals(product, foundMetal);

            productsMetalsRepository.save(productsMetal);

            productMetalsList.add(productsMetal);
        }

        product.setProductsMetals(productMetalsList);

        return productMetalsList;
    }
}
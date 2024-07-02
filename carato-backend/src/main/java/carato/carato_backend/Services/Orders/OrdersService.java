package carato.carato_backend.Services.Orders;

import carato.carato_backend.DTOs.Filters.Orders.OrderFilters;
import carato.carato_backend.DTOs.Post_Put_Requests.Orders.OrdersRequest;
import carato.carato_backend.DTOs.Post_Put_Requests.Orders.SelectedProducts;
import carato.carato_backend.DTOs.ReturnDTOs.OrdersDTO;
import carato.carato_backend.Models.Orders.Orders;
import carato.carato_backend.Models.Orders.Orders_Products;
import carato.carato_backend.Models.Products.ManyToMany.Products_Sizes;
import carato.carato_backend.Models.Products.Products;
import carato.carato_backend.Models.Products.Sizes;
import carato.carato_backend.Models.Users.UserAddresses;
import carato.carato_backend.Models.Users.Users;
import carato.carato_backend.Repositories.Orders.OrdersRepository;
import carato.carato_backend.Repositories.Orders.Orders_Products_Repository;
import carato.carato_backend.Repositories.Products.ManyToMany.Products_Sizes_Repository;
import carato.carato_backend.Repositories.Products.ProductsRepository;
import carato.carato_backend.Repositories.Products.SizesRepository;
import carato.carato_backend.Repositories.Users.UserAddressesRepository;
import carato.carato_backend.Repositories.Users.UsersRepository;
import carato.carato_backend.Services.Payments.ClickService;
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
import java.util.Optional;

import static carato.carato_backend.Configurations.JWT.AuthorizationMethods.USER_ID;

@Service
@RequiredArgsConstructor
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final Orders_Products_Repository ordersProductsRepository;

    private final ProductsRepository productsRepository;
    private final SizesRepository sizesRepository;
    private final Products_Sizes_Repository productsSizesRepository;

    private final UsersRepository usersRepository;
    private final UserAddressesRepository userAddressesRepository;

    private final ClickService clickService;

    @Value("${pageSize}")
    private Integer pageSize;

    public OrdersDTO create(OrdersRequest orderRequest) {

        Users user = usersRepository.findById(USER_ID)
                .orElseThrow(() -> new EntityNotFoundException("User Not Found"));

        UserAddresses userAddress = userAddressesRepository.findByIdAndUserId(orderRequest.getAddressId(), user)
                .orElseThrow(() -> new EntityNotFoundException("Address Not Found"));

        boolean isPreOrder = false;
        if (orderRequest.getIsPreOrder() != null) {

            isPreOrder = orderRequest.getIsPreOrder();
        }

        Orders order = Orders.builder()
                .isPreOrder(isPreOrder)
                .deliveryType(orderRequest.getDeliveryType())
                .createdTime(LocalDateTime.now())
                .phone(orderRequest.getPhone())
                .email(orderRequest.getEmail())
                .address(userAddress.getAddress())
                .comment(orderRequest.getComment())
                .isPaymentDone(false)
                .userId(user)
                .build();

        ordersRepository.save(order);

        order.setOrdersProductsList(setSelectedProductsToOrder(orderRequest, order));

        order.setOrderPaymentId(order.getId().toString());

        if (orderRequest.getPaymentType() != null && orderRequest.getPaymentType().equalsIgnoreCase("click")) {

            order.setPayTransactionUrl(clickService.createTransaction(order, orderRequest.getReturnUrl()));
        }

        return new OrdersDTO(ordersRepository.save(order));
    }

    public Page<OrdersDTO> getAll(Integer page, OrderFilters filter) {

        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by("createdTime").descending());

        if (filter != null) {

            return ordersRepository.findAllByCreatedTimeBetweenAndIsPreOrder(
                    filter.getStartTime().atStartOfDay(),
                    filter.getEndTime().atTime(23, 59), filter.getIsPreOrder(), pageable)
                    .map(OrdersDTO::new);
        }

        return ordersRepository.findAll(pageable).map(OrdersDTO::new);
    }

    public OrdersDTO getById(Long orderId) {

        return new OrdersDTO(ordersRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order Not Found")));
    }

    public Page<OrdersDTO> getAllMy(Integer page, OrderFilters filter) {

        Users user = usersRepository.findById(USER_ID).orElseThrow(() -> new EntityNotFoundException("User Not Found"));

        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by("createdTime").descending());

        if (filter != null) {

            return ordersRepository.findAllByUserIdAndCreatedTimeBetweenAndIsPreOrder
                    (user, filter.getStartTime().atStartOfDay(),
                    filter.getEndTime().atTime(23, 59), filter.getIsPreOrder(), pageable)
                    .map(OrdersDTO::new);
        }

        return ordersRepository.findAllByUserId(user, pageable).map(OrdersDTO::new);
    }

    public OrdersDTO update(Long orderId, OrdersRequest orderRequest) {

        Orders order = ordersRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundException("Order Not Found"));

        if (orderRequest.getIsPreOrder() != null) {

            order.setIsPreOrder(orderRequest.getIsPreOrder());
        }

        if (orderRequest.getAddressId() != null) {

            UserAddresses userAddress = userAddressesRepository.findByIdAndUserId(orderRequest.getAddressId(), order.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("Address Not Found"));

            order.setAddress(userAddress.getAddress());
        }

        if (orderRequest.getSelectedProductsList() != null && !orderRequest.getSelectedProductsList().isEmpty()) {

            List<Orders_Products> ordersProductsList = ordersProductsRepository.findAllByOrderId(order);

            ordersProductsRepository.deleteAll(ordersProductsList);

            order.setOrdersProductsList(setSelectedProductsToOrder(orderRequest, order));
        }

        Optional.ofNullable(orderRequest.getPhone()).ifPresent(order::setPhone);
        Optional.ofNullable(orderRequest.getEmail()).ifPresent(order::setEmail);
        Optional.ofNullable(orderRequest.getComment()).ifPresent(order::setComment);
        Optional.ofNullable(orderRequest.getIsPaymentDone()).ifPresent(order::setIsPaymentDone);
        Optional.ofNullable(orderRequest.getDeliveryType()).ifPresent(order::setDeliveryType);

        return new OrdersDTO(ordersRepository.save(order));
    }

    private List<Orders_Products> setSelectedProductsToOrder(OrdersRequest orderRequest, Orders order) {

        double totalSum = 0.0;

        List<Orders_Products> ordersProductsList = new ArrayList<>();

        for (SelectedProducts selectedProduct : orderRequest.getSelectedProductsList()) {

            Products product = productsRepository.findById(selectedProduct.getProductId())
                    .orElseThrow(() -> new EntityNotFoundException("Product Not Found"));

            Sizes size = sizesRepository.findById(selectedProduct.getSizeId())
                    .orElseThrow(() -> new EntityNotFoundException("Size Not Found"));

            Products_Sizes productsSize = productsSizesRepository.findByProductIdAndSizeId(product, size)
                    .orElseThrow(() -> new EntityNotFoundException("Product With This Size Is Not Found"));

            if (orderRequest.getIsPreOrder() == null || (!orderRequest.getIsPreOrder() && productsSize.getQuantity() <= 0)) {

                ordersRepository.delete(order);

                throw new IllegalArgumentException("Product With This Size Is Not In Stock");
            }

            Orders_Products ordersProduct = new Orders_Products();

            ordersProduct.setOrderId(order);
            ordersProduct.setProductId(product);
            ordersProduct.setUserId(order.getUserId());
            ordersProduct.setProductName(product.getName());
            ordersProduct.setSizeName(size.getName());
            ordersProduct.setQuantity(selectedProduct.getQuantity());
            ordersProduct.setPrice(productsSize.getPrice() * selectedProduct.getQuantity());

            totalSum += productsSize.getPrice() * selectedProduct.getQuantity();

            if (orderRequest.getIsPreOrder() == null || !orderRequest.getIsPreOrder()) {

                if (productsSize.getQuantity() - selectedProduct.getQuantity() < 0) {

                    throw new IllegalArgumentException("You Selected More Quantity Than It Has In Stock.\n" +
                            "Product ID: " + product.getId() + "\nProduct Name: " + product.getName() +
                            "\nRemaining Stock: " + productsSize.getQuantity());
                }
                else {

                    productsSize.setQuantity(productsSize.getQuantity() - selectedProduct.getQuantity());
                    productsSizesRepository.save(productsSize);
                }
            }

            ordersProductsRepository.save(ordersProduct);

            ordersProductsList.add(ordersProduct);
        }

        order.setTotalSum(totalSum);

        ordersRepository.save(order);

        return ordersProductsList;
    }

    public String delete(Long orderId) {

        Orders order = ordersRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundException("Order Not Found"));

        List<Orders_Products> ordersProductsList = ordersProductsRepository.findAllByOrderId(order);

        ordersProductsRepository.deleteAll(ordersProductsList);
        ordersRepository.delete(order);

        return "Order Successfully Deleted";
    }
}
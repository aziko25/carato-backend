package carato.carato_backend.Models.Orders;

import carato.carato_backend.Models.Users.Users;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "orders")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String orderPaymentId;

    private String payTransactionUrl;

    private Boolean isPreOrder;
    private String deliveryType;

    @JsonFormat(shape = STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createdTime;

    private String address;
    private Double totalSum;
    private String phone;
    private String email;
    private String comment;
    private Boolean isPaymentDone;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users userId;

    @OneToMany(mappedBy = "orderId")
    private List<Orders_Products> ordersProductsList;
}
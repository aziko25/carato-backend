package carato.carato_backend.Repositories.Orders;

import carato.carato_backend.Models.Orders.Orders;
import carato.carato_backend.Models.Users.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {

    Page<Orders> findAllByUserId(Users user, Pageable pageable);

    Page<Orders> findAllByCreatedTimeBetween(LocalDateTime startTime, LocalDateTime endTime, Pageable pageable);

    Page<Orders> findAllByUserIdAndCreatedTimeBetween(Users user, LocalDateTime startTime, LocalDateTime endTime, Pageable pageable);
}
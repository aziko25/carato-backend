package carato.carato_backend.Repositories.Users;

import carato.carato_backend.Models.Users.UserAddresses;
import carato.carato_backend.Models.Users.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAddressesRepository extends JpaRepository<UserAddresses, Long> {

    Page<UserAddresses> findAllByUserId(Users user, Pageable pageable);

    Optional<UserAddresses> findByIdAndUserId(Long id, Users user);
}
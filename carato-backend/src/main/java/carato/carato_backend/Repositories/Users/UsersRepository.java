package carato.carato_backend.Repositories.Users;

import carato.carato_backend.Models.Users.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByUsername(String username);

    boolean existsByUsername(String username);

    @Query("SELECT u FROM Users u WHERE " +
            "(:name IS NULL OR u.username ILIKE %:name% OR " +
            "u.email ILIKE %:name% OR " +
            "u.phone ILIKE %:name% OR " +
            "u.fullName ILIKE %:name% OR " +
            "u.role ILIKE %:name%)")
    Page<Users> searchUsers(@Param("name") String name, Pageable pageable);
}
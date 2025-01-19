package pl.wojo.app.ecommerce_backend.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.wojo.app.ecommerce_backend.model.LocalUser;

@Repository // no-required
public interface LocalUserRepository extends JpaRepository<LocalUser, Long>{
    Optional<LocalUser> findByUsername(String username);   // do not ignore cases

    Optional<LocalUser> findByEmailIgnoreCase(String email);
}

package org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.repositories;

import org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query("SELECT u.id FROM User u WHERE u.username = :username")
    Long getUserIdByUsername(String username);

    Boolean existsByUsername(String username);
}

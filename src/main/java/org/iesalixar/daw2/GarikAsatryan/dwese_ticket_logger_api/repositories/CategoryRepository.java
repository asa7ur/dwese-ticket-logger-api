package org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.repositories;

import org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String name);
}


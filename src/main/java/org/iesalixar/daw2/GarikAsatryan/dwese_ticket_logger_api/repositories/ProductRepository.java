package org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.repositories;

import org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContainingIgnoreCase(String name);
}

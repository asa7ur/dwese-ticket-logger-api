package org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.repositories;

import org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {


    List<Order> findByUserId(Long userId);
}


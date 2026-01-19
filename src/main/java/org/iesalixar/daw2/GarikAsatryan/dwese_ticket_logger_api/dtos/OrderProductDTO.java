package org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.dtos;

import lombok.Getter;
import lombok.Setter;
import org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.entities.OrderProductId;

@Getter
@Setter
public class OrderProductDTO {
    private OrderProductId id;
    private ProductDTO product;
    private int quantity;
}


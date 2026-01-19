package org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.mappers;

import org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.dtos.OrderProductDTO;
import org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.entities.Order;
import org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.entities.OrderProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderProductMapper {

    @Autowired
    private ProductMapper productMapper;

    public OrderProductDTO toDTO(OrderProduct orderProduct) {
        OrderProductDTO dto = new OrderProductDTO();
        dto.setId(orderProduct.getId());
        dto.setProduct(productMapper.toDTO(orderProduct.getProduct()));
        dto.setQuantity(orderProduct.getQuantity());
        return dto;
    }

    public OrderProduct toEntity(OrderProductDTO dto, Order order) {
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setProduct(productMapper.toEntity(dto.getProduct()));
        orderProduct.setOrder(order);
        orderProduct.setQuantity(dto.getQuantity());
        return orderProduct;
    }
}
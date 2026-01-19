package org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.mappers;

import org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.dtos.OrderDTO;
import org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.entities.Order;
import org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.entities.OrderProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OrderProductMapper orderProductMapper;

    public OrderDTO toDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setOrderDate(order.getOrderDate());
        dto.setTotal(order.getAmount());
        dto.setUser(userMapper.toDTO(order.getUser()));
        for (OrderProduct product : order.getOrderProducts()) {
            dto.getOrderProducts().add(orderProductMapper.toDTO(product));
        }
        return dto;
    }

    public Order toEntity(OrderDTO dto) {
        Order order = new Order();
        order.setId(dto.getId());
        order.setUser(userMapper.toEntity(dto.getUser()));
        return order;
    }
}


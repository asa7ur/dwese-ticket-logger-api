package org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.mappers;

import org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.dtos.UserDTO;
import org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    @Autowired
    private CategoryMapper categoryMapper;

    public UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        return dto;
    }

    public User toEntity(UserDTO dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        return user;
    }
}
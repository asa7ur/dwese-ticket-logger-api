package org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.mappers;

import org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.dtos.UserDTO;
import org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setImage(user.getImage());
        return dto;
    }

    public User toEntity(UserDTO dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setImage(user.getImage());
        return user;
    }

}

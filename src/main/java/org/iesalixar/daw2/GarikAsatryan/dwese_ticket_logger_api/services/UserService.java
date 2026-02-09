package org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.services;

import lombok.RequiredArgsConstructor;
import org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.dtos.RegionDTO;
import org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.dtos.UserDTO;
import org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.entities.User;
import org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.mappers.RegionMapper;
import org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.mappers.UserMapper;
import org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.repositories.RegionRepository;
import org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public Long getUserIdByUsername(String username) {
        try {
            logger.info("Buscando usuario con el username {}", username);
            return userRepository.getUserIdByUsername(username);
        } catch (Exception e) {
            logger.error("Error al buscar usuario con username {}: {}", username, e.getMessage());
            throw new RuntimeException("Error al buscar el usuario.", e);
        }
    }

    public Optional<UserDTO> getUserById(Long id) {
        try {
            logger.info("Buscando usuario con el id {}", id);
            return userRepository.findById(id).map(userMapper::toDTO);
        } catch (Exception e) {
            logger.error("Error al buscar usuario con id {}: {}", id, e.getMessage());
            throw new RuntimeException("Error al buscar el usuario.", e);
        }

    }
}

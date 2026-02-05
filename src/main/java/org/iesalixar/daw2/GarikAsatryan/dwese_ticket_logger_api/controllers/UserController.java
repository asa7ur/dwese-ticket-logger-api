package org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.controllers;

import lombok.RequiredArgsConstructor;
import org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.dtos.UserDTO;
import org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    /**
     * Obtener una usuario por ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        logger.info("Buscando usuario con ID {}", id);
        try {
            Optional<UserDTO> userDTO = userService.getUserById(id);
            if (userDTO.isPresent()) {
                logger.info("Región con ID {} encontrada", id);
                return ResponseEntity.ok(userDTO.get());
            } else {
                logger.warn("No se encontró ningun usuario con ID {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El usuario no existe.");
            }
        } catch (Exception e) {
            logger.error("Error al buscar el usuario con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar el usuario.");
        }
    }
}

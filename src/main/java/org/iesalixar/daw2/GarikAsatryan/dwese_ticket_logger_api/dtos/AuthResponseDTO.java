package org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthResponseDTO {
    private String token;
    private String message;
}

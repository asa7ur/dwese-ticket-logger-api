package org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private long Id;
    private String username;
    private String firstName;
    private String lastName;
    private String image;
}

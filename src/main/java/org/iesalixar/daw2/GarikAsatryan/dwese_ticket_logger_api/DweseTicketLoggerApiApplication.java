package org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@SpringBootApplication
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
public class DweseTicketLoggerApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DweseTicketLoggerApiApplication.class, args);
    }

}

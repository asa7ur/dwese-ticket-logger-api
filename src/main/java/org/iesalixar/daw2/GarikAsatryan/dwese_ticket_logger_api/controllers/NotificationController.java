package org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.controllers;

import lombok.RequiredArgsConstructor;
import org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.dtos.NotificationCreateDTO;
import org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.dtos.NotificationDTO;
import org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.services.NotificationService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ws/notifications")
public class NotificationController {
    private NotificationService notificationService;

    @GetMapping
    public Flux<NotificationDTO> getAllNotifications() {
        return notificationService.getAllNotifications();
    }

    @PostMapping
    public Mono<NotificationDTO> createNotification(@RequestBody NotificationCreateDTO notificationCreateDTO) {
        return notificationService.saveNotification(notificationCreateDTO);
    }
}

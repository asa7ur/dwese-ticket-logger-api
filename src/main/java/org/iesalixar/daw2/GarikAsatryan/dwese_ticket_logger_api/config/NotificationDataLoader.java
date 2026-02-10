package org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.config;

import lombok.RequiredArgsConstructor;
import org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.entities.Notification;
import org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.repositories.NotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Instant;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class NotificationDataLoader implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(NotificationDataLoader.class);

    private final NotificationRepository notificationRepository;

    @Override
    public void run(String... args) {
        logger.info("Iniciando la carga de datos de notificaciones...");

        notificationRepository.deleteAll()
                .thenMany(
                        Flux.just(
                                new Notification(UUID.randomUUID().toString(), "Precio más bajo", "Precio más bajo para el producto en el supermercado Mercadona", false, Instant.now()),
                                new Notification(UUID.randomUUID().toString(), "Producto nuevo añadido", "Se ha añadido un nuevo producto", false, Instant.now()),
                                new Notification(UUID.randomUUID().toString(), "Nuevo usuario", "Se ha registrado un nuevo usuario", false, Instant.now())
                        )
                )
                .flatMap(notificationRepository::save)
                .doOnNext(notification -> logger.info("Notificación insertada: {}", notification))
                .doOnError(error -> logger.error("Error al insertar notificación", error))
                .subscribe();
    }
}

package org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.dtos.ProvinceCreateDTO;
import org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.dtos.ProvinceDTO;
import org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.services.ProvinceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping("/api/provinces")
@RequiredArgsConstructor
public class ProvinceController {

    private static final Logger logger = LoggerFactory.getLogger(ProvinceController.class);

    private final ProvinceService provinceService;

    @GetMapping
    public ResponseEntity<Page<ProvinceDTO>> getAllProvinces(
            @PageableDefault(sort = "name") Pageable pageable
    ) {

        logger.info("Solicitando todas las provincias con paginación: página {}, tamaño {}", pageable.getPageNumber(), pageable.getPageSize());

        try {
            Page<ProvinceDTO> provinces = provinceService.getAllProvinces(pageable);
            logger.info("Se han encontrado {} provincias", provinces.getTotalElements());
            return ResponseEntity.ok(provinces);
        } catch (Exception e) {
            logger.error("Error al procesar la solicitud de listado de provincias: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProvinceById(@PathVariable Long id) {
        logger.info("Buscando provincia con ID {}", id);
        try {
            Optional<ProvinceDTO> provinceDTO = provinceService.getProvinceById(id);
            if (provinceDTO.isPresent()) {
                logger.info("Provincia con ID {} encontrada", id);
                return ResponseEntity.ok(provinceDTO.get());
            } else {
                logger.warn("No se encontró ninguna provincia con ID {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La provincia no existe.");
            }
        } catch (Exception e) {
            logger.error("Error al buscar la provincia con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar la provincia.");
        }
    }

    @PostMapping
    public ResponseEntity<?> createProvince(
            @Valid @RequestBody ProvinceCreateDTO provinceCreateDTO, // Cambiado de @RequestBody a @ModelAttribute
            Locale locale) {
        try {
            ProvinceDTO createdProvince = provinceService.createProvince(provinceCreateDTO, locale);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProvince);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error al crear la provincia: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear la provincia");
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateProvince(
            @PathVariable Long id,
            @Valid @RequestBody ProvinceCreateDTO provinceCreateDTO,
            Locale locale) {
        try {
            ProvinceDTO updatedProvince = provinceService.updateProvince(id, provinceCreateDTO, locale);
            return ResponseEntity.ok(updatedProvince);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error al actualizar la provincia con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar la provincia");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProvince(@PathVariable Long id) {
        logger.info("Eliminando región con ID {}", id);
        try {
            provinceService.deleteProvince(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error al eliminar la región con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la región.");
        }
    }
}
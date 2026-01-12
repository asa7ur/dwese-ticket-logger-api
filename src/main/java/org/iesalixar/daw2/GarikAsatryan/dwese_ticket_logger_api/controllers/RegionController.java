package org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.dtos.RegionCreateDTO;
import org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.dtos.RegionDTO;
import org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.entities.Region;
import org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.services.RegionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/regions")
@RequiredArgsConstructor
public class RegionController {

    private static final Logger logger = LoggerFactory.getLogger(RegionController.class);

    private final RegionService regionService;

    /**
     * Lista regiones con paginación, ordenación y búsqueda.
     */
    @GetMapping
    public ResponseEntity<Page<RegionDTO>> getAllRegions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "code") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) String searchTerm) {

        logger.info("Listando regiones - Página: {}, Orden: {} {}, Busqueda: {}", page, sortField, sortDir, searchTerm);

        try {
            Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
            Pageable pageable = PageRequest.of(page, size, sort);

            Page<RegionDTO> regionDTOs = regionService.getAllRegions(searchTerm, pageable);
            logger.info("Se han encontrado {} regiones", regionDTOs.getTotalElements());
            return ResponseEntity.ok(regionDTOs);
        } catch (Exception e) {
            logger.error("Error al procesar la solicitud de listado de regiones: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Obtener una región por ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getRegionById(@PathVariable Long id) {
        logger.info("Buscando región con ID {}", id);
        try {
            Optional<RegionDTO> regionDTO = regionService.getRegionById(id);
            if (regionDTO.isPresent()) {
                logger.info("Región con ID {} encontrada", id);
                return ResponseEntity.ok(regionDTO.get());
            } else {
                logger.warn("No se encontró ninguna región con ID {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La región no existe.");
            }
        } catch (Exception e) {
            logger.error("Error al buscar la región con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar la región.");
        }
    }

    /**
     * Inserta una nueva región recibiendo un JSON.
     */
    @PostMapping
    public ResponseEntity<?> createRegion(
            @Valid @RequestBody RegionCreateDTO regionCreateDTO,
            Locale locale) {
        logger.info("Insertando nueva región con código {}", regionCreateDTO.getCode());
        try {
            return regionService.createRegion(regionCreateDTO, locale);
        } catch (Exception e) {
            logger.error("Error al crear la región: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear la región");
        }
    }

    /**
     * Actualiza una región existente recibiendo un JSON.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRegion(
            @PathVariable Long id,
            @Valid @RequestBody RegionCreateDTO regionCreateDTO,
            Locale locale) {
        logger.info("Actualizando región con ID {}", id);

        try {
            return regionService.updateRegion(id, regionCreateDTO, locale);
        } catch (Exception e) {
            logger.error("Error al actualizar la región con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar la región.");
        }
    }

    /**
     * Elimina una región y su imagen asociada del sistema de archivos.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRegion(@PathVariable Long id) {
        logger.info("Eliminando región con ID {}", id);
        try {
            return regionService.deleteRegion(id);
        } catch (Exception e) {
            logger.error("Error al eliminar la región con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la región.");
        }
    }
}
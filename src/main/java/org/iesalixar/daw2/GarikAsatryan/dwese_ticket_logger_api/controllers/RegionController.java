package org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.entities.Region;
import org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.repositories.RegionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
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

    private final RegionRepository regionRepository;
    private final MessageSource messageSource;

    /**
     * Lista regiones con paginación, ordenación y búsqueda.
     */
    @GetMapping
    public ResponseEntity<Page<Region>> getAllRegions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "code") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) String searchTerm) {

        logger.info("Listando regiones - Página: {}, Orden: {} {}, Busqueda: {}", page, sortField, sortDir, searchTerm);

        try {
            Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
            Pageable pageable = PageRequest.of(page, size, sort);

            Page<Region> regionPage;
            if (searchTerm != null && !searchTerm.trim().isEmpty()) {
                regionPage = regionRepository.findByNameContainingIgnoreCase(searchTerm, pageable);
            } else {
                regionPage = regionRepository.findAll(pageable);
            }

            return ResponseEntity.ok(regionPage);
        } catch (Exception e) {
            logger.error("Error al listar las regiones: {}", e.getMessage());
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
            Optional<Region> region = regionRepository.findById(id);
            if (region.isPresent()) {
                logger.info("Región con ID {} encontrada: {}", id, region.get());
                return ResponseEntity.ok(region.get());
            } else {
                logger.warn("No se encontró ninguna región con ID {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            logger.error("Error al buscar la región con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Inserta una nueva región recibiendo un JSON.
     */
    @PostMapping
    public ResponseEntity<?> createRegion(@Valid @RequestBody Region region, Locale locale) {
        logger.info("Insertando nueva región con código {}", region.getCode());
        try {
            if (regionRepository.existsRegionByCode(region.getCode())) {
                String errorMsg = messageSource.getMessage("msg.region.code-exists", null, locale);
                logger.warn("El código {} ya existe.", region.getCode());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMsg);
            }

            Region savedRegion = regionRepository.save(region);
            logger.info("Región coreada exitosamente con ID {}", savedRegion.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(savedRegion);
        } catch (Exception e) {
            logger.error("Error al crear la región: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear la región");
        }
    }

    /**
     * Actualiza una región existente recibiendo un JSON.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRegion(@PathVariable Long id, @Valid @RequestBody Region region, Locale locale) {
        logger.info("Actualizando región con ID {}", id);

        try {
            // Verificar si la región exite
            Optional<Region> existingRegion = regionRepository.findById(id);
            if (existingRegion.isEmpty()) {
                logger.warn("No se encontró ninguna región con el ID {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La región no existe.");
            }
            // Validar si el código ya pertenece a otra región
            if (regionRepository.existsRegionByCodeAndNotId(region.getCode(), id)) {
                String errorMsg = messageSource.getMessage("msg.region.code-exists", null, locale);
                logger.warn("error al actualizar región: {}", errorMsg);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMsg);
            }
            // Actualizar la región
            region.setId(id);
            Region updateRegion = regionRepository.save(region);
            logger.info("Región con ID {} actualizada con éxito.", id);
            return ResponseEntity.ok(updateRegion);
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
            if (!regionRepository.existsById(id)) {
                logger.warn("No se encontró ninguna región con la ID {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La región no existe.");
            }
            // Eliminar la región
            regionRepository.deleteById(id);
            logger.info("Región con ID {} eliminada exitosamente.", id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Error al eliminar la región con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la región.");
        }
    }
}
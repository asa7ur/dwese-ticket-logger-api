package org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.services;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.dtos.RegionCreateDTO;
import org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.dtos.RegionDTO;
import org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.entities.Region;
import org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.mappers.RegionMapper;
import org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.repositories.RegionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegionService {
    private static final Logger logger = LoggerFactory.getLogger(RegionService.class);

    private final RegionRepository regionRepository;
    private final RegionMapper regionMapper;
    private final FileStorageService fileStorageService;
    public final MessageSource messageSource;

    public Page<RegionDTO> getAllRegions(Pageable pageable) {
        logger.info("Solicitando todas las regiones con paginación: página {}, tamaño {}", pageable.getPageNumber(), pageable.getPageSize());
        try {
            Page<Region> regionPage = regionRepository.findAll(pageable);
            logger.info("Se han encontrado {} regiones en la página actual.", regionPage.getNumberOfElements());
            return regionPage.map(regionMapper::toDTO);
        } catch (Exception e) {
            logger.error("Error al obtener todas las regiones: {}", e.getMessage());
            throw e;
        }
    }

    public Optional<RegionDTO> getRegionById(Long id) {
        try {
            logger.info("Buscando región con el ID {}", id);
            return regionRepository.findById(id).map(regionMapper::toDTO);
        } catch (Exception e) {
            logger.error("Error al buscar región con ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Error al buscar la región.", e);
        }
    }

    public RegionDTO createRegion(RegionCreateDTO regionCreateDTO, Locale locale) {
        if (regionRepository.existsRegionByCode(regionCreateDTO.getCode())) {
            String errorMessage = messageSource.getMessage("msg.region.code-exists", null, locale);
            throw new IllegalArgumentException(errorMessage);
        }

        String fileName = null;
        if (regionCreateDTO.getImage() != null && !regionCreateDTO.getImage().isEmpty()) {
            fileName = fileStorageService.saveFile(regionCreateDTO.getImage());
            if (fileName == null) {
                throw new RuntimeException("Error al guardar la imagen.");
            }
        }

        Region region = regionMapper.toEntity(regionCreateDTO);
        region.setImage(fileName); // Guardamos solo el nombre del archivo en la BD
        Region savedRegion = regionRepository.save(region);
        logger.info("Región {} creada con éxito", savedRegion.getName());

        return regionMapper.toDTO(savedRegion);
    }

    public RegionDTO updateRegion(
            Long id,
            @Valid RegionCreateDTO regionCreateDTO,
            Locale locale
    ) {
        Region existingRegion = regionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("La región no existe."));

        if (regionRepository.existsRegionByCodeAndNotId(regionCreateDTO.getCode(), id)) {
            String errorMessage = messageSource.getMessage("msg.region.code-exists", null, locale);
            throw new IllegalArgumentException(errorMessage);
        }

        String fileName = existingRegion.getImage();
        if (regionCreateDTO.getImage() != null && !regionCreateDTO.getImage().isEmpty()) {
            fileName = fileStorageService.saveFile(regionCreateDTO.getImage());
            if (fileName == null) {
                throw new RuntimeException("Error al guardar la nueva imagen");
            }
        }

        existingRegion.setCode(regionCreateDTO.getCode());
        existingRegion.setName(regionCreateDTO.getName());
        existingRegion.setImage(fileName);

        Region updatedRegion = regionRepository.save(existingRegion);
        logger.info("Región con ID {} actualizada exitosamente.", updatedRegion.getId());

        return regionMapper.toDTO(updatedRegion);
    }

    public void deleteRegion(Long id) {
        logger.info("Buscando región con ID {}", id);

        Region region = regionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("La región no existe"));

        if (region.getImage() != null && !region.getImage().isEmpty()) {
            fileStorageService.deleteFile(region.getImage());
            logger.info("imagen asociada a la región con ID {} eliminada.", id);
        }

        regionRepository.deleteById(id);
        logger.info("Región con ID {} eliminada exitosamente.", id);
    }
}

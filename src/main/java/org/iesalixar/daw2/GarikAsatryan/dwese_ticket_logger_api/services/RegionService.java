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
    public final MessageSource messageSource;

    public Page<RegionDTO> getAllRegions(String searchTerm, Pageable pageable) {
        try {
            Page<Region> regionPage;
            if (searchTerm != null && !searchTerm.trim().isEmpty()) {
                regionPage = regionRepository.findByNameContainingIgnoreCase(searchTerm, pageable);
            } else {
                regionPage = regionRepository.findAll(pageable);
            }
            return regionPage.map(regionMapper::toDTO);
        } catch (Exception e) {
            logger.error("Error al obtener todas las regiones: {}", e.getMessage());
            throw new RuntimeException("Error al obtener todas las regiones.", e);
        }
    }

    public Optional<RegionDTO> getRegionById(Long id) {
        try {
            logger.info("buscando región con ID {}", id);
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

        Region region = regionMapper.toEntity(regionCreateDTO);
        Region savedRegion = regionRepository.save(region);

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
        existingRegion.setCode(regionCreateDTO.getCode());
        existingRegion.setName(regionCreateDTO.getName());
        Region updatedRegion = regionRepository.save(existingRegion);

        return regionMapper.toDTO(updatedRegion);
    }

    public void deleteRegion(Long id) {
        if (!regionRepository.existsById(id)) {
            throw new IllegalArgumentException("la región no existe.");
        }
        regionRepository.deleteById(id);
    }
}

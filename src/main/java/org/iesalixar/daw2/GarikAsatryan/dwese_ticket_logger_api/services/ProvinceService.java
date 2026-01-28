package org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.services;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.dtos.ProvinceCreateDTO;
import org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.dtos.ProvinceDTO;
import org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.entities.Province;
import org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.entities.Region;
import org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.mappers.ProvinceMapper;
import org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.repositories.ProvinceRepository;
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
public class ProvinceService {
    private static final Logger logger = LoggerFactory.getLogger(ProvinceService.class);

    private final ProvinceRepository provinceRepository;
    private final RegionRepository regionRepository;
    private final ProvinceMapper provinceMapper;
    private final MessageSource messageSource;

    public Page<ProvinceDTO> getAllProvinces(Pageable pageable) {
        logger.info("Solicitando todas las provincias con paginación: página {}, tamaño {}", pageable.getPageNumber(), pageable.getPageSize());
        try {
            Page<Province> provincePage = provinceRepository.findAll(pageable);
            logger.info("Se han encontrado {} provincias en la página actual.", provincePage.getNumberOfElements());
            return provincePage.map(provinceMapper::toDTO);
        } catch (Exception e) {
            logger.error("Error al obtener todas las provincias: {}", e.getMessage());
            throw e;
        }
    }

    public Optional<ProvinceDTO> getProvinceById(Long id) {
        try {
            logger.info("Buscando provincia con ID {}", id);
            return provinceRepository.findById(id).map(provinceMapper::toDTO);
        } catch (Exception e) {
            logger.error("Error al buscar provincia con ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Error al buscar la provincia.", e);
        }
    }

    public ProvinceDTO createProvince(ProvinceCreateDTO provinceCreateDTO, Locale locale) {
        if (provinceRepository.existsProvinceByCode(provinceCreateDTO.getCode())) {
            String errorMessage = messageSource.getMessage("msg.province.code-exists", null, locale);
            throw new IllegalArgumentException(errorMessage);
        }

        Region region = regionRepository.findById(provinceCreateDTO.getRegionId())
                .orElseThrow(() -> new IllegalArgumentException("La región especificada no existe."));

        Province province = provinceMapper.toEntity(provinceCreateDTO);
        province.setRegion(region);

        Province savedProvince = provinceRepository.save(province);
        logger.info("Provincia {} creada con éxito", savedProvince.getName());

        return provinceMapper.toDTO(savedProvince);
    }

    public ProvinceDTO updateProvince(
            Long id,
            @Valid ProvinceCreateDTO provinceCreateDTO,
            Locale locale
    ) {
        Province existingProvince = provinceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("La provincia no existe."));

        // Validar código duplicado en otros IDs
        if (provinceRepository.existsProvinceByCodeAndNotId(provinceCreateDTO.getCode(), id)) {
            String errorMessage = messageSource.getMessage("msg.province.code-exists", null, locale);
            throw new IllegalArgumentException(errorMessage);
        }

        Region region = regionRepository.findById(provinceCreateDTO.getRegionId())
                .orElseThrow(() -> new IllegalArgumentException("La región especificada no existe."));

        existingProvince.setCode(provinceCreateDTO.getCode());
        existingProvince.setName(provinceCreateDTO.getName());
        existingProvince.setRegion(region);

        Province updatedProvince = provinceRepository.save(existingProvince);
        logger.info("Provincia con ID {} actualizada", id);

        return provinceMapper.toDTO(updatedProvince);
    }

    public void deleteProvince(Long id) {
        logger.info("Buscando provincia con la ID {}", id);

        if (!provinceRepository.existsById(id)) {
            throw new IllegalArgumentException("La provincia no existe.");
        }

        provinceRepository.deleteById(id);
        logger.info("Provincia con ID {} eliminada", id);
    }
}

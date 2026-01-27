package org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.mappers;

import lombok.RequiredArgsConstructor;
import org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.dtos.ProvinceCreateDTO;
import org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.dtos.ProvinceDTO;
import org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.entities.Province;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProvinceMapper {
    private final RegionMapper regionMapper;

    public ProvinceDTO toDTO(Province province) {
        ProvinceDTO dto = new ProvinceDTO();
        dto.setId(province.getId());
        dto.setCode(province.getCode());
        dto.setName(province.getName());
        dto.setRegion(regionMapper.toDTO((province.getRegion())));
        return dto;
    }

    public Province toEntity(ProvinceDTO dto) {
        Province province = new Province();
        province.setId(dto.getId());
        province.setCode(dto.getCode());
        province.setName(dto.getName());
        province.setRegion(regionMapper.toEntity(dto.getRegion()));
        return province;
    }

    public Province toEntity(ProvinceCreateDTO createDTO) {
        Province province = new Province();
        province.setCode(createDTO.getCode());
        province.setName(createDTO.getName());
        return province;
    }
}

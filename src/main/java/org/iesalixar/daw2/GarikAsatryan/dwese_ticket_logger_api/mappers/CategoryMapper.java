package org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.mappers;

import org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.dtos.CategoryDTO;
import org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.entities.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryDTO toDTO(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        return dto;
    }

    public Category toEntity(CategoryDTO dto) {
        Category category = new Category();
        category.setId(dto.getId());
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        return category;
    }
}

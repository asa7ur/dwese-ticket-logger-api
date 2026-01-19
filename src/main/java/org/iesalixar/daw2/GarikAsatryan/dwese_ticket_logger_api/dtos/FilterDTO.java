package org.iesalixar.daw2.GarikAsatryan.dwese_ticket_logger_api.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilterDTO {
    private int page;
    private int itemsPerPage;
    private String order;
    private String orderBy;
    private String search;
    private int totalPages;
}

package softuni.ivasi.mofa.sales.service;

import softuni.ivasi.mofa.sales.models.entity.TicketType;

import java.math.BigDecimal;

public interface SalesService {
    BigDecimal getSalesByType(TicketType type);
}

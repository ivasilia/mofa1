package softuni.ivasi.mofa.sales.models.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import softuni.ivasi.mofa.sales.models.entity.TicketType;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketServiceModel {

    private String id;
    private TicketType ticketType;
    private BigDecimal price;
}

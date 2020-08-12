package softuni.ivasi.mofa.sales.service.impl;

import org.springframework.stereotype.Service;
import softuni.ivasi.mofa.sales.models.entity.TicketType;
import softuni.ivasi.mofa.sales.repo.SalesRepo;
import softuni.ivasi.mofa.sales.service.SalesService;

import java.math.BigDecimal;

@Service
public class SalesServiceImpl implements SalesService {
    private final SalesRepo salesRepo;

    public SalesServiceImpl(SalesRepo salesRepo) {
        this.salesRepo = salesRepo;
    }

    @Override
    public BigDecimal getSalesByType(TicketType type) {
        return this.salesRepo.findByType(type).getAmount();
    }
}

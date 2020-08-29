package softuni.ivasi.mofa.sales.models.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import softuni.ivasi.mofa.users.models.entities.UserEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "ticket_type")
    private TicketType ticketType;

    @NotNull
    private BigDecimal price;

    private boolean sold = false;

    private LocalDateTime soldOn;

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity user;

    public Ticket (TicketType ticketType, BigDecimal price) {
        this.ticketType = ticketType;
        this.price = price;
    }

    public void setPrice(BigDecimal price) {

        if (price.doubleValue() < 10.0 || price.doubleValue() > 30.0) {
            throw new IllegalArgumentException(
                    "Price should be in range between 10.0 and 30.0 euros");
        }
        this.price = price;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
        this.soldOn = LocalDateTime.now();
    }
}

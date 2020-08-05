package softuni.ivasi.mofa.collections.models.entities;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import softuni.ivasi.mofa.projects.models.entity.Project;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Item {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @NonNull
    @Length(min = 5, message = " Item name must contain minimum 5 characters")
    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false,columnDefinition = "VARCHAR(255) DEFAULT 'anonymous'")
    private String author;

    private String description;

    @Column(nullable = false)
    private String imageUrl;

    @ManyToOne()
    private Department department;

    private LocalDateTime registeredOn = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private ItemStatus status = ItemStatus.EXPOSED;

    @ManyToOne(cascade = CascadeType.ALL)
    private Notes notes;

    private double rating;

    public Item (String name, String author, String imageUrl) {
        this.name = name;
        this.author = author;
        this.imageUrl = imageUrl;
    }

    @ManyToOne()
    @JoinColumn(name = "project_id")
    private Project project;

    private BigDecimal insurancePrice;
}

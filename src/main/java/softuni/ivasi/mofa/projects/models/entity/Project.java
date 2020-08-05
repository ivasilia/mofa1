package softuni.ivasi.mofa.projects.models.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "projects")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @NonNull
    @Length(min = 5, max = 80, message = "Project name must contain between 5 and 80 characters")
    @Column(unique = true, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private ProjectStatus projectStatus;

    private String imageUrl;
    private String description;
    private String location;
    private String venue;
    private LocalDate startingDate;
    private LocalDate endDate;
    private BigDecimal price;

    public Project(String name, String imageUrl, String location, String venue) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.location = location;
        this.venue = venue;
    }
}


package softuni.ivasi.mofa.collections.models.entities;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Department {     // Department is used for Collections in the templates

    public Department(String name, String description, String imageUrl, String abbreviation) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.abbreviation = abbreviation;
    }

    public Department(String id, String name, String abbreviation) {
        this.id = id;
        this.name = name;
        this.abbreviation = abbreviation;
    }

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @NonNull
    @Length(min = 5, max = 80, message = "Department name must contain between 5 and 80 characters")
    @Column(unique = true, nullable = false)
    private String name;

    private String abbreviation;

    private String description;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Transient
    private String imageUrlThumbnail;

    @OneToMany(mappedBy = "department")
    private Set<Item> items = new HashSet<>();


    public String getImageUrlThumbnail() {
        return imageUrl.substring(0, imageUrl.length()-4) + "_thumbnail" + ".jpg";
    }
}

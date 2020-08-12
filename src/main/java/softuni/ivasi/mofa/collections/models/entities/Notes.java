package softuni.ivasi.mofa.collections.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notes {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String text;

    @Column(name = "author", nullable = false)
    private String author;

    private String itemId;

    public Notes(String text, String author) {
        this.text = text;
        this.author = author;
    }
}
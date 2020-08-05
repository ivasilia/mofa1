package softuni.ivasi.mofa.collections.models.bindings;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemAddBinding {

    @Length(min = 5, message = "Item name must contain minimum 5 characters")
    private String name;

    @Length(min = 3, message = "Author must contain minimum 3 characters")
    private String author;

    private String description;

    @Length(min = 3, message = "Enter valid imageUrl")
    private String imageUrl;

    private String departmentId;
}

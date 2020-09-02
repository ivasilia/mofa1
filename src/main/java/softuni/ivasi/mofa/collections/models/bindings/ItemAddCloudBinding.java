package softuni.ivasi.mofa.collections.models.bindings;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
public class ItemAddCloudBinding {
    @Length(min = 5, message = "Item name must contain minimum 5 characters")
    private String name;

    @Length(min = 3, message = "Author must contain minimum 3 characters")
    private String author;

    private String description;

    private MultipartFile image;

    private String departmentId;

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public MultipartFile getImage() {
        return image;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public ItemAddCloudBinding setName(String name) {
        this.name = name;
        return this;
    }

    public ItemAddCloudBinding setAuthor(String author) {
        this.author = author;
        return this;
    }

    public ItemAddCloudBinding setDescription(String description) {
        this.description = description;
        return this;
    }

    public ItemAddCloudBinding setImage(MultipartFile image) {
        this.image = image;
        return this;
    }
}

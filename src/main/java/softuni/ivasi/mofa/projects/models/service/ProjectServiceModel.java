package softuni.ivasi.mofa.projects.models.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import softuni.ivasi.mofa.collections.models.entities.Item;
import softuni.ivasi.mofa.projects.models.entity.ProjectStatus;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectServiceModel {
    @NotNull
    private String id;

    @NotNull
    @Length(min = 5, max = 80, message = "Project name must contain between 5 and 80 characters")
    private String name;
    private ProjectStatus projectStatus;
    private String imageUrl;
    private String imageUrlThumbnail;
    private String description;
    private String location;
    private String venue;
    private LocalDate startingDate;
    private LocalDate endDate;
    private Set<Item> items;

    public String getImageUrlThumbnail() {
        return imageUrl.substring(0, imageUrl.length()-4) + "_thumbnail" + ".jpg";
    }
}

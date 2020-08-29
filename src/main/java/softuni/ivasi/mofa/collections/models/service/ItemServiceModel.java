package softuni.ivasi.mofa.collections.models.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import softuni.ivasi.mofa.collections.models.entities.ItemStatus;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemServiceModel {
    private String id;
    private String name;
    private String author;
    private String description;
    private String imageUrl;
    private String imageUrlThumbnail;
    private LocalDateTime registeredOn;
    private ItemStatus status;
    private String departmentId;
    private double rating;

    public String getImageUrlThumbnail() {
        return imageUrl.substring(0, imageUrl.length()-4) + "_thumbnail" + ".jpg";
    }
}

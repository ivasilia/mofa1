package softuni.ivasi.mofa.collections.models.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import softuni.ivasi.mofa.collections.models.entities.Item;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentServiceModel {
    private String name;
    private String description;
    private String imageUrl;
    private Set<Item> items;
}

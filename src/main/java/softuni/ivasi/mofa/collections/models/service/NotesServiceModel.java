package softuni.ivasi.mofa.collections.models.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotesServiceModel {

    private String text;
    private String author;
    private String itemId;

}

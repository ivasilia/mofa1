package softuni.ivasi.mofa.collections.models.bindings;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotesAddBinding {

    @NotNull(message = "Please enter your valuable text!")
    private String text;

    @NotNull(message = "Please enter valid author!")
    private String author;

    @NotNull
    private String itemId;
}

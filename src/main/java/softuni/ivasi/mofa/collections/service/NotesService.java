package softuni.ivasi.mofa.collections.service;

import softuni.ivasi.mofa.collections.models.bindings.NotesAddBinding;
import softuni.ivasi.mofa.collections.models.service.NotesServiceModel;

public interface NotesService {
    void save(NotesAddBinding notes);

    NotesServiceModel getByItemId(String id);
}

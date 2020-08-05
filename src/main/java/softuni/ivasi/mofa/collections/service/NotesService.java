package softuni.ivasi.mofa.collections.service;

import softuni.ivasi.mofa.collections.models.bindings.NotesAddBinding;

public interface NotesService {
    void add(NotesAddBinding notes);
    void save(NotesAddBinding notes);
}

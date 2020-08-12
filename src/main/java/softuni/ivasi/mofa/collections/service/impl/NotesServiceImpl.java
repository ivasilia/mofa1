package softuni.ivasi.mofa.collections.service.impl;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.ivasi.mofa.collections.models.bindings.NotesAddBinding;
import softuni.ivasi.mofa.collections.models.entities.Notes;
import softuni.ivasi.mofa.collections.models.service.NotesServiceModel;
import softuni.ivasi.mofa.collections.repo.NotesRepo;
import softuni.ivasi.mofa.collections.service.NotesService;

@Service
public class NotesServiceImpl implements NotesService {
    private final NotesRepo notesRepo;
    private final ModelMapper modelMapper;

    @Autowired
    public NotesServiceImpl(NotesRepo notesRepo, ModelMapper modelMapper) {
        this.notesRepo = notesRepo;
        this.modelMapper = modelMapper;
    }


    @Override
    public void save(NotesAddBinding notes) {
        this.notesRepo.save(
                this.modelMapper.map(
                        notes, Notes.class));
    }

    @Override
    public NotesServiceModel getByItemId(String id) {
        Notes notes = this.notesRepo.findByItemId(id);
        NotesServiceModel notesServiceModel = null;
        if (notes != null) {
            notesServiceModel = this.modelMapper.map(
                    notes, NotesServiceModel.class);
        }
        return notesServiceModel;
    }
}

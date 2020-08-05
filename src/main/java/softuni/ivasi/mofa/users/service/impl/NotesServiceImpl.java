package softuni.ivasi.mofa.users.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.ivasi.mofa.collections.models.bindings.NotesAddBinding;
import softuni.ivasi.mofa.collections.models.entities.Notes;
import softuni.ivasi.mofa.collections.repo.NotesRepo;
import softuni.ivasi.mofa.collections.service.NotesService;

import javax.validation.Valid;

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
    public void add(@Valid NotesAddBinding notes) {
        this.notesRepo.saveAndFlush(
                this.modelMapper.map(notes, Notes.class));
    }

    @Override
    public void save(NotesAddBinding notesAddBinding) {
        Notes notes = this.modelMapper.map(notesAddBinding, Notes.class);
        this.notesRepo.saveAndFlush(notes);
    }
}

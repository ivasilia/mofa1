package softuni.ivasi.mofa.collections.service.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import softuni.ivasi.mofa.collections.models.bindings.NotesAddBinding;
import softuni.ivasi.mofa.collections.models.entities.Notes;
import softuni.ivasi.mofa.collections.models.service.NotesServiceModel;
import softuni.ivasi.mofa.collections.repo.NotesRepo;

import static org.mockito.Mockito.*;

@SpringBootTest
class NotesServiceImplTest {
    private Notes testNotes;
    private NotesAddBinding notesAddBinding;
    private NotesRepo mockedNotesRepo;
    private String TEST_NOTES_ID;

    @Autowired
    private ModelMapper modelMapper;

    @Mock
    private NotesServiceImpl mockedNotesService;

    @BeforeEach
    void setUp() {
        this.testNotes = new Notes() {{
            setAuthor("Hammurapi");
            setItemId("0001");
        }};
        this.mockedNotesRepo = mock(NotesRepo.class);
        this.mockedNotesService.saveEntity(this.testNotes);
    }

    @AfterEach
    void tearDown() {
        this.mockedNotesRepo.deleteAll();
    }


    @Test
    void save() {
        this.notesAddBinding = new NotesAddBinding();
        this.notesAddBinding.setAuthor("Herodotus");

        this.mockedNotesService.save(this.notesAddBinding);

        verify(this.mockedNotesService, times(1))
                .save(this.notesAddBinding);
    }


    @Test
    void getByItemId() {
        when(this.mockedNotesService.getByItemId("0001"))
                .thenReturn(this.modelMapper.map(
                        this.testNotes, NotesServiceModel.class));
    }
}
package softuni.ivasi.mofa.collections.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import softuni.ivasi.mofa.collections.models.bindings.ItemAddBinding;
import softuni.ivasi.mofa.collections.models.bindings.NotesAddBinding;
import softuni.ivasi.mofa.collections.models.entities.Department;
import softuni.ivasi.mofa.collections.models.entities.Item;
import softuni.ivasi.mofa.collections.models.entities.Notes;
import softuni.ivasi.mofa.collections.models.service.ItemServiceModel;
import softuni.ivasi.mofa.collections.repo.ItemRepo;
import softuni.ivasi.mofa.collections.service.DepartmentService;
import softuni.ivasi.mofa.collections.service.ItemService;
import softuni.ivasi.mofa.projects.service.ProjectService;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepo itemRepo;
    private final DepartmentService departmentService;
    private final ProjectService projectService;
    private final ModelMapper modelMapper;

    @Autowired
    public ItemServiceImpl(ItemRepo itemRepo,
                           DepartmentService departmentService,
                           ProjectService projectService,
                           ModelMapper modelMapper) {
        this.itemRepo = itemRepo;
        this.departmentService = departmentService;
        this.projectService = projectService;
        this.modelMapper = modelMapper;
    }

    @Override
    @Scheduled(cron = "0 0 0 * * *")
    public void initialize() throws IOException {

        InputStream resource = new ClassPathResource(
                "files/items.txt").getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(resource));
        String line = reader.readLine();

        while (line != null) {
            String[] itemInput = line.split(": ");
            Item item = this.itemRepo.findByName(itemInput[0]);
            if (item != null) {
                line = reader.readLine();
                continue;
            } else {
                registerItem(itemInput);
            }
            line = reader.readLine();
        }
    }


    public void registerItem(String[] itemInput) {
        Department department = this.departmentService.getByAbbreviation(itemInput[2]);
        Item item = new Item(itemInput[0], itemInput[1], itemInput[3]);
        //TODO Cloudinary image upload
//        MultipartFile image = itemInput[3];
//        String url = this.cloudinaryService.uploadImage();
        item.setDepartment(department);
        this.itemRepo.save(item);
    }

    public void registerItem(ItemAddBinding itemAddBinding) {
        Item item = this.modelMapper.map(itemAddBinding, Item.class);

        Department department = this.departmentService.getByAbbreviation(
                itemAddBinding.getDepartmentId());
        item.setDepartment(department);

        this.itemRepo.save(item);
    }

    @Override
    public ItemServiceModel getById(String id) {
        ItemServiceModel item = this.modelMapper.map(
                this.itemRepo.findById(id).orElse(null),
                ItemServiceModel.class);
        return item;
    }


    @Override
    public ItemServiceModel finByName(String name) {
        return this.modelMapper.map(
                this.itemRepo.findByName(name),
                ItemServiceModel.class);
    }

    @Override
    public Item getEntityById(String id) {
        return this.itemRepo.findById(id).orElseThrow(() ->
                new EntityNotFoundException());
    }


    @Override
    @Cacheable("allItems")
    public List<Item> getAllItems() {
        return this.itemRepo.findAll();
    }

    @Override
    public List<ItemServiceModel> getAllProjectItems(String projectId) {
        return this.itemRepo.findAllByProjectId(projectId)
                .stream()
                .map(i -> this.modelMapper.map(
                        i, ItemServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
//    @Cacheable("allItemDTOs")
    public List<ItemServiceModel> getAllItemDtos() {
        return this.itemRepo.findAll().stream()
                .map(i -> this.modelMapper.map(i, ItemServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemServiceModel> getAllItemsExceptDeptId(String id) {

        List<String> ids = this.departmentService.findByDepartmentIdIsNot(id);
        List<ItemServiceModel> items = new ArrayList<>();

        for (String i: ids) {
            for (Item item: this.itemRepo.findAllByDepartmentId(i))

                items.add(this.modelMapper.map(
                        item, ItemServiceModel.class));
        }
        return items;
    }

    @Override
    public void save(@Valid ItemServiceModel item) {
        this.itemRepo.save(
                this.modelMapper.map(
                        item, Item.class));
    }

    @Override
    public void saveEntity(Item item) {
        this.itemRepo.save(item);
    }


    //  Operations on Items

    @Override
    public void addDepartmentToItem(String name, String id) {
        Item item = this.itemRepo.findByName(name);
        item.setDepartment(this.departmentService.getDepartmentById(id));
        this.itemRepo.saveAndFlush(item);
    }

    @Override   
    public void saveNotesToItem(String id, NotesAddBinding notesAddBinding) {
        Notes notes = this.modelMapper.map(notesAddBinding, Notes.class);
        Item item = this.itemRepo.findById(id).orElse(null);
        // TODO ??? Entity not found exception ???
        if (item != null && item.getNotes() == null) {
            item.setNotes(notes);
            this.itemRepo.saveAndFlush(item);
        }
    }

    @Override
    public boolean addProjectToItem(String name, String projectId) {
        boolean assigned = false;
        Item item = this.itemRepo.findByName(name);
        if (item != null) {
            assigned = true;
            item.setProject(this.projectService.getEntityById(projectId));
        }

        this.itemRepo.saveAndFlush(item);

        return assigned;
    }

    @Override
    public void increaseRating(String itemId) {
        Item item = this.itemRepo.findById(itemId).orElse(null);
        if (item != null) {
            item.setRating(item.getRating() + 1);
        }
        this.itemRepo.saveAndFlush(item);
    }
}

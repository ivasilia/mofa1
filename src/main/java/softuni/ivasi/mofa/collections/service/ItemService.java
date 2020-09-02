package softuni.ivasi.mofa.collections.service;

import softuni.ivasi.mofa.collections.models.bindings.ItemAddBinding;
import softuni.ivasi.mofa.collections.models.bindings.ItemAddCloudBinding;
import softuni.ivasi.mofa.collections.models.bindings.NotesAddBinding;
import softuni.ivasi.mofa.collections.models.entities.Item;
import softuni.ivasi.mofa.collections.models.service.ItemServiceModel;

import java.io.IOException;
import java.util.List;

public interface ItemService {

    void initialize() throws IOException;

    ItemServiceModel getById(String id);

    ItemServiceModel finByName(String name);

    Item getEntityById(String id);

    List<Item> getAllItems();

    List<ItemServiceModel> getAllProjectItems(String projectId);

//    void registerItem(ItemAddBinding itemAddBinding);

    void registerItem(String[] itemInput);

    void save(ItemServiceModel item);

    void saveEntity(Item item);

    void saveNotesToItem(String id, NotesAddBinding notes);

    List<ItemServiceModel> getAllItemDtos();

    List<ItemServiceModel> getAllItemsExceptDeptId(String id);

    void addDepartmentToItem(String name, String id);

    boolean addProjectToItem(String name, String projectId);

    void increaseRating(String itemId);

    void addItem(ItemAddCloudBinding itemAddCloudBinding) throws IOException;
}

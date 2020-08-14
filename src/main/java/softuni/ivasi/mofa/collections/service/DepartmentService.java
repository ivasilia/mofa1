package softuni.ivasi.mofa.collections.service;

import softuni.ivasi.mofa.collections.models.entities.Department;
import softuni.ivasi.mofa.collections.models.service.DepartmentServiceModel;

import java.io.IOException;
import java.util.List;

public interface DepartmentService {
    void initialize() throws IOException;
    Department getByName(String name);

    List<Department> findAll();

    Department getDepartmentById(String id);

    DepartmentServiceModel getById(String id);

    Department getByAbbreviation(String s);

    List<String> findByDepartmentIdIsNot(String id);

    void save(Department dept1);

    void clearAll();
}

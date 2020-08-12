package softuni.ivasi.mofa.collections.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import softuni.ivasi.mofa.collections.models.entities.Department;
import softuni.ivasi.mofa.collections.models.service.DepartmentServiceModel;
import softuni.ivasi.mofa.collections.repo.DepartmentRepo;
import softuni.ivasi.mofa.collections.service.DepartmentService;

import javax.persistence.EntityNotFoundException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepo departmentRepo;
    private final ModelMapper modelMapper;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepo departmentRepo, ModelMapper modelMapper) {
        this.departmentRepo = departmentRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public void initialize() throws IOException {
        InputStream resource = new ClassPathResource(
                "files/departments.txt")
                .getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(resource));
        String line = reader.readLine();
        while (line != null) {
            String[] departmentInput = line.split(": ");
            Department department = new Department(
                    departmentInput[0], departmentInput[1], departmentInput[2], departmentInput[3]);
            this.departmentRepo.save(department);
            line = reader.readLine();
        }
    }

    @Override
    public Department getByName(String name) {
        return this.departmentRepo.findByName(name);
    }

    @Override
    @Cacheable("allDepartments")
    public List<Department> findAll() {
        return this.departmentRepo.findAll();
    }

    @Override
    public Department getDepartmentById(String id) {
        return this.departmentRepo.findById(id).orElseThrow(() ->
                new EntityNotFoundException("No department with such ID"));
    }

    @Override
    public DepartmentServiceModel getById(String id) {
        Department dept = this.departmentRepo.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Collection  not found"));
        return this.modelMapper.map(dept, DepartmentServiceModel.class);
    }

    @Override
    public Department getByAbbreviation(String s) {
        return this.departmentRepo.findByAbbreviation(s).orElse(null);
    }

    @Override
    public List<String> findByDepartmentIdIsNot(String id) {
        return this.departmentRepo.findByIdIsNot(id).stream()
                .map(department -> department.getId())
                .collect(Collectors.toList());
    }

    @Override
    public void save(Department dept) {
        this.departmentRepo.save(dept);
    }
}


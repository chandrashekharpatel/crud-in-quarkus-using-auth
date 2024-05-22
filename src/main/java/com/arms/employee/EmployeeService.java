package com.arms.employee;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;


@ApplicationScoped
public class EmployeeService {

    private static final String UPLOAD_DIR = "D:\\Quarkus\\React-Application\\productmanagementsystem\\src\\images";

    @Inject
    EmployeeRepository employeeRepository;

    @Transactional
    public String saveFile(byte[] fileData, String fileName) throws IOException {
        String uniqueFileName = UUID.randomUUID().toString() + "-" + fileName;
        Path targetLocation = Paths.get(UPLOAD_DIR, uniqueFileName);
        Files.createDirectories(targetLocation.getParent());
        Files.write(targetLocation, fileData);
        return targetLocation.toString();
    }

    @Transactional
    public Employee saveEmployeeData(Employee employee) {
        employeeRepository.persist(employee);
        return employee;
    }

    public String saveImage(InputStream images, String fileNames) throws IOException {
        String uniqueFileName = UUID.randomUUID().toString() + "-" + fileNames;
        Path targetLocation = Paths.get(UPLOAD_DIR, uniqueFileName);
        Files.copy(images, targetLocation);
        return targetLocation.toString();
    }
}



package com.arms.employee;

import java.util.List;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;


@Path("/employees")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmployeeResource {

    @Inject
    EmployeeRepository employeeRepository;

    @Inject
    EmployeeService employeeService;

    @GET
    @Transactional
    public List<Employee> getAllEmployees() {
        return employeeRepository.listAll();
    }

    @GET
    @Path("/{id}")
    @Transactional
    public Employee getEmployeeById(@PathParam("id") Long id) {
        return employeeRepository.findById(id);
    }

    @POST
    @Transactional
    public Employee addEmployee(Employee employee) {
        employeeRepository.persist(employee);
        return employee;
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Employee updateEmployee(@PathParam("id") Long id, Employee employee) {
        Employee existingEmployee = employeeRepository.findById(id);
        existingEmployee.setName(employee.getName());
        existingEmployee.setAddress(employee.getAddress());
        existingEmployee.setImage(employee.getImage());
        return existingEmployee;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void deleteEmployee(@PathParam("id") Long id) {
        employeeRepository.deleteById(id);
    }
    
//     @POST
//     @Path("/saveEmployee")
//     @Consumes(MediaType.MULTIPART_FORM_DATA)
//     @Transactional
//    public Response saveEmployee(@MultipartForm Map<String, List<String>> formData) {
//     String imageUrl = formData.get("image").get(0); // Assuming "image" is the key for the file
//     // Extract other form data as needed
//     Employee employee = new Employee();
//     employee.setImage(imageUrl);
//     employeeService.saveEmployeeData(employee);
//     return Response.status(Response.Status.CREATED).entity(employee).build();
// }

// @POST
// @Path("/saveEmployee")
// @Consumes(MediaType.MULTIPART_FORM_DATA)
// @Transactional
// public Response saveEmployee(@MultipartForm EmployeeFormData formData) {
//     try {
//         // Save the image and get its URL
//         String imageUrl = employeeService.saveImage(formData.getImage(), formData.getName() + ".jpg");

//         // Create an Employee entity and set its fields
//         Employee employee = new Employee();
//         employee.setName(formData.getName());
//        // employee.setAddress(formData.getAddress());
//         employee.setImage(imageUrl);

//         // Persist the Employee entity
//         employeeRepository.persist(employee);

//         // Return a success response
//         return Response.status(Response.Status.CREATED).entity(employee).build();
//     } catch (IOException e) {
//         // Return an error response if there's an exception
//         return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to save image").build();
//     }
// }

}

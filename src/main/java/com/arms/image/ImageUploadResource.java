package com.arms.image;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import com.arms.image.model.PetRequestBody;
import com.arms.image.model.PetRequestDTO;
import com.arms.image.model.PetResponseDTO;
import com.arms.image.repository.PetImageRepository;
import com.arms.image.resources.PetImageResource;
import com.arms.image.services.ImageUploadService;


@Path("/image")
public class ImageUploadResource {

    @Inject
    PetImageRepository petImageRepository;

    @Inject
    PetImageResource petImageResource;

    @Inject
    ImageUploadService imageUploadService;

    @POST
    @Transactional
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadFile(
        @MultipartForm PetRequestBody petRequestBody) throws IOException {
        
        PetRequestDTO petRequestDTO = imageUploadService.createRequest(petRequestBody);

        if(petImageRepository.isPersistent(petRequestDTO)){
            petRequestDTO.setFileName(petRequestDTO.getFileName() + UUID.randomUUID());
        }

        petImageRepository.persist(petRequestDTO);
        
        PetResponseDTO petResponseDTO = imageUploadService.createResponse(petRequestDTO);
        
        return petImageRepository
                .findByIdOptional(petRequestDTO.getId())
                .map(pet -> Response.ok(petResponseDTO).build())
                .orElse(Response.status(Status.NOT_FOUND).build());
    }

    @GET
    @Path("/download/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response downloadFile(
        @PathParam("id") Long id) throws IOException {

        if(petImageResource.get(id) == null){
            return Response.ok(Response.Status.BAD_REQUEST).build();
        }

        PetRequestDTO petRequestDTO = petImageRepository.findById(id);
        
        PetResponseDTO petResponseDTO = imageUploadService.createResponse(petRequestDTO);

        return petImageRepository
                .findByIdOptional(petRequestDTO.getId())
                .map(pet -> Response.ok(petResponseDTO).build())
                .orElse(Response.status(Status.NOT_FOUND).build());
    }


    @GET
    @Path("/name/{fileName}")
    @Produces(MediaType.TEXT_PLAIN)
    public Long downloadFileByName(
        @PathParam("fileName") String fileName){

        String fileNameWithoutSpace = fileName.replace(" ", "").trim();
        
        PetRequestDTO petRequestDTO = petImageRepository.findByName(fileNameWithoutSpace);
        
        return petRequestDTO.getId();
    }

    @PUT
    @Path("/update/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response updateFile(
        @PathParam("id") Long id,
        @MultipartForm PetRequestBody petRequestBody) throws IOException {
        
        if(petImageResource.get(id) == null){
            return Response.ok(Response.Status.BAD_REQUEST).build();
        }
        
        PetRequestDTO petRequestDTO = petImageRepository.findById(id);
        PetResponseDTO petResponseDTO = imageUploadService.createResponse(petRequestDTO);

        petImageRepository.persist(petRequestDTO);
        
        PetRequestDTO petRequestDTO2 = petImageResource.update(id, petRequestDTO);

        return petImageRepository
                .findByIdOptional(petRequestDTO2.getId())
                .map(pet -> Response.ok(petResponseDTO).build())
                .orElse(Response.status(Status.NOT_FOUND).build());

    }

    @DELETE
    @Transactional
    @Path("/delete/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public String deleteFile(
        @PathParam("id") Long id) {
        
        try {
            if(petImageResource.get(id) == null){
                return "Esta imagem now exist.";
            }
            PetRequestDTO petRequestDTO = petImageRepository.findById(id);
            petImageRepository.delete(petRequestDTO);
            return "Imagem deletada com sucessful!";
        
        } catch (Exception e) {
            e.printStackTrace();
            return "Error not deleted image! ";
        }

    }
    
    // @GET
    // @Path("/all")
    // @Produces(MediaType.APPLICATION_JSON)
    // public Response getAllImages() {
    //     try {
    //         List<PetResponseDTO> allImages = petImageRepository.getAllImages(); // Implement this method in your repository

    //         if (allImages.isEmpty()) {
    //             return Response.status(Status.NOT_FOUND).entity("No images found").build();
    //         } else {
    //             return Response.ok(allImages).build();
    //         }
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Error fetching images").build();
    //     }
    // }
    // @GET
    // @Path("/filename/{filename}")
    // @Produces("images/*") 
    // public Response getImageDataByFilename(@PathParam("filename") String filename) {
    //     try {
           
    //         PetRequestDTO petRequestDTO = petImageRepository.findByName(filename);

    //         if (petRequestDTO != null) {
    //             byte[] imageData = petRequestDTO.getFile();
    //             return Response.ok(imageData).build();
    //         } else {
    //             return Response.status(Response.Status.NOT_FOUND).entity("Image not found").build();
    //         }
    //     } catch (Exception e) {
    //         return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
    //                        .entity("Error fetching image data: " + e.getMessage())
    //                        .build();
    //     }
    // }
    

}

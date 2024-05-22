package com.arms.image.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.zip.GZIPOutputStream;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.xml.bind.DatatypeConverter;

import org.apache.commons.io.IOUtils;

import com.arms.image.model.PetRequestBody;
import com.arms.image.model.PetRequestDTO;
import com.arms.image.model.PetResponseDTO;

@ApplicationScoped
public class ImageUploadService {
    
    public PetResponseDTO createResponse(PetRequestDTO petRequestDTO){
        
        PetResponseDTO petResponseDTO = new PetResponseDTO();

        byte[] decodedAsIs = Base64.getUrlDecoder().decode(petRequestDTO.getFile());

        String decoded = DatatypeConverter.printBase64Binary(decodedAsIs);

        String imageFile = new String("data:image/"+ petRequestDTO.getFileExtension() +";base64," + decoded);
        
        petResponseDTO.setId(petRequestDTO.getId());
        petResponseDTO.setFile(imageFile);
        petResponseDTO.setFileName(petRequestDTO.getFileName());
        petResponseDTO.setFileExtension(petRequestDTO.getFileExtension());

        return petResponseDTO;
    }

    public PetRequestDTO createRequest(PetRequestBody petRequestBody) throws IOException{

        PetRequestDTO petRequestDTO = new PetRequestDTO();

        try {
          byte[] compressed = IOUtils.toByteArray(petRequestBody.getFile());
          byte[] encodedAsBytes = Base64.getUrlEncoder().encode(compressed);
                    
          petRequestDTO.setFile(encodedAsBytes);
          petRequestDTO.setFileName(petRequestBody.getFileName());
          petRequestDTO.setFileExtension(petRequestBody.getFileExtension());
          
        } catch (Exception e) {
          e.printStackTrace();
        }

        return petRequestDTO;
    }
    
    public static byte[] compress(final byte[] bytesImage) throws IOException {
        if ((bytesImage == null) || (bytesImage.toString().length() == 0)) {
          return null;
        }
        ByteArrayOutputStream obj = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(obj);
        gzip.write(bytesImage);
        gzip.flush();
        gzip.close();
        return obj.toByteArray();
      }
}

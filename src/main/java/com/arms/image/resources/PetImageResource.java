package com.arms.image.resources;
import com.arms.image.model.*;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.rest.data.panache.ResourceProperties;

import com.arms.image.repository.PetImageRepository;
import jakarta.enterprise.context.*;;;

// // @ApplicationScoped
// // public class PetImageResource implements PanacheRepository<PetImageRepository> {
// //     // Custom repository methods can be added here if needed
// // }



// @ResourceProperties(exposed = false)
// public interface PetImageResource extends PanacheRepositoryResource<PetImageRepository, PetRequestDTO, Long>  {
    
// }


@ResourceProperties(exposed = false)
public interface PetImageResource extends io.quarkus.hibernate.orm.rest.data.panache.PanacheRepositoryResource<PetImageRepository, PetRequestDTO, Long>  {
    
}

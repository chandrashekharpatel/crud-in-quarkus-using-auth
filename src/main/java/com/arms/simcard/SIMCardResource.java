package com.arms.simcard;

import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Status;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import com.arms.citizen.*;

@Path("/simcards")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SIMCardResource {

    @Inject
    SIMCardRepository simCardRepository;
    @Inject
    CitizenService citizenService;
    @Inject
    SIMCardService simCardService;
    @Inject
    CitizenRepository citizenRepository;
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSimcards() {
        try {
            List<SIMCardResponseDTO> allSimcards = simCardService.getAllSimcards();
    
            if (allSimcards.isEmpty()) {
                return Response.status(Status.STATUS_UNKNOWN).entity("No SIM cards found").build();
            } else {
                return Response.ok(allSimcards).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Status.STATUS_UNKNOWN).entity("Error fetching SIM cards").build();
        }
    }
    
    // @GET
    // public List<SIMCard> getAllSIMCards() {
    //     return simCardRepository.listAll();
    // }

    // @GET
    // @Path("/{id}")
    // public SIMCard getSIMCardById(@PathParam("id") Long id) {
    //     return simCardRepository.findById(id);
    // }
    // Import the Item entity

    @GET
    @Path("/{id}")
    public SIMCard getSIMCardById(@PathParam("id") Long id) {
        SIMCard simCard = simCardRepository.findById(id);
        if (simCard != null) {
            Citizen citizen = citizenRepository.findById(id); // Assuming citizenId is the foreign key column name
            simCard.setCitizen(citizen);
        }
        return simCard;
    }


    // @POST
    // @Transactional
    // public Response addSIMCards(SIMCard simCard) {
    //     simCardRepository.persist(simCard);
    //     return Response.status(Response.Status.CREATED).entity(simCard).build();
    // }

// @PUT
// @Path("/{citizenId}/{simCardId}")
// public Response updateSIMCard(SIMCard simCard, 
//                                @PathParam("citizenId") Long citizenId, 
//                                @PathParam("simCardId") Long simCardId) {
//     try {
//         simCardService.updateSIMCard(simCard, citizenId, simCardId);
//         return Response.status(Response.Status.OK).entity(simCard).build();
//     } catch (EntityNotFoundException e) {
//         return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
//     } catch (Exception e) {
//         return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error occurred while updating SIMCard: " + e.getMessage()).build();
//     }
// }
@PUT
@Path("/{id}")
@Transactional
public Response updateSIMCard(@PathParam("id") Long id, @QueryParam("number") String number,
                              @QueryParam("provider") String provider, @QueryParam("isActive") boolean isActive,
                              @QueryParam("citizenId") Long citizenId) {
    SIMCard existingSIMCard = simCardRepository.findById(id);
    if (existingSIMCard != null) {
        // Update SIMCard details
        if (number != null) {
            existingSIMCard.setNumber(number);
        }
        if (provider != null) {
            existingSIMCard.setProvider(provider);
        }
        existingSIMCard.setActive(isActive);

        // Update associated Citizen ID if provided
        if (citizenId != null) {
            existingSIMCard.getCitizen().setId(citizenId);
        }

        simCardRepository.persist(existingSIMCard);
        return Response.ok(existingSIMCard).build();
    } else {
        return Response.status(Response.Status.NOT_FOUND).entity("SIMCard with ID " + id + " not found").build();
    }
}


// @PUT
// @Path("/{id}")
// @Transactional
// public Response updateSIMCard(@PathParam("id") Long id, SIMCard updatedSIMCard) {
//     SIMCard existingSIMCard = simCardRepository.findById(id);
//     if (existingSIMCard != null) {
//         // Update SIMCard details
//         existingSIMCard.setNumber(updatedSIMCard.getNumber());
//         existingSIMCard.setProvider(updatedSIMCard.getProvider());
//         existingSIMCard.setActive(updatedSIMCard.isActive());

//         // Update associated Citizen if needed
//         Citizen updatedCitizen = updatedSIMCard.getCitizen();
//         if (updatedCitizen != null) {
//             existingSIMCard.setCitizen(updatedCitizen);
//         }

//         simCardRepository.persist(existingSIMCard);
//         return Response.ok(existingSIMCard).build();
//     } else {
//         return Response.status(Response.Status.NOT_FOUND).entity("SIMCard with ID " + id + " not found").build();
//     }
// }

    // @PUT
    // @Path("/{id}")
    // @Transactional
    // public Response updateSIMCard(@PathParam("id") Long id, SIMCard updatedSIMCard) {
    //     SIMCard existingSIMCard = simCardRepository.findById(id);
    //     if (existingSIMCard != null) {
    //         existingSIMCard.setNumber(updatedSIMCard.getNumber());
    //         existingSIMCard.setProvider(updatedSIMCard.getProvider());
    //         existingSIMCard.setActive(updatedSIMCard.isActive());
    //         existingSIMCard.setCitizen(updatedSIMCard.getCitizen());
    //         simCardRepository.persist(existingSIMCard);
    //         return Response.ok(existingSIMCard).build();
    //     } else {
    //         return Response.status(Response.Status.NOT_FOUND).entity("SIMCard with ID " + id + " not found").build();
    //     }
    // }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteSIMCard(@PathParam("id") Long id) {
        SIMCard existingSIMCard = simCardRepository.findById(id);
        if (existingSIMCard != null) {
            simCardRepository.delete(existingSIMCard);
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("SIMCard with ID " + id + " not found").build();
        }
    }
    // @POST
    // @Path("/save")
    // @Transactional
    // public Response addSIMCard(SIMCard simCard) {
    //     Long citizenId = simCard.getCitizen().getId();

    //     Citizen citizen = citizenService.getCitizenById(citizenId);
    //     simCard.setCitizen(citizen);
    //     simCardRepository.persist(simCard);
        
    //     return Response.status(Response.Status.CREATED).entity(simCard).build();
    // }
   @POST
    @Path("/{citizenId}")
    public Response addSIMCard(SIMCard simCard, @PathParam("citizenId") Long citizenId) {
        try {
            simCardService.addSIMCard(simCard, citizenId);
            return Response.status(Response.Status.CREATED).entity(simCard).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error occurred while creating SIMCard: " + e.getMessage()).build();
        }
    }
}


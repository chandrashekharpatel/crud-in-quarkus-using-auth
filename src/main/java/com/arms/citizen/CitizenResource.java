package com.arms.citizen;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/citizens")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CitizenResource {

    @Inject
    CitizenRepository citizenRepository;

    @GET
    public List<Citizen> getAllCitizens() {
        return citizenRepository.listAll();
    }

    @GET
    @Path("/{id}")
    public Response getCitizenById(@PathParam("id") Long id) {
        try {
            Citizen citizen = citizenRepository.findByIdWithSimCards(id);
            if (citizen != null) {
                return Response.ok(citizen).build();
            } else {
                return Response.status(404).entity("Citizen with ID " + id + " not found").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(500).entity("Error fetching citizen").build();
        }
    }

    @POST
    @Transactional
    public Response addCitizen(Citizen citizen) {
        citizenRepository.persist(citizen);
        return Response.status(Response.Status.CREATED).entity(citizen).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response updateCitizen(@PathParam("id") Long id, Citizen updatedCitizen) {
        Citizen existingCitizen = citizenRepository.findById(id);
        if (existingCitizen != null) {
            existingCitizen.setName(updatedCitizen.getName());
            existingCitizen.setGender(updatedCitizen.getGender());
            citizenRepository.persist(existingCitizen);
            return Response.ok(existingCitizen).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Citizen with ID " + id + " not found").build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteCitizen(@PathParam("id") Long id) {
        Citizen existingCitizen = citizenRepository.findById(id);
        if (existingCitizen != null) {
            citizenRepository.delete(existingCitizen);
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Citizen with ID " + id + " not found").build();
        }
    }
}


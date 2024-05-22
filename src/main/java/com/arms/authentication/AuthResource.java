 package com.arms.authentication;
import jakarta.inject.Inject;
import jakarta.persistence.EntityExistsException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.HashSet;
import java.util.Set;

import jakarta.json.Json;
import jakarta.json.JsonObject;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    AuthService authService;
    // @Inject
    // HttpServletRequest request;

    @Inject
    JwtService jwtService;
    @Inject
    UserRepository userRepository;

    @POST
    @Path("/login")
    public Response login(LoginRequest request) {
        // Validate user credentials
        if (!authService.isValidUser(request.getUsername(), request.getPassword())) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid credentials").build();
        }

        //Generate JWT token
        // Set<String> roles = new HashSet<>(); // Add user roles here
        // String token = jwtService.generateToken(request.getUsername(), roles);
        // return Response.ok(new AuthResponse(token)).build();
        return Response.ok(new AuthResponse("Login Success!")).build();
    }


    @POST
    @Path("/register")
    @Consumes("application/json")
    @Transactional
    public Response registerUser(User user) {
        userRepository.persist(user);
        return Response.status(Response.Status.CREATED).entity(user).build();
    }


}

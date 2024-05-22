// package com.arms.authentication;

// import jakarta.annotation.Priority;
// import jakarta.enterprise.context.ApplicationScoped;
// import jakarta.inject.Inject;
// import jakarta.ws.rs.Priorities;
// import jakarta.ws.rs.container.ContainerRequestContext;
// import jakarta.ws.rs.container.ContainerRequestFilter;
// import jakarta.ws.rs.core.Response;
// import jakarta.ws.rs.ext.Provider;
// import io.quarkus.security.ForbiddenException;
// import io.quarkus.security.identity.SecurityIdentity;

// @Provider
// @ApplicationScoped
// @Priority(Priorities.AUTHENTICATION)
// public class AuthenticationFilter implements ContainerRequestFilter {

//     @Inject
//     SecurityIdentity securityIdentity;

//     @Override
//     public void filter(ContainerRequestContext requestContext) {
//         if (!securityIdentity.isAnonymous()) {
//             // User is authenticated, continue with the request
//             return;
//         } else {
//             // User is not authenticated, abort the request with an unauthorized response
//             throw new ForbiddenException("Unauthorized access");
//         }
//     }
// }

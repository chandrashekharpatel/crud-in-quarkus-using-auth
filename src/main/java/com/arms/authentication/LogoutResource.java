// package com.arms.authentication;

// import jakarta.inject.Inject;
// import jakarta.ws.rs.GET;
// import jakarta.ws.rs.Path;
// import jakarta.ws.rs.core.Response;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpSession;

// @Path("/logout")
// public class LogoutResource {

//     @Inject
//     HttpServletRequest request;

//     @GET
//     public Response logout() {
//         HttpSession session = request.getSession(false);
//         if (session != null) {
//             session.invalidate();
//         }
//         return Response.ok().build();
//     }
// }

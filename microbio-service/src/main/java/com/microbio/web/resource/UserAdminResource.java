package com.microbio.web.resource;

import com.microbio.infrastructure.security.KeycloakAdminClient;
import com.microbio.infrastructure.security.KeycloakTokenProvider;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/admin/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserAdminResource {

    @Inject @RestClient KeycloakAdminClient kc;
    @Inject
    KeycloakTokenProvider tokenProvider; // clase que obtiene token admin (client_credentials)

    @POST
    @RolesAllowed("LAB_ADMIN")
    public void createUser(KeycloakAdminClient.KeycloakUserPayload payload) {
        String token = tokenProvider.getAdminToken(); // "Bearer xxx"
        kc.createUser(token, tokenProvider.realm(), payload);
    }
}

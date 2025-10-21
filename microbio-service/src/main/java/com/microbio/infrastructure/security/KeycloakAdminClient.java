package com.microbio.infrastructure.security;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "keycloak-admin")
@Path("/admin/realms/{realm}")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface KeycloakAdminClient {

    @POST @Path("/users")
    void createUser(@HeaderParam("Authorization") String bearer,
                    @PathParam("realm") String realm, KeycloakUserPayload user);

    class KeycloakUserPayload {
        public String username; public String firstName; public String lastName; public String email; public boolean enabled=true;
        public java.util.Map<String,Object> credentials;
    }
}

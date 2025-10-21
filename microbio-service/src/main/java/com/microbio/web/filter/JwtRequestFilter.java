package com.microbio.web.filter;

import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class JwtRequestFilter implements ContainerRequestFilter {

    private static final Logger LOG = Logger.getLogger(JwtRequestFilter.class);

    @Override
    public void filter(ContainerRequestContext requestContext) {
        String authHeader = requestContext.getHeaderString("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            LOG.warn("Solicitud sin token JWT válido: " + requestContext.getUriInfo().getPath());
        } else {
            String token = authHeader.substring("Bearer ".length());
            LOG.debug("Token JWT recibido: " + token.substring(0, Math.min(15, token.length())) + "...");
        }
    }
}

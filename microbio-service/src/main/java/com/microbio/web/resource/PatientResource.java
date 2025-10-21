package com.microbio.web.resource;

import com.microbio.application.port.out.PatientRepository;
import com.microbio.domain.model.Patient;
import io.reactivex.rxjava3.core.Maybe;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/patients")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PatientResource {
    @Inject PatientRepository repo;

    @GET @Path("/{id}")
    @RolesAllowed({"LAB_TECH","LAB_ADMIN","DOCTOR","AUDITOR"})
    public Maybe<Patient> get(@PathParam("id") String id) {
        return repo.findById(id);
    }
}

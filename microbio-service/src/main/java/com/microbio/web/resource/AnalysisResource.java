package com.microbio.web.resource;

import com.microbio.application.usecase.GetLatestAnalysisUseCase;
import com.microbio.application.usecase.RegisterAnalysisUseCase;
import com.microbio.web.dto.*;
import com.microbio.web.mapper.DtoDomainMapper;
import io.reactivex.rxjava3.core.*;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

@Path("/analyses")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AnalysisResource {

    @Inject RegisterAnalysisUseCase register;
    @Inject GetLatestAnalysisUseCase latest;
    @Inject DtoDomainMapper mapper;

    @POST
    @RolesAllowed({"LAB_TECH","LAB_ADMIN"})
    public Single<Response> create(@Valid AnalysisCreateDTO dto, @Context UriInfo uri) {
        return register.execute(mapper.toDomain(dto))
                .map(a -> {
                    latest.evictCache(a.getPatientId()); // invalida caché
                    return Response.created(uri.getAbsolutePathBuilder().path(a.getId()).build())
                            .entity(mapper.toDto(a)).build();
                });
    }

    @GET @Path("/patients/{patientId}/latest")
    @RolesAllowed({"LAB_TECH","LAB_ADMIN","DOCTOR","AUDITOR"})
    public Maybe<AnalysisDTO> getLatest(@PathParam("patientId") String patientId) {
        return latest.execute(patientId).map(mapper::toDto);
    }
}

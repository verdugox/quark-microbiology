package com.microbio.web.resource;

import com.microbio.web.response.ApiResponse;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.time.LocalDateTime;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class ApplicationResource {

    @GET
    @Path("/status")
    public Uni<ApiResponse<String>> status() {
        return Uni.createFrom().item(() ->
                ApiResponse.ok("🧬 Microbio Service activo - " + LocalDateTime.now())
        );
    }

    @GET
    @Path("/info")
    public Uni<ApiResponse<String>> info() {
        String info = """
                Microbio Service v1.0.0
                Arquitectura: Hexagonal + CQRS + Reactive
                Motor: Quarkus 3.28 + Java 21
                Estado: 🟢 Operativo
                """;
        return Uni.createFrom().item(() -> ApiResponse.ok(info));
    }
}

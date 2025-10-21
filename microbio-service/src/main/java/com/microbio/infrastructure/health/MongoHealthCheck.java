package com.microbio.infrastructure.health;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import com.mongodb.client.MongoClient;
import jakarta.inject.Inject;

@Readiness
@ApplicationScoped
public class MongoHealthCheck implements HealthCheck {

    @Inject
    MongoClient client;

    @Override
    public HealthCheckResponse call() {
        try {
            client.getDatabase("admin").runCommand(new org.bson.Document("ping", 1));
            return HealthCheckResponse.builder()
                    .name("mongo")
                    .up()
                    .build();
        } catch (Exception e) {
            return HealthCheckResponse.builder()
                    .name("mongo")
                    .down()
                    .withData("error", e.getMessage())
                    .build();
        }
    }
}

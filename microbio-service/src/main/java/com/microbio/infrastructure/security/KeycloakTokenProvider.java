package com.microbio.infrastructure.security;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import io.vertx.mutiny.ext.web.client.WebClient;
import io.vertx.mutiny.core.Vertx;

@ApplicationScoped
public class KeycloakTokenProvider {
    @Inject Vertx vertx;
    @ConfigProperty(name="kc.realm") String realm;
    @ConfigProperty(name="kc.token.url") String tokenUrl;
    @ConfigProperty(name="kc.client.id") String clientId;
    @ConfigProperty(name="kc.client.secret") String clientSecret;

    public String realm(){ return realm; }

    public String getAdminToken() {
        var client = WebClient.create(vertx);
        var resp = client.postAbs(tokenUrl)
                .sendForm(io.vertx.mutiny.core.MultiMap.caseInsensitiveMultiMap()
                        .add("grant_type","client_credentials")
                        .add("client_id",clientId)
                        .add("client_secret",clientSecret))
                .await().indefinitely();
        String access = resp.bodyAsJsonObject().getString("access_token");
        return "Bearer " + access;
    }
}

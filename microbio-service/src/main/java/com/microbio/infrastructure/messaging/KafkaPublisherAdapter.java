package com.microbio.infrastructure.messaging;

import com.microbio.domain.model.Analysis;
import io.reactivex.rxjava3.core.Completable;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@ApplicationScoped
public class KafkaPublisherAdapter {

    @Channel("analysis-events-out")
    Emitter<Analysis> emitter;

    public Completable publish(Analysis a) {
        return Completable.fromAction(() -> emitter.send(a));
    }
}

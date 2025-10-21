package com.microbio.application.port.out;

import com.microbio.domain.model.Analysis;
import io.reactivex.rxjava3.core.Completable;

public interface Notifier {
    Completable emailAnalysisCreated(Analysis a);
    Completable publishAnalysisEvent(Analysis a); // Kafka/Event Hubs
}

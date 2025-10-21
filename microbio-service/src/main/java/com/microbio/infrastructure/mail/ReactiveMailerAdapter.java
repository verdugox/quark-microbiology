package com.microbio.infrastructure.mail;

import com.microbio.application.port.out.Notifier;
import com.microbio.domain.model.Analysis;
import com.microbio.infrastructure.messaging.KafkaPublisherAdapter;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.reactive.ReactiveMailer;
import io.reactivex.rxjava3.core.Completable;
import io.smallrye.mutiny.converters.uni.UniRx3Converters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Timeout;

@ApplicationScoped
public class ReactiveMailerAdapter implements Notifier {

    @Inject
    ReactiveMailer mailer;

    @Inject
    KafkaPublisherAdapter kafka;

    @Override
    @Timeout(2000)
    @CircuitBreaker(requestVolumeThreshold = 5, failureRatio = 0.5, delay = 1000)
    public Completable emailAnalysisCreated(Analysis a) {
        String body = "Nuevo análisis para paciente " + a.getPatientId() + " (runId=" + a.getRunId() + ")";
        Mail mail = Mail.withText("lab@microbio.com", "Análisis registrado", body)
                .addTo("notifier@microbio.com");

        return mailer.send(mail)
                .convert().with(UniRx3Converters.toCompletable());
    }

    @Override
    public Completable publishAnalysisEvent(Analysis a) {
        return kafka.publish(a);
    }
}

package io.microsamples.r.client;

import io.rsocket.core.Resume;
import lombok.SneakyThrows;
import lombok.Value;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.rsocket.server.RSocketServerCustomizer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.time.Instant;

@SpringBootApplication
@Log4j2
public class ClientApplication {

    @Autowired
    private RSocketRequester chachkiesRequester;

    @SneakyThrows
    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
        System.in.read();
    }



    @Bean
    RSocketRequester rSocketRequester(RSocketRequester.Builder builder) {
        return builder
                .rsocketConnector(connector -> {
                       connector.reconnect(Retry.backoff(10, Duration.ofMillis(500)));
//                    connector.keepAlive(Duration.ofSeconds(10), Duration.ofSeconds(60));
                })
                .tcp("localhost", 9091);
    }

    @Bean
    ApplicationListener<ApplicationReadyEvent> ready(RSocketRequester chachkiesRequester) {
        return event ->
                chachkiesRequester
                        .route("chachkies")
                        .data(Mono.justOrEmpty(Instant.now()))
                        .retrieveFlux(Chachkie.class)
                        .subscribe(gr -> log.info("🎟 response: " + gr.toString()));
    }

}

@Value
class Chachkie {
    Double lat, lon;
    Instant when;
}

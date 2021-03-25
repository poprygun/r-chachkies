package io.microsamples.r.client;

import io.rsocket.routing.client.spring.RoutingRSocketRequester;
import lombok.SneakyThrows;
import lombok.Value;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.time.Instant;

@SpringBootApplication
@Log4j2
public class ClientApplication {

    private RoutingRSocketRequester chachkiesRequester;

    public ClientApplication(RoutingRSocketRequester chachkiesRequester) {
        this.chachkiesRequester = chachkiesRequester;
    }

    @SneakyThrows
    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
        System.in.read();
    }

    @Bean
    ApplicationListener<ApplicationReadyEvent> ready() {
        return event ->
                chachkiesRequester
                        .route("chachkies")
                        .address("server")
                        .data(Mono.justOrEmpty(Instant.now()))
                        .retrieveFlux(Chachkie.class)
                        .timeout(Duration.ofSeconds(10))
                        .retryWhen(Retry.backoff(10, Duration.ofSeconds(3)))
                        .subscribe(gr -> log.info("🎟 response: " + gr.toString()));
    }
}

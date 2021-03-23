package io.microsamples.r.server;

import lombok.Value;
import lombok.extern.log4j.Log4j2;
import org.jeasy.random.EasyRandom;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.Stream;

@SpringBootApplication
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

}

@Controller
@Log4j2
class GreetingController {

    private final EasyRandom chachkieFactory = new EasyRandom();

    @MessageMapping("chachkies")
    Flux<Chachkie> chachkies(Mono<Instant> chRequest) {
        return chRequest
                .doOnNext(r -> log.info(" 🀄 " + r.toString()))
                .flatMapMany(when -> this.someChachkies());
    }

    private Flux<Chachkie> someChachkies() {

        return Flux
                .fromStream(Stream.generate(() -> chachkieFactory.nextObject(Chachkie.class)))
                .delayElements(Duration.ofSeconds(1));
    }
}

@Value
class Chachkie {
    Double lat, lon;
    Instant when;
}
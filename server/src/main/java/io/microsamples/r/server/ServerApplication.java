package io.microsamples.r.server;

import lombok.Value;
import lombok.extern.log4j.Log4j2;
import org.jeasy.random.EasyRandom;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;

@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

}

@Controller
@Log4j2
class GreetingController {

	private EasyRandom chachkieFactory = new EasyRandom();

	@MessageMapping("chachkies")
	Flux<Chachkie> chachkies(
			RSocketRequester clientRSocketConnection,
			Mono<Instant> chRequest) {
		return chRequest
				.flatMapMany(when -> this.greet(clientRSocketConnection, when));
	}

	private Flux<Chachkie> greet(
			RSocketRequester clientRSocketConnection, Instant requests) {

//		var clientHealth = clientRSocketConnection
//				.route("health")
//				.retrieveFlux(ClientHealthState.class)
//				.filter(chs -> !chs.isHealthy())
//				.doOnNext(chs -> log.info("not healthy! "));

		var chackies = Flux
				.fromStream(chachkieFactory.objects(Chachkie.class, 13))
				.take(2)
				.delayElements(Duration.ofSeconds(1));

//		return chackies.takeUntilOther(clientHealth);
		return chackies;
	}
}

//@Value
//class ChachkieRequest {
//	Instant when;
//}

@Value
class Chachkie {
	Double lat, lon;
	Instant when;
}
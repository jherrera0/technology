package reactive_backend.technology.application.http.handler;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface IAbilityHandler {
    Mono<ServerResponse> addAbility(ServerRequest serverRequest);

    Mono<ServerResponse> getAllTechnologiesByAbilityId(ServerRequest request);
}

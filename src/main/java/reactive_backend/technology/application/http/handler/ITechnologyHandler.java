package reactive_backend.technology.application.http.handler;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface ITechnologyHandler {
    Mono<ServerResponse> saveTechnology(ServerRequest request);
}

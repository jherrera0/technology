package reactive_backend.technology.application.http.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactive_backend.technology.application.http.dto.TechnologyDtoRequest;
import reactive_backend.technology.application.http.mapper.ITechnologyDtoMapper;
import reactive_backend.technology.domain.api.ITechnologyServicePort;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class TechnologyHandler implements ITechnologyHandler {

    private final ITechnologyServicePort technologyServicePort;
    private final ITechnologyDtoMapper technologyDtoMapper;

    @Override
    public Mono<ServerResponse> saveTechnology(ServerRequest request) {
        return request.bodyToMono(TechnologyDtoRequest.class)
                .doOnNext(dto -> log.info("Datos recibidos desde Postman: {}", dto))
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Request body cannot be empty")))
                .map(technologyDtoMapper::toTechnology)
                .doOnNext(tech -> log.info("Datos mapeados a dominio: {}", tech))
                .flatMap(technologyServicePort::saveTechnology)
                .map(technologyDtoMapper::toDtoResponse)
                .doOnNext(response -> log.info("Datos a devolver en respuesta: {}", response))
                .flatMap(response -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(response)
                )
                .onErrorResume(error -> {
                    log.error("Error al procesar la solicitud: {}", error.getMessage());
                    return ServerResponse.badRequest()
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(Map.of(
                                    "error", error.getMessage(),
                                    "timestamp", Instant.now()
                            ));
                });
    }
}
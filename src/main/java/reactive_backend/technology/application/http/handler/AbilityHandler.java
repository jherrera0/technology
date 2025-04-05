package reactive_backend.technology.application.http.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactive_backend.technology.application.http.dto.request.AddAbilityDtoRequest;
import reactive_backend.technology.domain.api.IAbilityServicePort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class AbilityHandler implements IAbilityHandler {
    private final IAbilityServicePort abilityServicePort;

    @Override
    public Mono<ServerResponse> addAbility(ServerRequest request) {
        return request.bodyToMono(AddAbilityDtoRequest.class)
                .doOnNext(dto -> log.info("Datos recibidos desde Postman para agregar habilidad: {}", dto))
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Request body cannot be empty")))
                .flatMapMany(dto -> abilityServicePort.addAbility(dto.getAbilityId(), dto.getTechnologies()))
                .collectList() // Convertir el Flux a una lista para devolverlo como un único response
                .doOnNext(list -> log.info("Datos a devolver en respuesta de la habilidad agregada: {}", list))
                .flatMap(list -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(list)
                )
                .onErrorResume(error -> {
                    log.error("Error al procesar la solicitud de agregar habilidad: {}", error.getMessage());
                    return ServerResponse.badRequest()
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(Map.of(
                                    "error", error.getMessage(),
                                    "timestamp", Instant.now()
                            ));
                });
    }
}

package reactive_backend.technology.application.http.handler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.mock.web.reactive.function.server.MockServerRequest;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactive_backend.technology.application.http.dto.request.AddAbilityDtoRequest;
import reactive_backend.technology.domain.api.IAbilityServicePort;
import reactive_backend.technology.domain.model.Ability;
import reactive_backend.technology.domain.model.Technology;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class AbilityHandlerTest {

    private IAbilityServicePort abilityServicePort;
    private AbilityHandler abilityHandler;

    @BeforeEach
    void setUp() {
        abilityServicePort = Mockito.mock(IAbilityServicePort.class);
        abilityHandler = new AbilityHandler(abilityServicePort);
    }

    @Test
    void addAbilitySuccessfully() {
        AddAbilityDtoRequest dtoRequest = new AddAbilityDtoRequest(1,
                List.of(new Technology(1,"tech1","tech1"),
                        new Technology(2,"tech2","tech2")));
        ServerRequest request = MockServerRequest.builder()
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(dtoRequest));

        when(abilityServicePort.addAbility(any(), any())).thenReturn(Flux.just(new Ability(1,1,1)));

        Mono<ServerResponse> response = abilityHandler.addAbility(request);

        StepVerifier.create(response)
                .expectNextMatches(serverResponse -> serverResponse.statusCode().is2xxSuccessful())
                .verifyComplete();
    }

    @Test
    void addAbilityWithEmptyRequestBody() {
        ServerRequest request = MockServerRequest.builder()
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.empty());
        Mono<ServerResponse> response = abilityHandler.addAbility(request);

        StepVerifier.create(response)
                .expectNextMatches(serverResponse -> serverResponse.statusCode().is4xxClientError())
                .verifyComplete();
    }

    @Test
    void addAbilityWithErrorInService() {
        AddAbilityDtoRequest dtoRequest = new AddAbilityDtoRequest(1,
                List.of(new Technology(1,"tech1","tech1"),
                        new Technology(2,"tech2","tech2")));
        ServerRequest request = MockServerRequest.builder()
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(dtoRequest));

        when(abilityServicePort.addAbility(any(), any())).thenReturn(Flux.error(new RuntimeException("Service error")));

        Mono<ServerResponse> response = abilityHandler.addAbility(request);

        StepVerifier.create(response)
                .expectNextMatches(serverResponse -> serverResponse.statusCode().is4xxClientError())
                .verifyComplete();
    }

}
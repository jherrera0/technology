package reactive_backend.technology.application.http.handler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunctions;
import reactive_backend.technology.application.http.dto.TechnologyDtoRequest;
import reactive_backend.technology.application.http.dto.TechnologyDtoResponse;
import reactive_backend.technology.application.http.mapper.ITechnologyDtoMapper;
import reactive_backend.technology.domain.api.ITechnologyServicePort;
import reactive_backend.technology.domain.model.Technology;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TechnologyHandlerTest {

    @Mock
    private ITechnologyServicePort technologyServicePort;

    @Mock
    private ITechnologyDtoMapper technologyDtoMapper;

    @InjectMocks
    private TechnologyHandler technologyHandler;

    private WebTestClient webTestClient;

    private TechnologyDtoRequest technologyDtoRequest;
    private Technology technology;
    private TechnologyDtoResponse technologyDtoResponse;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient.bindToRouterFunction(
                RouterFunctions.route(RequestPredicates.POST("/technology"), technologyHandler::saveTechnology)
        ).build();

        technologyDtoRequest = new TechnologyDtoRequest("Java", "Programming language");
        technology = new Technology(1, "Java", "Programming language");
        technologyDtoResponse = new TechnologyDtoResponse(1, "Java", "Programming language");
    }

    @Test
    void saveTechnology_WhenValidRequest_ShouldReturnOk() {
        when(technologyDtoMapper.toTechnology(technologyDtoRequest)).thenReturn(technology);
        when(technologyServicePort.saveTechnology(technology)).thenReturn(Mono.just(technology));
        when(technologyDtoMapper.toDtoResponse(technology)).thenReturn(technologyDtoResponse);

        webTestClient.post()
                .uri("/technology")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(technologyDtoRequest)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TechnologyDtoResponse.class)
                .isEqualTo(technologyDtoResponse);

        verify(technologyDtoMapper).toTechnology(technologyDtoRequest);
        verify(technologyServicePort).saveTechnology(technology);
        verify(technologyDtoMapper).toDtoResponse(technology);
    }

    @Test
    void saveTechnology_WhenRequestBodyIsEmpty_ShouldReturnBadRequest() {
        webTestClient.post()
                .uri("/technology")
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isBadRequest();
    }
}

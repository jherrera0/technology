package reactive_backend.technology.application.http.handler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunctions;
import reactive_backend.technology.application.http.dto.request.TechnologiesListDtoRequest;
import reactive_backend.technology.application.http.dto.request.TechnologyDtoRequest;
import reactive_backend.technology.application.http.dto.response.PageResponse;
import reactive_backend.technology.application.http.dto.response.TechnologyDtoResponse;
import reactive_backend.technology.application.http.mapper.IPageResponseMapper;
import reactive_backend.technology.application.http.mapper.ITechnologyDtoMapper;
import reactive_backend.technology.domain.api.ITechnologyServicePort;
import reactive_backend.technology.domain.model.PageCustom;
import reactive_backend.technology.domain.model.Technology;
import reactive_backend.technology.domain.util.ConstRoute;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TechnologyHandlerTest {

    @Mock
    private ITechnologyServicePort technologyServicePort;

    @Mock
    private ITechnologyDtoMapper technologyDtoMapper;

    @Mock
    private IPageResponseMapper pageResponseMapper;

    @InjectMocks
    private TechnologyHandler technologyHandler;

    private WebTestClient webTestClient;

    private TechnologyDtoRequest technologyDtoRequest;
    private Technology technology;
    private TechnologyDtoResponse technologyDtoResponse;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient.bindToRouterFunction(
                RouterFunctions.route(RequestPredicates.POST(ConstRoute.TECHNOLOGY_REST_ROUTE+
                        ConstRoute.CREATE_TECHNOLOGY_REST_ROUTE), technologyHandler::saveTechnology)
                        .andRoute(RequestPredicates.POST(ConstRoute.TECHNOLOGY_REST_ROUTE+
                                ConstRoute.LIST_TECHNOLOGY_REST_ROUTE), technologyHandler::listTechnology)
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
                .uri(ConstRoute.TECHNOLOGY_REST_ROUTE+
                        ConstRoute.CREATE_TECHNOLOGY_REST_ROUTE)
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
                .uri(ConstRoute.TECHNOLOGY_REST_ROUTE+
                        ConstRoute.CREATE_TECHNOLOGY_REST_ROUTE)
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void listTechnology_WhenValidRequest_ShouldReturnOk() {
        TechnologiesListDtoRequest dtoRequest = new TechnologiesListDtoRequest("asc", 10, 1);
        PageCustom<Technology> pageCustom = new PageCustom<>(
                0, 10, 1,
                List.of(new Technology(1, "Java", "Programming language"))
        );

        PageResponse<TechnologyDtoResponse> pageResponse = new PageResponse<>(
                0, 10, 1,
                List.of(new TechnologyDtoResponse(1, "Java", "Programming language"))
        );

        when(technologyServicePort.listTechnology(dtoRequest.getOrderDirection(), dtoRequest.getPageSize(),
                dtoRequest.getCurrentPage()))
                .thenReturn(Mono.just(pageCustom));
        when(pageResponseMapper.toPageResponse(pageCustom)).thenReturn(pageResponse);

        webTestClient.post()
                .uri("/technology/list")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dtoRequest)
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<PageResponse<TechnologyDtoResponse>>() {})
                .isEqualTo(pageResponse);

        verify(technologyServicePort).listTechnology(dtoRequest.getOrderDirection(), dtoRequest.getPageSize(), dtoRequest.getCurrentPage());
        verify(pageResponseMapper).toPageResponse(pageCustom);
    }


    @Test
    void listTechnology_WhenRequestBodyIsEmpty_ShouldReturnBadRequest() {
        webTestClient.post()
                .uri("/technology/list")
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void listTechnology_WhenOrderDirectionIsInvalid_ShouldReturnBadRequest() {
        TechnologiesListDtoRequest dtoRequest = new TechnologiesListDtoRequest("INVALID", 10, 1);

        webTestClient.post()
                .uri("/technology/list")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dtoRequest)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void listTechnology_WhenPageSizeIsInvalid_ShouldReturnBadRequest() {
        TechnologiesListDtoRequest dtoRequest = new TechnologiesListDtoRequest("asc", 0, 1);

        webTestClient.post()
                .uri("/technology/list")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dtoRequest)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void listTechnology_WhenCurrentPageIsInvalid_ShouldReturnBadRequest() {
        TechnologiesListDtoRequest dtoRequest = new TechnologiesListDtoRequest("asc", 10, -1);

        webTestClient.post()
                .uri("/technology/list")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dtoRequest)
                .exchange()
                .expectStatus().isBadRequest();
    }
}

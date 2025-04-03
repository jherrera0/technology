package reactive_backend.technology.domain.usecase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactive_backend.technology.domain.exception.ListTechnologyCurrentPageInvalidException;
import reactive_backend.technology.domain.exception.ListTechnologyOrderDirectionInvalidException;
import reactive_backend.technology.domain.exception.ListTechnologyPageSizeInvalidException;
import reactive_backend.technology.domain.exception.TechnologyAlreadyExistsException;
import reactive_backend.technology.domain.model.PageCustom;
import reactive_backend.technology.domain.model.Technology;
import reactive_backend.technology.domain.spi.ITechnologyPersistencePort;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TechnologyCaseTest {

    @Mock
    private ITechnologyPersistencePort technologyPersistencePort;

    @InjectMocks
    private TechnologyCase technologyCase;

    private Technology technology;

    @BeforeEach
    void setUp() {
        technology = new Technology(null,"Spring WebFlux", "A reactive programming framework for building web applications in Java.");
    }

    @Test
    void saveTechnology_WhenTechnologyDoesNotExist_ShouldSaveTechnology() {
        when(technologyPersistencePort.technologyExistsByName(technology.getName())).thenReturn(Mono.just(false));
        when(technologyPersistencePort.saveTechnology(technology)).thenReturn(Mono.just(technology));

        StepVerifier.create(technologyCase.saveTechnology(technology))
                .expectNext(technology)
                .verifyComplete();

        verify(technologyPersistencePort).technologyExistsByName(technology.getName());
        verify(technologyPersistencePort).saveTechnology(technology);
    }

    @Test
    void saveTechnology_WhenTechnologyExists_ShouldThrowException() {
        when(technologyPersistencePort.technologyExistsByName(technology.getName())).thenReturn(Mono.just(true));

        StepVerifier.create(technologyCase.saveTechnology(technology))
                .expectError(TechnologyAlreadyExistsException.class)
                .verify();

        verify(technologyPersistencePort).technologyExistsByName(technology.getName());
        verify(technologyPersistencePort, never()).saveTechnology(any());
    }

    @Test
    void listTechnology_ShouldReturnPageCustom_WhenValidParameters() {
        // Arrange
        String orderDirection = "asc";
        int pageSize = 5;
        int currentPage = 0;

        PageCustom<Technology> pageCustom = new PageCustom<>(0, 5, 1,
                List.of(new Technology(1, "Java","1")));
        when(technologyPersistencePort.getAllTechnologies(orderDirection, pageSize, currentPage))
                .thenReturn(Mono.just(pageCustom));

        // Act & Assert
        StepVerifier.create(technologyCase.listTechnology(orderDirection, pageSize, currentPage))
                .expectNext(pageCustom)
                .verifyComplete();

        verify(technologyPersistencePort, times(1)).getAllTechnologies(orderDirection, pageSize, currentPage);
    }

    @Test
    void listTechnology_ShouldThrowException_WhenInvalidOrderDirection() {
        // Arrange
        String invalidOrder = "INVALID";
        int pageSize = 5;
        int currentPage = 0;

        // Act & Assert
        StepVerifier.create(technologyCase.listTechnology(invalidOrder, pageSize, currentPage))
                .expectError(ListTechnologyOrderDirectionInvalidException.class)
                .verify();

        verify(technologyPersistencePort, never()).getAllTechnologies(anyString(), anyInt(), anyInt());
    }

    @Test
    void listTechnology_ShouldThrowException_WhenInvalidPageSize() {
        // Arrange
        String orderDirection = "asc";
        int invalidPageSize = 0;
        int currentPage = 0;

        // Act & Assert
        StepVerifier.create(technologyCase.listTechnology(orderDirection, invalidPageSize, currentPage))
                .expectError(ListTechnologyPageSizeInvalidException.class)
                .verify();

        verify(technologyPersistencePort, never()).getAllTechnologies(anyString(), anyInt(), anyInt());
    }

    @Test
    void listTechnology_ShouldThrowException_WhenNegativeCurrentPage() {
        // Arrange
        String orderDirection = "asc";
        int pageSize = 5;
        int invalidCurrentPage = -1;

        // Act & Assert
        StepVerifier.create(technologyCase.listTechnology(orderDirection, pageSize, invalidCurrentPage))
                .expectError(ListTechnologyCurrentPageInvalidException.class)
                .verify();

        verify(technologyPersistencePort, never()).getAllTechnologies(anyString(), anyInt(), anyInt());
    }
}

package reactive_backend.technology.domain.usecase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactive_backend.technology.domain.exception.TechnologyAlreadyExistsException;
import reactive_backend.technology.domain.model.Technology;
import reactive_backend.technology.domain.spi.ITechnologyPersistencePort;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

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
}

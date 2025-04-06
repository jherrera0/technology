package reactive_backend.technology.application.jpa.adapter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import reactive_backend.technology.application.jpa.entity.TechnologyEntity;
import reactive_backend.technology.application.jpa.mapper.ITechnologyEntityMapper;
import reactive_backend.technology.application.jpa.repository.ITechnologyRepository;
import reactive_backend.technology.domain.model.Technology;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TechnologyJpaAdapterTest {

    @Mock
    private ITechnologyRepository technologyRepository;

    @Mock
    private ITechnologyEntityMapper technologyEntityMapper;

    @InjectMocks
    private TechnologyJpaAdapter technologyJpaAdapter;

    private Technology technology;
    private TechnologyEntity technologyEntity;

    @BeforeEach
    void setUp() {
        technology = new Technology(1, "Java", "Programming language");
        technologyEntity = new TechnologyEntity(1, "Java", "Programming language");
    }

    @Test
    void saveTechnology_WhenValidTechnology_ShouldReturnSavedTechnology() {
        when(technologyEntityMapper.toEntity(technology)).thenReturn(technologyEntity);
        when(technologyRepository.save(technologyEntity)).thenReturn(Mono.just(technologyEntity));
        when(technologyEntityMapper.toDomain(technologyEntity)).thenReturn(technology);

        Mono<Technology> result = technologyJpaAdapter.saveTechnology(technology);

        assertEquals(technology, result.block());
        verify(technologyEntityMapper).toEntity(technology);
        verify(technologyRepository).save(technologyEntity);
        verify(technologyEntityMapper).toDomain(technologyEntity);
    }

    @Test
    void technologyExistsByName_WhenTechnologyExists_ShouldReturnTrue() {
        when(technologyRepository.existsByName("Java")).thenReturn(Mono.just(true));

        Mono<Boolean> result = technologyJpaAdapter.technologyExistsByName("Java");

        assertEquals(Boolean.TRUE, result.block());
        verify(technologyRepository).existsByName("Java");
    }

    @Test
    void technologyExistsByName_WhenTechnologyDoesNotExist_ShouldReturnFalse() {
        when(technologyRepository.existsByName("Python")).thenReturn(Mono.just(false));

        Mono<Boolean> result = technologyJpaAdapter.technologyExistsByName("Python");

        assertNotEquals(Boolean.TRUE, result.block());
        verify(technologyRepository).existsByName("Python");
    }
    @Test
    void getAllTechnologies_WhenValidParameters_ShouldReturnPageCustom() {
        String orderDirection = "asc";
        int pageSize = 10;
        int currentPage = 1;
        List<TechnologyEntity> technologyEntities = List.of(technologyEntity);
        List<Technology> technologies = List.of(technology);

        when(technologyRepository.findAllBy(any(Pageable.class))).thenReturn(Flux.fromIterable(technologyEntities));
        when(technologyRepository.count()).thenReturn(Mono.just((long) technologyEntities.size()));
        when(technologyEntityMapper.toDomainList(technologyEntities)).thenReturn(technologies);

        StepVerifier.create(technologyJpaAdapter.getAllTechnologies(orderDirection, pageSize, currentPage))
                .expectNextMatches(pageCustom -> pageCustom.getItems().equals(technologies))
                .verifyComplete();

        verify(technologyRepository).findAllBy(any(Pageable.class));
        verify(technologyRepository).count();
        verify(technologyEntityMapper).toDomainList(technologyEntities);
    }

    @Test
    void getAllTechnologiesByName_WhenNamesExist_ShouldReturnTechnologies() {
        List<String> names = List.of("Java", "Python");
        List<TechnologyEntity> technologyEntities = List.of(technologyEntity);
        List<Technology> technologies = List.of(technology);

        when(technologyRepository.findAllByNameIn(names)).thenReturn(Flux.fromIterable(technologyEntities));
        when(technologyEntityMapper.toDomainList(technologyEntities)).thenReturn(technologies);

        StepVerifier.create(technologyJpaAdapter.getAllTechnologiesByName(names))
                .expectNext(technologies)
                .verifyComplete();

        verify(technologyRepository).findAllByNameIn(names);
        verify(technologyEntityMapper).toDomainList(technologyEntities);
    }

}

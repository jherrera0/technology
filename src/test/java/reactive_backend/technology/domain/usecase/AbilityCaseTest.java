package reactive_backend.technology.domain.usecase;

import org.junit.jupiter.api.Test;
import reactive_backend.technology.domain.model.Ability;
import reactive_backend.technology.domain.model.Technology;
import reactive_backend.technology.domain.spi.IAbilityPersistencePort;
import reactive_backend.technology.domain.spi.ITechnologyPersistencePort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.Mockito.*;

class AbilityCaseTest {

    private final IAbilityPersistencePort abilityPersistencePort = mock(IAbilityPersistencePort.class);
    private final ITechnologyPersistencePort technologyPersistencePort = mock(ITechnologyPersistencePort.class);
    private final AbilityCase abilityCase = new AbilityCase(abilityPersistencePort, technologyPersistencePort);

    @Test
    void addAbility_ShouldReturnFluxOfAbilities_WhenTechnologiesAreValid() {
        List<Technology> technologies = List.of(
                new Technology(1, "Java", "A programming language."),
                new Technology(2, "Spring", "A framework for Java.")
        );
        List<Integer> technologyIds = List.of(1, 2);
        Ability ability = new Ability(1, 1, 1);

        when(abilityPersistencePort.addAbility(1, technologyIds)).thenReturn(Flux.just(ability));

        StepVerifier.create(abilityCase.addAbility(1, technologies))
                .expectNext(ability)
                .verifyComplete();

        verify(abilityPersistencePort).addAbility(1, technologyIds);
    }

    @Test
    void addAbility_ShouldReturnEmptyFlux_WhenTechnologiesListIsEmpty() {
        List<Technology> technologies = List.of();
        List<Integer> technologyIds = List.of();

        when(abilityPersistencePort.addAbility(1, technologyIds)).thenReturn(Flux.empty());

        StepVerifier.create(abilityCase.addAbility(1, technologies))
                .expectNextCount(0)
                .verifyComplete();

        verify(abilityPersistencePort).addAbility(1, technologyIds);
    }
    @Test
    void getAllTechnologiesByAbilityId_ShouldReturnTechnologies_WhenAbilityIdIsValid() {
        List<Integer> technologyIds = List.of(1, 2);
        List<Technology> technologies = List.of(
                new Technology(1, "Java", "A programming language."),
                new Technology(2, "Spring", "A framework for Java.")
        );

        when(abilityPersistencePort.getAllTechnologiesByAbilityId(1)).thenReturn(Mono.just(technologyIds));
        when(technologyPersistencePort.getTechnologiesById(technologyIds)).thenReturn(Mono.just(technologies));

        StepVerifier.create(abilityCase.getAllTechnologiesByAbilityId(1))
                .expectNext(technologies)
                .verifyComplete();

        verify(abilityPersistencePort).getAllTechnologiesByAbilityId(1);
        verify(technologyPersistencePort).getTechnologiesById(technologyIds);
    }

    @Test
    void getAllTechnologiesByAbilityId_ShouldReturnEmptyList_WhenAbilityIdIsInvalid() {
        when(abilityPersistencePort.getAllTechnologiesByAbilityId(99)).thenReturn(Mono.empty());

        StepVerifier.create(abilityCase.getAllTechnologiesByAbilityId(99))
                .expectNextCount(0)
                .verifyComplete();

        verify(abilityPersistencePort).getAllTechnologiesByAbilityId(99);
        verify(technologyPersistencePort, never()).getTechnologiesById(anyList());
    }
}
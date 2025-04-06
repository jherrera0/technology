package reactive_backend.technology.domain.usecase;

import org.junit.jupiter.api.Test;
import reactive_backend.technology.domain.model.Ability;
import reactive_backend.technology.domain.model.Technology;
import reactive_backend.technology.domain.spi.IAbilityPersistencePort;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.Mockito.*;

class AbilityCaseTest {

    private final IAbilityPersistencePort abilityPersistencePort = mock(IAbilityPersistencePort.class);
    private final AbilityCase abilityCase = new AbilityCase(abilityPersistencePort);

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
}
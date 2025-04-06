package reactive_backend.technology.application.jpa.adapter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactive_backend.technology.application.jpa.entity.AbilityEntity;
import reactive_backend.technology.application.jpa.mapper.IAbilityEntityMapper;
import reactive_backend.technology.application.jpa.repository.IAbilityRepository;
import reactive_backend.technology.domain.model.Ability;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AbilityJpaAdapterTest {

    @Mock
    private IAbilityRepository abilityRepository;

    @Mock
    private IAbilityEntityMapper abilityEntityMapper;

    @InjectMocks
    private AbilityJpaAdapter abilityJpaAdapter;

    @BeforeEach
    void setUp() {
        abilityJpaAdapter = new AbilityJpaAdapter(abilityRepository, abilityEntityMapper);
    }

    @Test
    void addAbilitySuccessfully() {
        List<Integer> technologyIds = List.of(1, 2);
        AbilityEntity abilityEntity1 = new AbilityEntity(1, 1,1);
        AbilityEntity abilityEntity2 = new AbilityEntity(1, 2,1);
        Ability ability1 = new Ability(1, 1,1);
        Ability ability2 = new Ability(1, 2,1);

        when(abilityRepository.save(any(AbilityEntity.class)))
                .thenReturn(Mono.just(abilityEntity1))
                .thenReturn(Mono.just(abilityEntity2));
        when(abilityEntityMapper.toDomain(abilityEntity1)).thenReturn(ability1);
        when(abilityEntityMapper.toDomain(abilityEntity2)).thenReturn(ability2);

        Flux<Ability> result = abilityJpaAdapter.addAbility(1, technologyIds);

        StepVerifier.create(result)
                .expectNext(ability1, ability2)
                .verifyComplete();
    }

    @Test
    void addAbilityWithEmptyTechnologyIds() {
        List<Integer> technologyIds = List.of();

        Flux<Ability> result = abilityJpaAdapter.addAbility(1, technologyIds);

        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void addAbilityWithErrorInRepository() {
        List<Integer> technologyIds = List.of(1, 2);

        when(abilityRepository.save(any(AbilityEntity.class))).
                thenReturn(Mono.error(new RuntimeException("Repository error")));

        Flux<Ability> result = abilityJpaAdapter.addAbility(1, technologyIds);

        StepVerifier.create(result)
                .expectErrorMatches(throwable ->
                        throwable instanceof RuntimeException && throwable.getMessage().equals("Repository error"))
                .verify();
    }
}
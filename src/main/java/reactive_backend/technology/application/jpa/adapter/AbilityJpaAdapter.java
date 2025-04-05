package reactive_backend.technology.application.jpa.adapter;

import lombok.AllArgsConstructor;
import reactive_backend.technology.application.jpa.entity.AbilityEntity;
import reactive_backend.technology.application.jpa.mapper.IAbilityEntityMapper;
import reactive_backend.technology.application.jpa.repository.IAbilityRepository;
import reactive_backend.technology.domain.model.Ability;
import reactive_backend.technology.domain.spi.IAbilityPersistencePort;
import reactor.core.publisher.Flux;

import java.util.List;

@AllArgsConstructor
public class AbilityJpaAdapter implements IAbilityPersistencePort {
    private final IAbilityRepository abilityRepository;
    private final IAbilityEntityMapper abilityEntityMapper;
    @Override
    public Flux<Ability> addAbility(Integer abilityId, List<Integer> technologyIds) {
        return Flux.fromIterable(technologyIds)
                .map(techId -> {
                    AbilityEntity relation = new AbilityEntity();
                    relation.setAbilityId(abilityId);
                    relation.setTechnologyId(techId);
                    return relation;
                })
                .flatMap(abilityRepository::save).map(abilityEntityMapper::toDomain);
    }
}

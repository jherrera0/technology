package reactive_backend.technology.domain.usecase;

import reactive_backend.technology.domain.api.IAbilityServicePort;
import reactive_backend.technology.domain.model.Ability;
import reactive_backend.technology.domain.model.Technology;
import reactive_backend.technology.domain.spi.IAbilityPersistencePort;
import reactor.core.publisher.Flux;

import java.util.List;

public class AbilityCase implements IAbilityServicePort {

    private final IAbilityPersistencePort abilityPersistencePort;

    public AbilityCase(IAbilityPersistencePort abilityPersistencePort) {
        this.abilityPersistencePort = abilityPersistencePort;
    }

    @Override
    public Flux<Ability> addAbility(Integer abilityId, List<Technology> technologies) {
        List<Integer> technologyIds = technologies.stream()
                .map(Technology::getId)
                .toList();
        return abilityPersistencePort.addAbility(abilityId, technologyIds);
    }
}

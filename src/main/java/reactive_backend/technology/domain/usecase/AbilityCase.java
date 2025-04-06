package reactive_backend.technology.domain.usecase;

import reactive_backend.technology.domain.api.IAbilityServicePort;
import reactive_backend.technology.domain.model.Ability;
import reactive_backend.technology.domain.model.Technology;
import reactive_backend.technology.domain.spi.IAbilityPersistencePort;
import reactive_backend.technology.domain.spi.ITechnologyPersistencePort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class AbilityCase implements IAbilityServicePort {

    private final IAbilityPersistencePort abilityPersistencePort;
    private final ITechnologyPersistencePort technologyPersistencePort;

    public AbilityCase(IAbilityPersistencePort abilityPersistencePort,
                       ITechnologyPersistencePort technologyPersistencePort) {
        this.abilityPersistencePort = abilityPersistencePort;
        this.technologyPersistencePort = technologyPersistencePort;
    }

    @Override
    public Flux<Ability> addAbility(Integer abilityId, List<Technology> technologies) {
        List<Integer> technologyIds = technologies.stream()
                .map(Technology::getId)
                .toList();
        return abilityPersistencePort.addAbility(abilityId, technologyIds);
    }

    @Override
    public Mono<List<Technology>> getAllTechnologiesByAbilityId(Integer id) {
        return abilityPersistencePort.getAllTechnologiesByAbilityId(id)
                .flatMap(technologies -> technologyPersistencePort.getTechnologiesById(technologies)
                        .flatMap(Mono::just));
    }

}

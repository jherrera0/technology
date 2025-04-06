package reactive_backend.technology.domain.spi;

import reactive_backend.technology.domain.model.Ability;
import reactor.core.publisher.Flux;

import java.util.List;

public interface IAbilityPersistencePort {
    Flux<Ability> addAbility(Integer abilityId, List<Integer> technologyId);
}

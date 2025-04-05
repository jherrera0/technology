package reactive_backend.technology.domain.api;

import reactive_backend.technology.domain.model.Ability;
import reactive_backend.technology.domain.model.Technology;
import reactor.core.publisher.Flux;

import java.util.List;

public interface IAbilityServicePort {
    Flux<Ability> addAbility(Integer abilityId, List<Technology> technologies);
}

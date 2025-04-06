package reactive_backend.technology.domain.api;

import reactive_backend.technology.domain.model.Ability;
import reactive_backend.technology.domain.model.Technology;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IAbilityServicePort {
    Flux<Ability> addAbility(Integer abilityId, List<Technology> technologies);
    Mono<List<Technology>> getAllTechnologiesByAbilityId(Integer id);

}

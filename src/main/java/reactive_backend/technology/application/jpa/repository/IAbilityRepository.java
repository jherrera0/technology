package reactive_backend.technology.application.jpa.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactive_backend.technology.application.jpa.entity.AbilityEntity;
import reactor.core.publisher.Flux;

public interface IAbilityRepository extends ReactiveCrudRepository<AbilityEntity, Integer> {
    Flux<AbilityEntity> findAllByAbilityId(Integer id);
}

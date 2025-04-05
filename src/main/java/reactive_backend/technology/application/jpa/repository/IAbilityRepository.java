package reactive_backend.technology.application.jpa.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactive_backend.technology.application.jpa.entity.AbilityEntity;

public interface IAbilityRepository extends ReactiveCrudRepository<AbilityEntity, Integer> {
}

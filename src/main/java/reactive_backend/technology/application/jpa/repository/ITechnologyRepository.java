package reactive_backend.technology.application.jpa.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactive_backend.technology.application.jpa.entity.TechnologyEntity;
import reactor.core.publisher.Mono;

public interface ITechnologyRepository extends ReactiveCrudRepository<TechnologyEntity, Integer> {
    Mono<Boolean> existsByName(String name);
}

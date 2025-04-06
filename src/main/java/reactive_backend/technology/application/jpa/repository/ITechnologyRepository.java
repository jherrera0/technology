package reactive_backend.technology.application.jpa.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactive_backend.technology.application.jpa.entity.TechnologyEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

public interface ITechnologyRepository extends ReactiveCrudRepository<TechnologyEntity, UUID> {
    Mono<Boolean> existsByName(String name);
    Flux<TechnologyEntity> findAllBy(Pageable pageable);

    Flux<TechnologyEntity> findAllByNameIn(List<String> nameList);
}

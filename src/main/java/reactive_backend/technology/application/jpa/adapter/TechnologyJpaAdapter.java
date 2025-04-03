package reactive_backend.technology.application.jpa.adapter;

import lombok.RequiredArgsConstructor;
import reactive_backend.technology.application.jpa.mapper.ITechnologyEntityMapper;
import reactive_backend.technology.application.jpa.repository.ITechnologyRepository;
import reactive_backend.technology.domain.model.Technology;
import reactive_backend.technology.domain.spi.ITechnologyPersistencePort;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class TechnologyJpaAdapter implements ITechnologyPersistencePort {

    private final ITechnologyRepository technologyRepository;
    private final ITechnologyEntityMapper technologyEntityMapper;

    @Override
    public Mono<Technology> saveTechnology(Technology technology) {
        return technologyRepository.save(technologyEntityMapper.toEntity(technology))
                .map(technologyEntityMapper::toDomain);
    }

    @Override
    public Mono<Boolean> technologyExistsByName(String name) {
        return technologyRepository.existsByName(name);
    }

}

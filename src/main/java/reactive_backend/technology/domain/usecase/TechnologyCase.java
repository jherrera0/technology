package reactive_backend.technology.domain.usecase;

import reactive_backend.technology.domain.api.ITechnologyServicePort;
import reactive_backend.technology.domain.exception.TechnologyAlreadyExistsException;
import reactive_backend.technology.domain.model.Technology;
import reactive_backend.technology.domain.spi.ITechnologyPersistencePort;
import reactive_backend.technology.domain.util.ConstValidation;
import reactor.core.publisher.Mono;

public class TechnologyCase implements ITechnologyServicePort {

    private final ITechnologyPersistencePort technologyPersistencePort;

    public TechnologyCase(ITechnologyPersistencePort technologyPersistencePort) {
        this.technologyPersistencePort = technologyPersistencePort;
    }

    @Override
    public Mono<Technology> saveTechnology(Technology technology) {
        return technologyPersistencePort.technologyExistsByName(technology.getName())
                .flatMap(exists -> {
                    if (exists.compareTo(Boolean.TRUE) == ConstValidation.ZERO) {
                        return Mono.error(new TechnologyAlreadyExistsException());
                    }
                    return technologyPersistencePort.saveTechnology(technology);
                });
    }
}

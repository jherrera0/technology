package reactive_backend.technology.domain.usecase;

import reactive_backend.technology.domain.api.ITechnologyServicePort;
import reactive_backend.technology.domain.exception.*;
import reactive_backend.technology.domain.model.PageCustom;
import reactive_backend.technology.domain.model.Technology;
import reactive_backend.technology.domain.spi.ITechnologyPersistencePort;
import reactive_backend.technology.domain.util.ConstValidation;
import reactor.core.publisher.Mono;

import java.util.List;

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

    @Override
    public Mono<PageCustom<Technology>> listTechnology(String orderDirection, Integer pageSize, Integer currentPage) {
        Mono<PageCustom<Technology>> error = validateParameters(orderDirection, pageSize, currentPage);
        if (error != null) return error;
        return technologyPersistencePort.getAllTechnologies(orderDirection, pageSize, currentPage)
                .flatMap(page -> {
                    if (page.getTotalPages() < page.getCurrentPage()+ConstValidation.ONE) {
                        return Mono.error(new ListTechnologyPageInvalidException());
                    }
                    return Mono.just(page);
                });
    }

    @Override
    public Mono<List<Technology>> getTechnologiesByName(List<String> names) {
        return technologyPersistencePort.getAllTechnologiesByName(names)
                .flatMap(Mono::just);
    }




    private static Mono<PageCustom<Technology>> validateParameters(String orderDirection, Integer pageSize, Integer currentPage) {
        if(orderDirection.compareTo(ConstValidation.ASC) != ConstValidation.ZERO &&
                orderDirection.compareTo(ConstValidation.DESC) != ConstValidation.ZERO) {
            return Mono.error(new ListTechnologyOrderDirectionInvalidException());
        }

        if (pageSize <= ConstValidation.ZERO) {
            return Mono.error(new ListTechnologyPageSizeInvalidException());
        }

        if(currentPage < ConstValidation.ZERO) {
            return Mono.error(new ListTechnologyCurrentPageInvalidException());
        }
        return null;
    }
}

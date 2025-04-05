package reactive_backend.technology.domain.spi;

import reactive_backend.technology.domain.model.PageCustom;
import reactive_backend.technology.domain.model.Technology;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ITechnologyPersistencePort {
    Mono<Technology> saveTechnology(Technology technology);
    Mono<Boolean> technologyExistsByName(String name);
    Mono<PageCustom<Technology>> getAllTechnologies(String orderDirection, Integer pageSize, Integer currentPage);

    Mono<List<Technology>> getAllTechnologiesByName(List<String> nameList);
}

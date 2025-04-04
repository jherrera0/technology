package reactive_backend.technology.domain.api;

import reactive_backend.technology.domain.model.PageCustom;
import reactive_backend.technology.domain.model.Technology;
import reactor.core.publisher.Mono;

public interface ITechnologyServicePort {
    Mono<Technology> saveTechnology(Technology technology);
    Mono<PageCustom<Technology>> listTechnology(String orderDirection, Integer pageSize, Integer currentPage);
}
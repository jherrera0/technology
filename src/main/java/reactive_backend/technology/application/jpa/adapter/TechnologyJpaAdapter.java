package reactive_backend.technology.application.jpa.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import reactive_backend.technology.application.jpa.mapper.ITechnologyEntityMapper;
import reactive_backend.technology.application.jpa.repository.ITechnologyRepository;
import reactive_backend.technology.domain.model.PageCustom;
import reactive_backend.technology.domain.model.Technology;
import reactive_backend.technology.domain.spi.ITechnologyPersistencePort;
import reactive_backend.technology.domain.util.ConstValidation;
import reactor.core.publisher.Mono;

import java.util.List;

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

    @Override
    public Mono<PageCustom<Technology>> getAllTechnologies(String orderDirection, Integer pageSize, Integer currentPage) {
        Pageable pageable = PageRequest.of(currentPage, pageSize, Sort.by(Sort.Direction.fromString(orderDirection), ConstValidation.NAME));

        return technologyRepository.findAllBy(pageable)
                .collectList()
                .zipWith(technologyRepository.count())
                .map(tuple -> new PageCustom<>(
                        currentPage,
                        pageSize,
                        (int) Math.ceil((double) tuple.getT2() / pageSize),
                        technologyEntityMapper.toDomainList(tuple.getT1())
                ));
    }

    @Override
    public Mono<List<Technology>> getAllTechnologiesByName(List<String> nameList) {
        return technologyRepository.findAllByNameIn(nameList)
                .collectList()
                .map(technologyEntityMapper::toDomainList);
    }

    @Override
    public Mono<List<Technology>> getTechnologiesById(List<Integer> technologies) {
        return technologyRepository.findAllByIdIsIn(technologies)
                .collectList()
                .map(technologyEntityMapper::toDomainList);
    }


}

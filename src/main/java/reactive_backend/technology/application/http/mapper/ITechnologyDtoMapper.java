package reactive_backend.technology.application.http.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import reactive_backend.technology.application.http.dto.TechnologyDtoRequest;
import reactive_backend.technology.application.http.dto.TechnologyDtoResponse;
import reactive_backend.technology.domain.model.Technology;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ITechnologyDtoMapper {
    @Mapping(target = "id", ignore = true)
    Technology toTechnology(TechnologyDtoRequest technologyDtoRequest);

    TechnologyDtoResponse toDtoResponse(Technology technology);
}

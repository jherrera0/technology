package reactive_backend.technology.application.http.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import reactive_backend.technology.application.http.dto.request.TechnologyDtoRequest;
import reactive_backend.technology.application.http.dto.response.TechnologyDtoResponse;
import reactive_backend.technology.domain.model.Technology;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ITechnologyDtoMapper {
    @Mapping(target = "id", ignore = true)
    Technology toTechnology(TechnologyDtoRequest technologyDtoRequest);

    @Mapping(target = "id", source = "technology.id")
    @Mapping(target = "name", source = "technology.name")
    @Mapping(target = "description", source = "technology.description")
    TechnologyDtoResponse toDtoResponse(Technology technology);

    List<TechnologyDtoResponse> toDtoResponseList(List<Technology> technologyList);
}

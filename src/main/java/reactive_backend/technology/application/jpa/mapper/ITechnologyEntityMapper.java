package reactive_backend.technology.application.jpa.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import reactive_backend.technology.application.jpa.entity.TechnologyEntity;
import reactive_backend.technology.domain.model.Technology;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ITechnologyEntityMapper {


    Technology toDomain(TechnologyEntity entity);

    @Mapping(target = "id", ignore = true)
    TechnologyEntity toEntity(Technology domain);
}

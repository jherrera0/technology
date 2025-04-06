package reactive_backend.technology.application.jpa.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import reactive_backend.technology.application.jpa.entity.AbilityEntity;
import reactive_backend.technology.domain.model.Ability;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IAbilityEntityMapper {
    @Mapping(target = "id", ignore = true)
    Ability toDomain(AbilityEntity entity);

    AbilityEntity toEntity(Ability domain);

    List<Ability> toDomainList(List<AbilityEntity> abilities);
}

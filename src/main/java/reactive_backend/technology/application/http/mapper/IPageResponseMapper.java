package reactive_backend.technology.application.http.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import reactive_backend.technology.application.http.dto.response.PageResponse;
import reactive_backend.technology.application.http.dto.response.TechnologyDtoResponse;
import reactive_backend.technology.domain.model.PageCustom;
import reactive_backend.technology.domain.model.Technology;

@Mapper(componentModel = "spring", uses = {ITechnologyDtoMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IPageResponseMapper {
    @Mapping(target = "currentPage", source = "page.currentPage")
    @Mapping(target = "pageSize", source = "page.pageSize")
    @Mapping(target = "totalPages", source = "page.totalPages")
    @Mapping(target = "items", source = "page.items")
    PageResponse<TechnologyDtoResponse> toPageResponse(PageCustom<Technology> page);
}

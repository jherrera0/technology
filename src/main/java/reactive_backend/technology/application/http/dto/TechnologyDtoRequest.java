package reactive_backend.technology.application.http.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import reactive_backend.technology.domain.util.ConstExceptions;
import reactive_backend.technology.domain.util.ConstValidation;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TechnologyDtoRequest {

    @NotBlank(message = ConstExceptions.TECHNOLOGY_NAME_EMPTY)
    @Size(max = ConstValidation.TECHNOLOGY_NAME_MAX_SIZE,
            message = ConstExceptions.TECHNOLOGY_NAME_MAX_SIZE)
    private String name;

    @NotBlank(message = ConstExceptions.TECHNOLOGY_DESCRIPTION_EMPTY)
    @Size(max = ConstValidation.TECHNOLOGY_DESCRIPTION_MAX_SIZE,
            message = ConstExceptions.TECHNOLOGY_DESCRIPTION_MAX_SIZE)
    private String description;
}

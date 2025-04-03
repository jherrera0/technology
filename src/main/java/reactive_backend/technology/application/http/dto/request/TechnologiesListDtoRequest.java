package reactive_backend.technology.application.http.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TechnologiesListDtoRequest {
    @NotBlank
    private String orderDirection;
    @PositiveOrZero
    private Integer currentPage;
    @Positive
    private Integer pageSize;
}

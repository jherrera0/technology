package reactive_backend.technology.application.http.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import reactive_backend.technology.domain.model.Technology;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddAbilityDtoRequest {
    private Integer abilityId;
    private List<Technology> technologies;
}

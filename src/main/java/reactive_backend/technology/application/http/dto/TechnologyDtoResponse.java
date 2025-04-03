package reactive_backend.technology.application.http.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TechnologyDtoResponse {
    private Integer id;
    private String name;
    private String description;
}

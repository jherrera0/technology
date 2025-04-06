package reactive_backend.technology.application.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("ability_entity")
public class AbilityEntity {

    @Id
    private Integer id;

    @Column("technology_id")
    private Integer technologyId;

    @Column("ability_id")
    private Integer abilityId;
}

package reactive_backend.technology.domain.model;

public class Ability {
    private Integer id;
    private Integer technologyId;
    private Integer abilityId;

    public Ability() {
    }

    public Ability(Integer id, Integer technologyId, Integer abilityId) {
        this.id = id;
        this.technologyId = technologyId;
        this.abilityId = abilityId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTechnologyId() {
        return technologyId;
    }

    public void setTechnologyId(Integer technologyId) {
        this.technologyId = technologyId;
    }

    public Integer getAbilityId() {
        return abilityId;
    }

    public void setAbilityId(Integer abilityId) {
        this.abilityId = abilityId;
    }

}

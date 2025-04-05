package reactive_backend.technology.domain.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AbilityTest {

    @Test
    void constructorShouldInitializeFields() {
        Ability ability = new Ability(1, 2, 3);
        assertEquals(1, ability.getId());
        assertEquals(2, ability.getTechnologyId());
        assertEquals(3, ability.getAbilityId());
    }

    @Test
    void defaultConstructorShouldInitializeFieldsToNull() {
        Ability ability = new Ability();
        assertNull(ability.getId());
        assertNull(ability.getTechnologyId());
        assertNull(ability.getAbilityId());
    }

    @Test
    void setIdShouldUpdateId() {
        Ability ability = new Ability();
        ability.setId(1);
        assertEquals(1, ability.getId());
    }

    @Test
    void setTechnologyIdShouldUpdateTechnologyId() {
        Ability ability = new Ability();
        ability.setTechnologyId(2);
        assertEquals(2, ability.getTechnologyId());
    }

    @Test
    void setAbilityIdShouldUpdateAbilityId() {
        Ability ability = new Ability();
        ability.setAbilityId(3);
        assertEquals(3, ability.getAbilityId());
    }
}
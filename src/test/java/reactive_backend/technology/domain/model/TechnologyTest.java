package reactive_backend.technology.domain.model;

import org.junit.jupiter.api.Test;
import reactive_backend.technology.domain.exception.*;
import reactive_backend.technology.domain.util.ConstValidation;

import static org.junit.jupiter.api.Assertions.*;

class TechnologyTest {

    @Test
    void createTechnology_WithValidData_ShouldCreateSuccessfully() {
        Technology technology = new Technology(1, "Java", "Programming language");
        assertNotNull(technology);
        assertEquals(1, technology.getId());
        assertEquals("Java", technology.getName());
        assertEquals("Programming language", technology.getDescription());
    }

    @Test
    void setName_WhenNameIsNull_ShouldThrowException() {
        Technology technology = new Technology();
        assertThrows(TechnologyNameEmptyException.class, () -> technology.setName(null));
    }

    @Test
    void setName_WhenNameIsEmpty_ShouldThrowException() {
        Technology technology = new Technology();
        assertThrows(TechnologyNameEmptyException.class, () -> technology.setName(""));
    }

    @Test
    void setName_WhenNameExceedsMaxSize_ShouldThrowException() {
        Technology technology = new Technology();
        String oversizedName = "a".repeat(ConstValidation.TECHNOLOGY_NAME_MAX_SIZE + 1);
        assertThrows(TechnologyNameOverSizeException.class, () -> technology.setName(oversizedName));
    }

    @Test
    void setDescription_WhenDescriptionIsNull_ShouldThrowException() {
        Technology technology = new Technology();
        assertThrows(TechnologyDescriptionEmptyException.class, () -> technology.setDescription(null));
    }

    @Test
    void setDescription_WhenDescriptionIsEmpty_ShouldThrowException() {
        Technology technology = new Technology();
        assertThrows(TechnologyDescriptionEmptyException.class, () -> technology.setDescription(""));
    }

    @Test
    void setDescription_WhenDescriptionExceedsMaxSize_ShouldThrowException() {
        Technology technology = new Technology();
        String oversizedDescription = "a".repeat(ConstValidation.TECHNOLOGY_DESCRIPTION_MAX_SIZE + 1);
        assertThrows(TechnologyDescriptionOverSizeException.class, () -> technology.setDescription(oversizedDescription));
    }
}

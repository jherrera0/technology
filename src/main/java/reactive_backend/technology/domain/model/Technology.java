package reactive_backend.technology.domain.model;

import reactive_backend.technology.domain.exception.TechnologyDescriptionEmptyException;
import reactive_backend.technology.domain.exception.TechnologyDescriptionOverSizeException;
import reactive_backend.technology.domain.exception.TechnologyNameEmptyException;
import reactive_backend.technology.domain.exception.TechnologyNameOverSizeException;
import reactive_backend.technology.domain.util.ConstValidation;

public class Technology {
    private Integer id;
    private String name;
    private String description;

    public Technology() {
    }

    public Technology(Integer id, String name, String description) {
        setId(id);
        setName(name);
        setDescription(description);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name == null || name.isEmpty()) {
            throw new TechnologyNameEmptyException();
        }

        if (name.length() > ConstValidation.TECHNOLOGY_NAME_MAX_SIZE) {
            throw new TechnologyNameOverSizeException();
        }
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if(description == null || description.isEmpty()) {
            throw new TechnologyDescriptionEmptyException();
        }

        if (description.length() > ConstValidation.TECHNOLOGY_DESCRIPTION_MAX_SIZE) {
            throw new TechnologyDescriptionOverSizeException();
        }

        this.description = description;
    }
}

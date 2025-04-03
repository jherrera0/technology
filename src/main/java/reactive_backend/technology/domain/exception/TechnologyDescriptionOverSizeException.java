package reactive_backend.technology.domain.exception;

import reactive_backend.technology.domain.util.ConstExceptions;

public class TechnologyDescriptionOverSizeException extends RuntimeException {
    public TechnologyDescriptionOverSizeException() {
        super(ConstExceptions.TECHNOLOGY_DESCRIPTION_MAX_SIZE);
    }
}

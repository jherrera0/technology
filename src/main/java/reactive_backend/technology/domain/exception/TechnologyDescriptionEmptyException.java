package reactive_backend.technology.domain.exception;

import reactive_backend.technology.domain.util.ConstExceptions;

public class TechnologyDescriptionEmptyException extends RuntimeException {
    public TechnologyDescriptionEmptyException() {
        super(ConstExceptions.TECHNOLOGY_DESCRIPTION_EMPTY);
    }
}

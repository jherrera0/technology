package reactive_backend.technology.domain.exception;

import reactive_backend.technology.domain.util.ConstExceptions;

public class TechnologyNameOverSizeException extends RuntimeException {
    public TechnologyNameOverSizeException() {
        super(ConstExceptions.TECHNOLOGY_NAME_MAX_SIZE);
    }
}

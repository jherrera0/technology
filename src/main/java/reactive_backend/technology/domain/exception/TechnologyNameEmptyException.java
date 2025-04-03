package reactive_backend.technology.domain.exception;

import reactive_backend.technology.domain.util.ConstExceptions;

public class TechnologyNameEmptyException extends RuntimeException {
    public TechnologyNameEmptyException() {
        super(ConstExceptions.TECHNOLOGY_NAME_EMPTY);
    }
}

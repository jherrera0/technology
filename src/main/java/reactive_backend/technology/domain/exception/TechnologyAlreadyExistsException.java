package reactive_backend.technology.domain.exception;

import reactive_backend.technology.domain.util.ConstExceptions;

public class TechnologyAlreadyExistsException extends RuntimeException {
    public TechnologyAlreadyExistsException() {
        super(ConstExceptions.TECHNOLOGY_ALREADY_EXISTS);
    }
}

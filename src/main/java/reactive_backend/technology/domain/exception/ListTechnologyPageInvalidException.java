package reactive_backend.technology.domain.exception;

import reactive_backend.technology.domain.util.ConstExceptions;

public class ListTechnologyPageInvalidException extends RuntimeException {
    public ListTechnologyPageInvalidException() {
        super(ConstExceptions.LIST_TECHNOLOGY_PAGE_INVALID_EXCEPTION);
    }
}

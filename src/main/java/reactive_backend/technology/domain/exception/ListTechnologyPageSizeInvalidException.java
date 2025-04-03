package reactive_backend.technology.domain.exception;

import reactive_backend.technology.domain.util.ConstExceptions;

public class ListTechnologyPageSizeInvalidException extends RuntimeException {

    public ListTechnologyPageSizeInvalidException() {
        super(ConstExceptions.LIST_TECHNOLOGY_PAGE_SIZE_INVALID);
    }
}

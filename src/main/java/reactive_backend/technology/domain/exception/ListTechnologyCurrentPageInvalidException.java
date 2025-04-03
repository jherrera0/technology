package reactive_backend.technology.domain.exception;

import reactive_backend.technology.domain.util.ConstExceptions;

public class ListTechnologyCurrentPageInvalidException extends RuntimeException {
    public ListTechnologyCurrentPageInvalidException() {
        super(ConstExceptions.LIST_TECHNOLOGY_CURRENT_PAGE_INVALID);
    }
}

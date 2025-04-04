package reactive_backend.technology.domain.exception;

import reactive_backend.technology.domain.util.ConstExceptions;

public class ListTechnologyOrderDirectionInvalidException extends RuntimeException {
    public ListTechnologyOrderDirectionInvalidException() {
        super(ConstExceptions.LIST_TECHNOLOGY_ORDER_DIRECTION_INVALID);
    }
}

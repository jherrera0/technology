package reactive_backend.technology.domain.util;

public class ConstExceptions {
    public static final String TECHNOLOGY_ALREADY_EXISTS = "Technology already exists";
    public static final String TECHNOLOGY_NAME_EMPTY = "Technology name cannot be empty";
    public static final String TECHNOLOGY_DESCRIPTION_EMPTY = "Technology description cannot be empty";
    public static final String TECHNOLOGY_NAME_MAX_SIZE = "Technology name cannot be greater than 50 characters";
    public static final String TECHNOLOGY_DESCRIPTION_MAX_SIZE =
            "Technology description cannot be greater than 90 characters";
    public static final String LIST_TECHNOLOGY_ORDER_DIRECTION_INVALID = "Order direction must be either ASC or DESC";
    public static final String LIST_TECHNOLOGY_PAGE_SIZE_INVALID = "Page size must be greater than 0";
    public static final String LIST_TECHNOLOGY_CURRENT_PAGE_INVALID = "Current page must be greater than 0";

    private ConstExceptions() {
    }
}

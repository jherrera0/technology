package reactive_backend.technology.domain.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PageCustomTest {

    @Test
    void constructor_WhenValidParameters_ShouldCreatePageCustom() {
        int pageSize = 10;
        int currentPage = 1;
        int totalElements = 1;
        List<Technology> technologies = List.of(new Technology(1,"Java","2"));

        PageCustom<Technology> pageCustom = new PageCustom<>(
                currentPage, pageSize, totalElements, technologies);

        assertEquals(pageSize, pageCustom.getPageSize());
        assertEquals(currentPage, pageCustom.getCurrentPage());
        assertEquals(totalElements, pageCustom.getTotalPages());
        assertEquals(technologies, pageCustom.getItems());
    }

    @Test
    void setters_WhenValidParameters_ShouldCreatePageCustom() {
        int pageSize = 10;
        int currentPage = 1;
        int totalElements = 1;
        List<Technology> technologies = List.of(new Technology(1,"Java","2"));

        PageCustom<Technology> pageCustom = new PageCustom<>();

        pageCustom.setPageSize(pageSize);
        pageCustom.setCurrentPage(currentPage);
        pageCustom.setTotalPages(totalElements);
        pageCustom.setItems(technologies);

        assertEquals(pageSize, pageCustom.getPageSize());
        assertEquals(currentPage, pageCustom.getCurrentPage());
        assertEquals(totalElements, pageCustom.getTotalPages());
        assertEquals(technologies, pageCustom.getItems());
    }


}
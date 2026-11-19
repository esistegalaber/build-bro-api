package com.egalaber.buildbro.api.model;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * Paging and sorting parameters shared by the search requests. Every component is optional;
 * {@link #pageRequest()} fills in defaults (first page, 10 items, descending by {@link #defaultSortAttribute()}).
 */
public interface PagedSearch {

    Integer pageSize();

    Integer page();

    String sortAttribute();

    String sortDirection();

    String defaultSortAttribute();

    default PageRequest pageRequest() {
        int thePage = page() != null ? page() : 0;
        int thePageSize = pageSize() != null ? pageSize() : 10;
        String theSortAttribute = sortAttribute() != null ? sortAttribute() : defaultSortAttribute();
        Sort.Direction theDirection = sortDirection() != null ? Sort.Direction.fromString(sortDirection()) : Sort.Direction.DESC;
        return PageRequest.of(thePage, thePageSize, Sort.by(theDirection, theSortAttribute));
    }
}

package com.egalaber.buildbro.core.service;

import com.egalaber.buildbro.api.model.IBuildSearch;
import com.egalaber.buildbro.api.model.SortDirection;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * Turns the paging and sorting fields of the search requests into a Spring {@link PageRequest}.
 * <p>
 * The defaults (first page, 10 per page, descending by the search's default attribute) are defined in the
 * contract and filled in by the generated records' constructors, so the fields are never {@code null} here.
 */
final class Paging {

    private Paging() {
    }

    static PageRequest of(IBuildSearch search) {
        return of(search.page(), search.pageSize(), search.sortAttribute(), search.sortDirection());
    }

    private static PageRequest of(int page, int pageSize, String sortAttribute, SortDirection direction) {
        return PageRequest.of(page, pageSize, Sort.by(Sort.Direction.valueOf(direction.getValue()), sortAttribute));
    }
}

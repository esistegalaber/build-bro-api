package com.egalaber.buildbro.api.model;

import java.io.Serializable;
import java.util.List;

/**
 * One page of search results.
 *
 * @param <R> the type of the results returned from the search
 */
public interface SearchResult<R extends Serializable> extends Serializable {

    List<R> data();

    Integer page();

    Long totalElements();

    Integer totalPages();

    Boolean hasNext();

    Boolean hasPrevious();
}

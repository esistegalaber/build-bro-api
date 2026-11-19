package com.egalaber.buildbro.api.model;

import java.io.Serializable;
import java.util.List;

public record IBuildSearchResult(List<IBuild> data, Integer page, Long totalElements, Integer totalPages,
                                Boolean hasNext, Boolean hasPrevious) implements SearchResult<IBuild>, Serializable {

    public IBuildSearchResult {
        data = ModelCollections.list(data);
    }

    @Override
    public String toString() {
        // data left out on purpose: its size is only bounded by the client-chosen pageSize
        return "IBuildSearchResult[page=" + page + ", items=" + data.size() + ", totalElements=" + totalElements
                + ", totalPages=" + totalPages + ", hasNext=" + hasNext + ", hasPrevious=" + hasPrevious + "]";
    }
}

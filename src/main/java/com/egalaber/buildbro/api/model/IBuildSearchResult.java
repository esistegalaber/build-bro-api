package com.egalaber.buildbro.api.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.List;

@Schema(description = "One page of build search results.")
public record IBuildSearchResult(
        @Schema(description = "The results on this page") List<IBuild> data,
        @Schema(description = "Zero-based page number", example = "0") Integer page,
        @Schema(description = "Number of matches over all pages", example = "57") Long totalElements,
        @Schema(description = "Number of pages", example = "6") Integer totalPages,
        @Schema(description = "Whether a next page exists", example = "true") Boolean hasNext,
        @Schema(description = "Whether a previous page exists", example = "false") Boolean hasPrevious
) implements SearchResult<IBuild>, Serializable {

    public IBuildSearchResult {
        data = ModelCollections.list(data);
    }
}

package com.egalaber.buildbro.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.List;

@Schema(description = "One page of deployment search results.")
public record IDeploymentSearchResult(
        @Schema(description = "The results on this page") List<IDeployment> data,
        @Schema(description = "Zero-based page number", example = "0") Integer page,
        @Schema(description = "Number of matches over all pages", example = "57") Long totalElements,
        @Schema(description = "Number of pages", example = "6") Integer totalPages,
        @Schema(description = "Whether a next page exists", example = "true") Boolean hasNext,
        @Schema(description = "Whether a previous page exists", example = "false") Boolean hasPrevious
) implements SearchResult<IDeployment>, Serializable {

    public IDeploymentSearchResult {
        data = ModelCollections.list(data);
    }

    @Override
    public String toString() {
        // data left out on purpose: its size is only bounded by the client-chosen pageSize
        return "IDeploymentSearchResult[page=" + page + ", items=" + data.size() + ", totalElements=" + totalElements
                + ", totalPages=" + totalPages + ", hasNext=" + hasNext + ", hasPrevious=" + hasPrevious + "]";
    }
}

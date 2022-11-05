package com.egalaber.buildbro.core.mapping;

import com.egalaber.buildbro.api.model.IBuildSearchResult;
import com.egalaber.buildbro.core.domain.Build;
import org.springframework.data.domain.Page;

import java.util.stream.Collectors;

public class SearchResultMapper {
    private SearchResultMapper() {
    }

    public static IBuildSearchResult toApi(Page<Build> result) {
        IBuildSearchResult toReturn = new IBuildSearchResult();
        toReturn.setData(
                result.getContent().stream()
                        .map(BuildMapper::toApi)
                        .collect(Collectors.toList())
        );
        toReturn.setPage(result.getNumber());
        toReturn.setHasNext(result.hasNext());
        toReturn.setHasPrevious(result.hasPrevious());
        toReturn.setTotalElements(result.getTotalElements());
        toReturn.setTotalPages(result.getTotalPages());
        return toReturn;
    }


}

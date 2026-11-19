package com.egalaber.buildbro.api.model;

import java.io.Serializable;
import java.util.Map;

public record IBuildTemplate(Long id, String project, String branch, Long buildNumber,
                             Map<String, String> labels) implements Serializable {

    public IBuildTemplate {
        labels = ModelCollections.sortedMap(labels);
    }
}

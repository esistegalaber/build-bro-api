package com.egalaber.buildbro.api.model;

import java.io.Serializable;
import java.util.List;

public record IBuild(Long id, String project, String branch, Long buildNumber, List<IBuildLabel> labels)
        implements Serializable {

    public IBuild {
        labels = ModelCollections.list(labels);
    }
}

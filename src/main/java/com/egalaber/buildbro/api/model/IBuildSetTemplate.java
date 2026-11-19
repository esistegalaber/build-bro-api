package com.egalaber.buildbro.api.model;

import java.io.Serializable;
import java.util.List;

public record IBuildSetTemplate(Long id, String name, List<IBuildTemplate> buildTemplates) implements Serializable {

    public IBuildSetTemplate {
        buildTemplates = ModelCollections.list(buildTemplates);
    }
}

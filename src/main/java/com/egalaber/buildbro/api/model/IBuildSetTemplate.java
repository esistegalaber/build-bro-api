package com.egalaber.buildbro.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.List;

@Schema(description = "A named set of build templates, e.g. describing an environment.")
public record IBuildSetTemplate(
        @Schema(description = "Template id. Leave empty to create a new build set; set it to update an existing one.", example = "1") Long id,
        @Schema(description = "Unique name of the build set", example = "staging") String name,
        @Schema(description = "One template per project") List<IBuildTemplate> buildTemplates
) implements Serializable {

    public IBuildSetTemplate {
        buildTemplates = ModelCollections.list(buildTemplates);
    }
}

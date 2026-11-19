package com.egalaber.buildbro.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;

@Schema(description = "A key/value label on a deployment.")
public record IDeploymentLabel(
        @Schema(description = "Label id, assigned by Build Bro", accessMode = Schema.AccessMode.READ_ONLY, example = "3") Long id,
        @Schema(description = "Label key", example = "triggered-by") String key,
        @Schema(description = "Label value", example = "release-pipeline") String value
) implements Serializable, Comparable<IDeploymentLabel> {

    @Override
    public int compareTo(IDeploymentLabel other) {
        return key().compareTo(other.key());
    }
}

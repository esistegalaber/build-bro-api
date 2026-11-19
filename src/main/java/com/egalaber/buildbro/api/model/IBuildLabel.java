package com.egalaber.buildbro.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;

@Schema(description = "A key/value label on a build.")
public record IBuildLabel(
        @Schema(description = "Label id, assigned by Build Bro", accessMode = Schema.AccessMode.READ_ONLY, example = "7") Long id,
        @Schema(description = "Label key", example = "git-sha") String key,
        @Schema(description = "Label value", example = "3f2c1ab") String value
) implements Serializable, Comparable<IBuildLabel> {

    @Override
    public int compareTo(IBuildLabel other) {
        return key().compareTo(other.key());
    }
}

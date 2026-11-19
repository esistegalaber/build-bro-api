package com.egalaber.buildbro.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;

@Schema(description = "Error response body.")
public record IExceptionInfo(
        @Schema(description = "Machine-readable error key", allowableValues = {"not-found", "invalid-request"}, example = "not-found") String key,
        @Schema(description = "What exactly went wrong", example = "No Build found for buildId='42'") String description,
        @Schema(description = "Generic message for the error key", example = "No such data") String message
) implements Serializable {
}

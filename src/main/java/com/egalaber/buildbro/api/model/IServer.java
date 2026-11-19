package com.egalaber.buildbro.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;

@Schema(description = "A deployment target.")
public record IServer(
        @Schema(description = "Server id, assigned by Build Bro", example = "1") Long id,
        @Schema(description = "Server name, as used when recording deployments. Cannot be changed.", example = "staging-01") String name,
        @Schema(description = "Optional display name", example = "Staging (EU)") String nickName,
        @Schema(description = "Optional free text", example = "Shared staging environment") String description
) implements Serializable {
}

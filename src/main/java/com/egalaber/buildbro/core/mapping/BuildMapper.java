package com.egalaber.buildbro.core.mapping;

import com.egalaber.buildbro.api.model.IBuild;
import com.egalaber.buildbro.core.domain.Build;

public class BuildMapper {
    private BuildMapper() {
    }

    public static IBuild toApi(Build build) {
        return new IBuild(
                build.getId(),
                build.getProject(),
                build.getBranch(),
                build.getBuildNumber(),
                build.getLabels().stream().map(BuildLabelMapper::forApi).toList()
        );
    }
}

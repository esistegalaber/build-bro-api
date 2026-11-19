package com.egalaber.buildbro.core.mapping;

import com.egalaber.buildbro.api.model.IBuildNumber;
import com.egalaber.buildbro.core.domain.BuildNumber;

public class BuildNumberMapper {
    private BuildNumberMapper() {
    }

    public static IBuildNumber toApi(BuildNumber buildNumber) {
        return new IBuildNumber(buildNumber.getProject(), buildNumber.getBranch(), buildNumber.getNumber());
    }
}

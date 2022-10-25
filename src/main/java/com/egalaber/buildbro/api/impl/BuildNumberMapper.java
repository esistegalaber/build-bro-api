package com.egalaber.buildbro.api.impl;

import com.egalaber.buildbro.api.model.IBuildNumber;
import com.egalaber.buildbro.core.domain.BuildNumber;

public class BuildNumberMapper {
    private BuildNumberMapper() {
    }

    public static IBuildNumber from(BuildNumber buildNumber) {
        IBuildNumber iBuildNumber = new IBuildNumber();
        iBuildNumber.setBranch(buildNumber.getBranch());
        iBuildNumber.setProject(buildNumber.getProject());
        iBuildNumber.setNumber(buildNumber.getNumber());
        return iBuildNumber;
    }
}

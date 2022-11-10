package com.egalaber.buildbro.core.mapping;

import com.egalaber.buildbro.api.model.IBuildNumber;
import com.egalaber.buildbro.core.domain.BuildNumber;

public class BuildNumberMapper {
    private BuildNumberMapper() {
    }

    public static IBuildNumber toApi(BuildNumber buildNumber) {
        IBuildNumber toReturn = new IBuildNumber();
        toReturn.setBranch(buildNumber.getBranch());
        toReturn.setProject(buildNumber.getProject());
        toReturn.setNumber(buildNumber.getNumber());
        return toReturn;
    }
}

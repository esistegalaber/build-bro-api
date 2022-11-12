package com.egalaber.buildbro.core.mapping;

import com.egalaber.buildbro.api.model.IBuild;
import com.egalaber.buildbro.core.domain.Build;

public class BuildMapper {
    private BuildMapper() {
    }

    public static IBuild toApi(Build build) {
        IBuild toReturn = new IBuild();
        toReturn.setBranch(build.getBranch());
        toReturn.setProject(build.getProject());
        toReturn.setId(build.getId());
        toReturn.setBuildNumber(build.getBuildNumber());
        build.getLabels().forEach(singleLabel ->
                toReturn.getLabels().add(BuildLabelMapper.forApi(singleLabel))
        );
        return toReturn;
    }
}

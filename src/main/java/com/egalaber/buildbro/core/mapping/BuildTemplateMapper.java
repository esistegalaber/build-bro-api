package com.egalaber.buildbro.core.mapping;

import com.egalaber.buildbro.api.model.IBuildTemplate;
import com.egalaber.buildbro.core.domain.BuildTemplate;

public class BuildTemplateMapper {
    private BuildTemplateMapper() {
    }

    public static IBuildTemplate toApi(BuildTemplate template) {
        IBuildTemplate toReturn = new IBuildTemplate();
        toReturn.setId(template.getId());
        toReturn.setProject(template.getProject());
        toReturn.setBranch(template.getBranch());
        toReturn.setBuildNumber(template.getBuildNumber());
        toReturn.getLabels().putAll(template.getLabels());
        return toReturn;
    }

    public static BuildTemplate toDomain(IBuildTemplate template) {
        BuildTemplate toReturn = new BuildTemplate();
        toReturn.setProject(template.getProject());
        toReturn.setBranch(template.getBranch());
        toReturn.setBuildNumber(template.getBuildNumber());
        toReturn.getLabels().putAll(template.getLabels());
        return toReturn;
    }
}

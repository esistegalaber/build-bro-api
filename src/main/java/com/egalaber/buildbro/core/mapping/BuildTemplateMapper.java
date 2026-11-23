package com.egalaber.buildbro.core.mapping;

import com.egalaber.buildbro.api.model.IBuildTemplate;
import com.egalaber.buildbro.core.domain.BuildTemplate;

import java.util.TreeMap;

public class BuildTemplateMapper {
    private BuildTemplateMapper() {
    }

    public static IBuildTemplate toApi(BuildTemplate template) {
        return new IBuildTemplate(
                template.getId(),
                template.getProject(),
                template.getBranch(),
                template.getBuildNumber(),
                new TreeMap<>(template.getLabels()) // a sorted copy, not the persistent collection
        );
    }

    public static BuildTemplate toDomain(IBuildTemplate template) {
        BuildTemplate toReturn = new BuildTemplate();
        toReturn.setProject(template.project());
        toReturn.setBranch(template.branch());
        toReturn.setBuildNumber(template.buildNumber());
        toReturn.getLabels().putAll(template.labels());
        return toReturn;
    }
}

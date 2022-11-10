package com.egalaber.buildbro.core.mapping;

import com.egalaber.buildbro.api.model.IBuildSetTemplate;
import com.egalaber.buildbro.core.domain.BuildSetTemplate;

import java.util.stream.Collectors;

public class BuildSetTemplateMapper {
    private BuildSetTemplateMapper() {
    }

    public static IBuildSetTemplate toApi(BuildSetTemplate template) {
        IBuildSetTemplate toReturn = new IBuildSetTemplate();
        toReturn.setId(template.getId());
        toReturn.setName(template.getName());
        toReturn.setBuildTemplates(
                template.getBuildTemplates().stream()
                        .map(BuildTemplateMapper::toApi)
                        .collect(Collectors.toList())
        );
        return toReturn;
    }
}

package com.egalaber.buildbro.core.mapping;

import com.egalaber.buildbro.api.model.IBuildSetTemplate;
import com.egalaber.buildbro.core.domain.BuildSetTemplate;

public class BuildSetTemplateMapper {
    private BuildSetTemplateMapper() {
    }

    public static IBuildSetTemplate toApi(BuildSetTemplate template) {
        return new IBuildSetTemplate(
                template.getId(),
                template.getName(),
                template.getBuildTemplates().stream().map(BuildTemplateMapper::toApi).toList()
        );
    }
}

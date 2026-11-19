package com.egalaber.buildbro.core.mapping;

import com.egalaber.buildbro.api.model.IBuildLabel;
import com.egalaber.buildbro.core.domain.BuildLabel;

public class BuildLabelMapper {
    private BuildLabelMapper() {
    }

    public static IBuildLabel forApi(BuildLabel buildLabel) {
        return new IBuildLabel(buildLabel.getId(), buildLabel.getKey(), buildLabel.getValue());
    }
}

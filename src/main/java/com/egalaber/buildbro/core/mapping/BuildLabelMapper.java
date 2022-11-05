package com.egalaber.buildbro.core.mapping;

import com.egalaber.buildbro.api.model.IBuildLabel;
import com.egalaber.buildbro.core.domain.BuildLabel;

public class BuildLabelMapper {
    private BuildLabelMapper() {
    }

    public static IBuildLabel forApi(BuildLabel buildLabel) {
        IBuildLabel toReturn = new IBuildLabel();
        toReturn.setId(buildLabel.getId());
        toReturn.setKey(buildLabel.getKey());
        toReturn.setValue(buildLabel.getValue());
        return toReturn;
    }
}
